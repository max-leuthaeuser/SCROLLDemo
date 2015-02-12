name := "VLDBDemo"

scalaVersion := "2.11.5"

lazy val commonSettings = Seq(
  organization := "tu.dresden.de",
  version := "0.0.1",
  scalaVersion := "2.11.5",
  scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")
)

lazy val scalaroles = RootProject(file("../RoleDispatch"))

lazy val main = (project in file(".")).dependsOn(scalaroles % "test->test;compile->compile").settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
    	"org.scalatest" %% "scalatest" % "2.2.3" % "test"
    )
  )

assemblyJarName in assembly := "VLDBDemo.jar"

scalacOptions in(Compile, doc) <+= (scalaVersion, scalaInstance) map { (
  scalaVer,
  scalaIn
  ) => "-doc-external-doc:" + scalaIn.libraryJar + "#http://www.scala-lang.org/api/" + scalaVer + "/"
}