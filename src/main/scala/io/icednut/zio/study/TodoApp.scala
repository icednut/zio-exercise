package io.icednut.zio.study

import sttp.tapir.PublicEndpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir._
import zhttp.http.{Http, Request, Response}
import zhttp.service.Server
import zio._


object TodoApp extends ZIOAppDefault {
  def countCharacters(s: String): ZIO[Any, Nothing, Int] =
    ZIO.succeed(s.length)

  val countCharactersEndpoint: PublicEndpoint[String, Unit, Int, Any] =
    endpoint.in(stringBody).out(plainBody[Int])

  val countCharactersHttp: Http[Any, Throwable, Request, Response] =
    ZioHttpInterpreter().toHttp(countCharactersEndpoint.zServerLogic(countCharacters))

  override def run =
    Server.start(8090, countCharactersHttp)
}
