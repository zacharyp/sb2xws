name := """sb2xws"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"


val json4s = "org.json4s" %% "json4s-jackson" % "3.6.0"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  guice,
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  ws,
  json4s
)
