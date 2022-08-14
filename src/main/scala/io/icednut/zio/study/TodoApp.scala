package io.icednut.zio.study

import sttp.model.StatusCode
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.service.Server
import zio._
import zio.json._

case class TodoItem(id: Int, message: String)

object TodoItem {
  implicit val decoder: JsonDecoder[TodoItem] = DeriveJsonDecoder.gen[TodoItem]
  implicit val encode: JsonEncoder[TodoItem] = DeriveJsonEncoder.gen[TodoItem]
}

object TodoApp extends ZIOAppDefault {

  import TodoItem._

  var todoList = List(
    TodoItem(1, "Hello"),
    TodoItem(2, "Hello2"),
    TodoItem(3, "Hello3"),
  )

  def countCharacters(p: Any) =
    ZIO.succeed(todoList)

  val countCharactersEndpoint =
    endpoint.get
      .in("todo")
      .out(jsonBody[List[TodoItem]])

  val todoItemEndpoint =
    endpoint.get
      .in("todo")
      .in(path[Int]("id"))
      .out(jsonBody[TodoItem])
      .errorOut(
        oneOf[String](
          oneOfVariant(statusCode(StatusCode.NotFound).and(stringBody))
        )
      )

  val todoItemAddEndpoint =
    endpoint.post
      .in("todo")
      .in(stringBody)
      .out(stringBody)

  val countCharactersHttp =
    ZioHttpInterpreter().toHttp(countCharactersEndpoint.zServerLogic(countCharacters)) ++
      ZioHttpInterpreter().toHttp(todoItemEndpoint.zServerLogic(id =>
        todoList.find(item => item.id == id).map(item => ZIO.succeed(item)).getOrElse(ZIO.fail("not found"))
      )) ++
      ZioHttpInterpreter().toHttp(todoItemAddEndpoint.zServerLogic(item => {
        val newTodoItemId = todoList.last.id + 1
        val newTodoItem = TodoItem(id = newTodoItemId, message = item)

        todoList ++= List(newTodoItem)
        ZIO.succeed(newTodoItem.id.toString)
      }))

  override def run =
    Server.start(8090, countCharactersHttp)
}
