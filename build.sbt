val scala3Version = "3.1.2"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "scalajs-test",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0"
  )
