package io.icednut.zio.study

import sttp.client3
import sttp.client3.{HttpClientSyncBackend, UriContext}
import zio.test._



object TodoServerSpec extends ZIOSpecDefault {

  def spec = suite("clock")(
    test("foo") {
      // 1 요청을 보낸다
      val request = client3.basicRequest.get(uri"https://www.google.com/") // https://www.google.com/
      val backend = HttpClientSyncBackend()
//      val value = backend.send(client3.basicRequest.get(uri"http://google.com"))

      val value = request.send(backend)

      // 2 응답을 검증한다
      println(value)
      assertTrue(true)
    },
    test("foo bar") {
      assertTrue(true)
    },
    test("foo bar baz") {
      assertTrue(true)
    }
  )
}
