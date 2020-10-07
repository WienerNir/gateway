import sbt._

object Dependencies {

  val akkaHttpVersion = "10.1.12"
  val akkaVersion = "2.6.6"
  val circeVersion = "0.12.3"
  val enumeratumCֹirceVersion = "1.5.18"
  val enumeratumVersion = "1.5.13"
  val http4sVersion = "0.19.0"

  private val akka: List[ModuleID] = List(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test
  )
  private val unitTests: List[ModuleID] = List(
    "org.scalatest" %% "scalatest" % "3.0.6" % Test,
    "org.scalamock" %% "scalamock" % "4.1.0" % Test,
  )

  val enumeratum: List[ModuleID] = List(
    "com.beachape" %% "enumeratum" % enumeratumVersion,
    "com.beachape" %% "enumeratum-circe" % enumeratumCֹirceVersion
  )

  val common: List[ModuleID] = List(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "com.github.pureconfig" %% "pureconfig" % "0.14.0",
    "com.softwaremill.retry" %% "retry" % "0.3.3",
    "io.circe" %% "circe-derivation" % "0.12.0-M3",
    "org.typelevel" %% "cats-core" % "2.0.0",
    "com.softwaremill.quicklens" %% "quicklens" % "1.6.0",
    "de.heikoseeberger" %% "akka-http-circe" % "1.30.0"
  )

  val circe: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-parser",
  ).map(_ % circeVersion)

  val it: Seq[ModuleID] = Seq(
    "io.monix" %% "monix" % "3.1.0" % Test,
    "org.testcontainers" % "testcontainers" % "1.12.3" % Test,
    "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.368" % Test,
    "org.gnieh" %% "diffson-circe" % "4.0.1" % Test,
    "org.scalatest" %% "scalatest" % "3.0.6" % Test,
    "org.scalamock" %% "scalamock" % "4.1.0" % Test
  )

  val http = Seq(
    // Only necessary for SNAPSHOT release
    //resolvers += Resolver.sonatypeRepo("snapshots")
    "org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion,
    "org.http4s" %% "http4s-client" % http4sVersion,
  )

  val serverItDependencies: List[ModuleID] = unitTests ++ it

  val commonDependencies
    : Seq[ModuleID] = circe ++ common ++ akka ++ enumeratum ++ unitTests ++ http

  val serverDependencies: Seq[ModuleID] = commonDependencies
}
