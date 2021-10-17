name := "xmlite"

version := "0.1"

scalaVersion := "3.0.2"

idePackagePrefix := Some("plenix.tools")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.0",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

