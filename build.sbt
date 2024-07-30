ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

lazy val root = (project in file("."))
  .settings(
    name := "Calculator",
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "8.0.102-R11",
      "com.lihaoyi" %% "fastparse" % "2.3.3"
    ),

    mainClass in Compile := Some("com.example.calculator.CalculatorGUI")
  )
