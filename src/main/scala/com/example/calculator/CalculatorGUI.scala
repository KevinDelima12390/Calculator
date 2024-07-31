package com.example.calculator

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, Tooltip}
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.scene.paint.Color

object CalculatorGUI extends JFXApp {
  val calculator = new Calculator
  val displayLabel = new Label {
    text = "0"
    style = "-fx-font-size: 30pt; -fx-text-fill: white; -fx-background-color: black; -fx-background-radius: 15px; -fx-padding: 20px; -fx-alignment: center-right;"
    minWidth = 320
    wrapText = true
  }

  def updateDisplay(): Unit = {
    displayLabel.text = calculator.getCurrentExpression
  }

  def showError(message: String): Unit = {
    displayLabel.text = message
  }

  val buttonSize = 80
  val buttonFontSize = 20

  val buttons = Seq(
    ("C", "clear"), ("sin", "sin"), ("cos", "cos"), ("tan", "tan"),
    ("7", "digit"), ("8", "digit"), ("9", "digit"), ("/", "operator"),
    ("4", "digit"), ("5", "digit"), ("6", "digit"), ("*", "operator"),
    ("1", "digit"), ("2", "digit"), ("3", "digit"), ("-", "operator"),
    ("0", "digit"), (".", "decimal"), ("=", "equals"), ("+", "operator"),
    ("ln", "ln"), ("log", "log"), ("√", "sqrt"), ("^", "operator"),
    ("%", "percent"), ("n!", "factorial")
  ).map { case (label, buttonType) =>
    val btn = new Button(label) {
      minWidth = buttonSize
      minHeight = buttonSize
      style = buttonType match {
        case "digit" => s"-fx-font-size: ${buttonFontSize}pt; -fx-background-color: #505050; -fx-text-fill: white; -fx-background-radius: 40px;"
        case "operator" => s"-fx-font-size: ${buttonFontSize}pt; -fx-background-color: #d4d4d2; -fx-text-fill: black; -fx-background-radius: 40px;"
        case "clear" | "equals" => s"-fx-font-size: ${buttonFontSize}pt; -fx-background-color: #ff9500; -fx-text-fill: white; -fx-background-radius: 40px;"
        case _ => s"-fx-font-size: ${buttonFontSize}pt; -fx-background-color: #d4d4d2; -fx-text-fill: black; -fx-background-radius: 40px;"
      }
      tooltip = new Tooltip(label match {
        case "sin" => "Sine"
        case "cos" => "Cosine"
        case "tan" => "Tangent"
        case "ln" => "Natural Logarithm"
        case "log" => "Logarithm"
        case "√" => "Square Root"
        case "^" => "Exponentiation"
        case "%" => "Percentage"
        case "n!" => "Factorial"
        case _ => label
      })
      onAction = _ => handleButtonClick(label)
    }
    btn
  }

  def handleButtonClick(label: String): Unit = label match {
    case digit if "0123456789".contains(digit) =>
      calculator.inputDigit(digit)
      updateDisplay()
    case "sin" =>
      calculator.inputSine()
      updateDisplay()
    case "cos" =>
      calculator.inputCosine()
      updateDisplay()
    case "tan" =>
      calculator.inputTangent()
      updateDisplay()
    case "ln" =>
      calculator.inputLn()
      updateDisplay()
    case "log" =>
      calculator.inputLog()
      updateDisplay()
    case "√" =>
      calculator.inputSquareRoot()
      updateDisplay()
    case "%" =>
      calculator.inputPercentage()
      updateDisplay()
    case "n!" =>
      calculator.inputFactorial()
      updateDisplay()
    case "." =>
      calculator.inputDecimal()
      updateDisplay()
    case "=" =>
      try {
        calculator.calculate()
        updateDisplay()
      } catch {
        case e: Exception => showError("Error")
      }
    case "C" =>
      calculator.clear()
      updateDisplay()
    case op if "+-*/^".contains(op) =>
      calculator.setOperation(op)
      updateDisplay()
  }

  stage = new PrimaryStage {
    title = "Calculator"
    scene = new Scene {
      fill = Color.Black
      content = new VBox {
        padding = Insets(20)
        spacing = 10
        children = Seq(
          displayLabel,
          new GridPane {
            hgap = 10
            vgap = 10
            style = "-fx-padding: 20px;"
            buttons.zipWithIndex.foreach { case (btn, idx) =>
              GridPane.setConstraints(btn, idx % 4, idx / 4)  // Set button position in grid
              if (btn.getText == "0") {
                GridPane.setColumnSpan(btn, 2)
              }
              children.add(btn)
            }
          }
        )
      }
    }
  }
}
