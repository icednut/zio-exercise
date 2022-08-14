ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "zio-exercise",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0",
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "1.0.4",
      "com.softwaremill.sttp.client3" %% "core" % "3.7.4",
      "dev.zio" %% "zio-test" % "2.0.0" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.0" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.0.0" % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % "1.0.4" % Test
    )
  )
