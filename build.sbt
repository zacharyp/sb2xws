
name := """sb2xws"""

version := "1.1.0"

scalaVersion := "2.12.6"

val json4s = "org.json4s" %% "json4s-jackson" % "3.6.0"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  guice,
  ws,
  json4s
)

lazy val sb2xws = (project in file(".")).enablePlugins(PlayScala)
  .settings(
    unmanagedResourceDirectories in Compile += baseDirectory.value / "xwing-data2"
  )
