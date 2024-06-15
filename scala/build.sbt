
val scala3Version = "3.4.2"
val scala2Version = "2.13.14"
val scalaVersionSet = scala3Version

val http4sVersion = "0.23.26" //pulls cats effect 3
val circeVersion = "0.14.5"
val scalatestVersion = "3.2.15"
val typesafeConfigVersion ="1.4.3"
val MunitVersion = "0.7.29"
val LogbackVersion = "1.5.6"
val MunitCatsEffectVersion = "1.0.7"


lazy val httpApi = Seq(
  "org.http4s"            %% "http4s-core"         % http4sVersion,
  "org.http4s"            %% "http4s-ember-server" % http4sVersion,
  "org.http4s"            %% "http4s-ember-client" % http4sVersion,
  "org.http4s"            %% "http4s-circe"        % http4sVersion,
  "org.http4s"            %% "http4s-dsl"          % http4sVersion
)

lazy val jsonDeps = Seq(
  "io.circe"              %% "circe-core"          % circeVersion,
  "io.circe"              %% "circe-generic"       % circeVersion,
  "io.circe"              %% "circe-literal"       % circeVersion,
  "io.circe"              %% "circe-parser"        % circeVersion,
  "io.circe"              %% "circe-config"        % "0.10.0"
)



lazy val root = project
 // .enablePlugins(GraalVMNativeImagePlugin, DockerPlugin, JavaServerAppPackaging)
  .in(file("."))
  .settings(
    name := "scala",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scalaVersionSet,

    libraryDependencies ++= httpApi ++ jsonDeps ++ 
      Seq(
        "com.typesafe" % "config" % typesafeConfigVersion,
        "ch.qos.logback" % "logback-classic" % LogbackVersion,

        // Test
        "org.scalatest" %% "scalatest" % scalatestVersion % Test,
        "org.scalameta" %% "munit" % MunitVersion % Test,
        "org.typelevel" %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      ),

  )


resolvers ++= Seq(
  "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
)

