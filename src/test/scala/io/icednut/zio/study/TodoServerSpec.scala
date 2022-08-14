package io.icednut.zio.study

import io.icednut.zio.study.HelloWorld.countCharactersEndpoint
import sttp.client3
import sttp.client3._
import sttp.tapir.client.sttp.SttpClientInterpreter
import zio.test._


object TodoServerSpec extends ZIOSpecDefault {

  def spec = suite("HTTP 요청 보내기 연습")(
    test("Sttp Client 사용하기") {
      // 1 요청을 보낸다
      val request = client3.basicRequest.get(uri"http://localhost:8090")
      val backend = HttpClientSyncBackend()
      val response = request.send(backend)

      // 2 응답을 검증한다
      val result = response.body match {
        case Right(result) => result
        case _ => ""
      }

      assertTrue(result == "0")
    },
    test("Tapir Sttp Client 사용하기") {
      val backend = HttpClientSyncBackend()
      val expectedText = "Ann"
      val request = SttpClientInterpreter()
        .toRequest(countCharactersEndpoint, Some(uri"http://localhost:8090"))
        .apply(expectedText)
        .response(asStringAlways)

      val response = request.send(backend)

      assertTrue(response.body == s"${expectedText.length}")

    }
  )
}
