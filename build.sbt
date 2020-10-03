import sbt.Keys.libraryDependencies

lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion = "2.6.6"

name := "gateway"

lazy val server = (project in file("server")).enablePlugins(JavaAppPackaging)
  .dependsOn(toolkit)
  .settings(name := "server",
    libraryDependencies ++= Seq(
     "org.scalatest" %% "scalatest" % "3.0.6" % Test,
     "org.scalamock" %% "scalamock" % "4.1.0" % Test,
    )
  )

lazy val toolkit = (project in file("toolkit"))
  .settings(
    name := "toolkit",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.0.6" % Test,
      "org.scalamock" %% "scalamock" % "4.1.0" % Test,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "com.github.pureconfig" %% "pureconfig" % "0.14.0",
      "com.softwaremill.retry" %% "retry" % "0.3.3",
      "io.circe" %% "circe-derivation" % "0.12.0-M3",
      "org.typelevel" %% "cats-core" % "2.0.0"
    ),
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser",
     
      
    ).map(_ % "0.12.3")
  )

lazy val it = (project in file("it"))
  .settings(
    name := "it",
    libraryDependencies ++= serverIt
  )
  .dependsOn(toolkit)


val serverIt: List[ModuleID] = List(
  "io.monix" %% "monix" % "3.1.0" % Test,
  "org.testcontainers" % "testcontainers" % "1.12.3" % Test,
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.368" % Test,
  "org.gnieh" %% "diffson-circe" % "4.0.1" % Test,
  "org.scalatest" %% "scalatest" % "3.0.6" % Test,
  "org.scalamock" %% "scalamock" % "4.1.0" % Test,
)
