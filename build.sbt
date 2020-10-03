import sbt.Keys.libraryDependencies

lazy val akkaHttpVersion = "10.1.12"
lazy val akkaVersion = "2.6.6"

name := "gateway"

lazy val server = (project in file("server"))
  .enablePlugins(JavaAppPackaging)
  .dependsOn(toolkit)
  .settings(
    name := "server",
    libraryDependencies ++= Dependencies.serverDependencies
  )

lazy val toolkit = (project in file("toolkit"))
  .settings(
    name := "toolkit",
    libraryDependencies ++= Dependencies.commonDependencies
  )

lazy val it = (project in file("it"))
  .settings(
    name := "it",
    libraryDependencies ++= Dependencies.serverItDependencies
  )
  .dependsOn(toolkit)
