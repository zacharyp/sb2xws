name := """sb2xws"""

version := "1.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"


val json4s = "org.json4s" %% "json4s-jackson" % "3.6.0"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  guice,
  ws,
  json4s
)
