organization := "org.zachary"

name := """sb2xws"""

version := "1.1.0"

scalaVersion := "2.12.6"

val json4s = "org.json4s" %% "json4s-jackson" % "3.6.0"

val commonsIO = "commons-io" % "commons-io" % "2.6"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  guice,
  ws,
  json4s,
  commonsIO
)

lazy val sb2xws = (project in file("."))
  .enablePlugins(PlayScala)

