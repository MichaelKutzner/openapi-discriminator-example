val scala3Version = "3.7.4"
val circeVersion = "0.14.15"
val http4sVersion = "0.23.34"

lazy val root = project
  .in(file("."))
  .aggregate(client)
  .dependsOn(client)
  .settings(
    name := "OpenAPI usage example",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
    ),
  )

lazy val client = project
  .in(file("http4s-client"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .settings(
    name := "http4s client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "openapi.yaml",
    openApiConfigFile := "config-http4s.yaml",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
    ),
  )

lazy val pythonClient = project
  .in(file("python-client"))
  .enablePlugins(OpenApiGeneratorPlugin)
  .settings(
    name := "python client",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    openApiInputSpec := "openapi.yaml",
    openApiConfigFile := "config-python.yaml",
  )
