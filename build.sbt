ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"  // Update to a more recent version compatible with ScalaFX

lazy val root = (project in file("."))
  .settings(
    name := "Calculator",
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "8.0.102-R11"  // Add ScalaFX dependency
    ),
    // Optionally, you can add a mainClass setting if you want to specify the entry point
    mainClass in Compile := Some("com.example.calculator.CalculatorGUI")
  )
