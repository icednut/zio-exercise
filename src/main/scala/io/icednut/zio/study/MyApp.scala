package io.icednut.zio.study

import zio._
import zio.Console._


object MyApp extends ZIOAppDefault {

  override def run = myAppLogic

  val myAppLogic = for {
    args <- getArgs
    _ <- printLine(s"Hello, ZIO. ${args}")
  } yield ()
}
