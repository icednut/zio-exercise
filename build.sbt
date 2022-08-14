ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "zio-exercise",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "com.softwaremill.sttp.client3" %% "core" % "3.7.4",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "1.0.4",
      "com.softwaremill.sttp.tapir" %% "tapir-zio" % "1.0.4",
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % "1.0.4",
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % "1.0.4",
      "io.d11" %% "zhttp" % "2.0.0-RC10",
      "dev.zio" %% "zio-test" % "2.0.0" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.0" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.0.0" % Test
    )
  )
