name         := "hello-scala"
version      := "0.1.0-SNAPSHOT"
organization := "com.example"
scalaVersion := "3.8.3"

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

Test / testOptions += Tests.Argument(TestFrameworks.MUnit, "-o")

// sbt-assembly configuration
assembly / assemblyJarName := s"${name.value}-assembly-${version.value}.jar"
assembly / mainClass := Some("Main")

// Merge strategy for resolving conflicts in META-INF and other files
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "maven", "org.webjars", "swagger-ui", "pom.properties") => MergeStrategy.first
  case PathList("META-INF", "versions", xs @ _*)                                    => MergeStrategy.first
  case PathList("META-INF", "io.netty.versions.properties")                         => MergeStrategy.first
  case PathList("META-INF", "native-image", xs @ _*)                                => MergeStrategy.first
  case PathList("META-INF", xs @ _*) if xs.lastOption.exists(_.endsWith(".SF"))     => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) if xs.lastOption.exists(_.endsWith(".DSA"))    => MergeStrategy.discard
  case PathList("META-INF", xs @ _*) if xs.lastOption.exists(_.endsWith(".RSA"))    => MergeStrategy.discard
  case PathList("META-INF", "MANIFEST.MF")                                          => MergeStrategy.discard
  case "module-info.class"                                                          => MergeStrategy.discard
  case "application.conf"                                                           => MergeStrategy.concat
  case "reference.conf"                                                             => MergeStrategy.concat
  case x if x.endsWith(".proto")                                                    => MergeStrategy.first
  case _                                                                            => MergeStrategy.first
}
