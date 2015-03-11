import spray.revolver.RevolverPlugin._

name := "SCROLLDemo"

lazy val commonSettings = Seq(
  organization := "tu.dresden.de",
  version := "0.0.1",
  scalaVersion := "2.11.6",
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")
)

lazy val scalaroles = RootProject(file("../RoleDispatch"))

lazy val main = (project in file(".")).dependsOn(scalaroles % "test->test;compile->compile").settings(commonSettings: _*).
  settings(
    resolvers += "spray repo" at "http://repo.spray.io",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.3" % "test",
      "io.spray" %% "spray-can" % "1.3.2",
      "io.spray" %% "spray-caching" % "1.3.2",
      "io.spray" %% "spray-routing-shapeless2" % "1.3.2",
      "io.spray" %% "spray-json" % "1.3.1",
      "com.typesafe.akka" %% "akka-actor" % "2.3.6"
    ),
    unmanagedResourceDirectories in Compile <++= baseDirectory { base =>
      Seq(base / "src/main/webapp")
    },
    assemblyJarName in assembly := "SCROLLDemo.jar"
  ).enablePlugins(SbtTwirl)

Revolver.settings

mainClass in Revolver.reStart := Some("Boot")

mainClass in assembly := Some("Boot")

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

scalacOptions in(Compile, doc) <+= (scalaVersion, scalaInstance) map { (
 scalaVer,
 scalaIn) => "-doc-external-doc:" + scalaIn.libraryJar + "#http://www.scala-lang.org/api/" + scalaVer + "/"
}
