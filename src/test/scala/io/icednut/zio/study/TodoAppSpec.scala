package io.icednut.zio.study

import io.icednut.zio.study.TodoApp.countCharactersEndpoint
import sttp.client3._
import sttp.tapir.client.sttp.SttpClientInterpreter
import zio._
import zio.test._


object TodoAppSpec extends ZIOSpecDefault {

  override def spec = suite("HTTP 요청 보내기 연습")(
    //    test("Sttp Client 사용하기") {
    //      // 1 요청을 보낸다
    //      val request = basicRequest.get(uri"http://localhost:8090/")
    //      val backend = HttpClientSyncBackend()
    //      val response = request.send(backend)
    //
    //      // 2 응답을 검증한다
    //      val result = response.body match {
    //        case Right(result) => result
    //        case _ => ""
    //      }
    //
    //      assertTrue(result == "0")
    //    },
    //    test("Tapir Sttp Client 사용하기") {
    //      val backend = HttpClientSyncBackend()
    //      val expectedText = "Ann"
    //      val request = SttpClientInterpreter()
    //        .toRequest(countCharactersEndpoint, Some(uri"http://localhost:8090"))
    //        .apply(expectedText)
    //        .response(asStringAlways)
    //
    //      val response = request.send(backend)
    //
    //      assertTrue(response.body == s"${expectedText.length}")
    //
    //    },
//    test("TODO 목록 받아오기 (나중에 다시 고치자)") {
//      val backend = HttpClientSyncBackend()
//      val expectedText = "[{\"id\":1,\"message\":\"Hello\"},{\"id\":2,\"message\":\"Hello2\"},{\"id\":3,\"message\":\"Hello3\"}]"
//      val request: RequestT[Identity, String, Any] = SttpClientInterpreter()
//        .toRequest(countCharactersEndpoint, Some(uri"http://localhost:8090"))
//        .apply(expectedText)
//        .response(asStringAlways)
//
//      val response = request.send(backend)
//      val body: String = response.body
//
//      assertTrue(response.code.code == 200)
//      assertTrue(body == expectedText)
//    },
    test("TODO 아이템 1개만 가져오기") {
      // 1 요청을 보낸다
      val request = basicRequest.get(uri"http://localhost:8090/todo/1")
      val backend = HttpClientSyncBackend()
      val response = request.send(backend)

      // 2 응답을 검증한다
      assertTrue(response.body == Right("{\"id\":1,\"message\":\"Hello\"}"))
    },
    test("없는 TODO 아이템 요청") {
      // 1 요청을 보낸다
      val request = basicRequest.get(uri"http://localhost:8090/todo/0")
      val backend = HttpClientSyncBackend()
      val response = request.send(backend)

      // 2 응답을 검증한다
      assertTrue(response.code.code == 404)
    },
    test("TODO 아이템 추가하기") {
      // 1 요청을 보낸다
      val request = basicRequest.post(uri"http://localhost:8090/todo")
        .body(
          "ZIO Study"
        )
      val backend = HttpClientSyncBackend()
      val response = request.send(backend)

      val newTodoItemId = response.body.getOrElse("0")

      println(s"TODO ADD 결과: ${newTodoItemId}")

      val getTodoItemRequest = basicRequest.get(uri"http://localhost:8090/todo/${newTodoItemId}")
      val todoItemResponse = getTodoItemRequest.send(backend)

      // 2 응답을 검증한다
      assertTrue(response.code.code == 200)
      assertTrue(todoItemResponse.code.code == 200)
      assertTrue(todoItemResponse.body == Right(s"{\"id\":${newTodoItemId},\"message\":\"ZIO Study\"}"))
    }

    // TODO: 테스트 전용 웹서버 실행하기
    //    test("Sttp Client 사용하기 2") {
    //      for {
    //        backend <- ZIO.service[SttpBackend[Task, Any]]
    //        response <- basicRequest.get(uri"http://localhost:8090").send(backend)
    //      } yield {
    //        val result = response.body match {
    //          case Right(result) => result
    //          case _ => ""
    //        }
    //
    //        assertTrue(result == "0")
    //      }
    //    }
    //  ).provide(
    //    ???
    //    //    HttpClientZioBackend.layer()
    //
  )
}
