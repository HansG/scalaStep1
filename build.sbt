scalaVersion := "3.7.4"

val http4sVersion = "0.23.30"
val circeVersion  = "0.14.6"
val munitVersion  = "1.0.7"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion, // statt blaze-server
  "org.http4s" %% "http4s-ember-client" % http4sVersion, // statt blaze-client
  "org.http4s" %% "http4s-circe"        % http4sVersion,
  "io.circe"   %% "circe-generic"       % "0.14.6",
  "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test
)

