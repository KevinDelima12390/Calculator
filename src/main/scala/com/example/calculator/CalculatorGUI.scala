package com.example.calculator

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{GridPane, VBox}
import scalafx.scene.paint.Color

object CalculatorGUI extends JFXApp {
  val calculator = new Calculator
  val displayLabel = new Label {
    text = "0"
    style = "-fx-font-size: 30pt; -fx-text-fill: white; -fx-background-color: black; -fx-background-radius: 15px; -fx-padding: 20px; -fx-alignment: center-right;"
    minWidth = 320
  }

  def updateDisplay(): Unit = {
    if (calculator.getCurrentInput.isEmpty) {
      displayLabel.text = calculator.getCurrentResult.toString
    } else {
      displayLabel.text = calculator.getCurrentInput
    }
  }

  def showError(message: String): Unit = {
    displayLabel.text = message
  }

  val buttons = Seq(
    "C", "", "", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "0", ".", "="
  ).map { label =>
    val btn = new Button(label) {
      style = "-fx-font-size: 20pt; -fx-pref-width: 80px; -fx-pref-height: 80px; -fx-background-radius: 40px; -fx-text-fill: white;"
      if (label.isEmpty) {
        style = "-fx-background-color: transparent;"
        disable = true
      } else if ("0123456789".contains(label)) {
        style = style() + "; -fx-background-color: #505050;"
      } else if (label == "0") {
        style = style() + "; -fx-background-color: #505050; -fx-pref-width: 170px; -fx-background-radius: 40px;"
        minWidth = 160 // Ensure it spans two columns
      } else if (label == "C") {
        style = style() + "; -fx-background-color: #ff9500;"
      } else if (label == "=") {
        style = style() + "; -fx-background-color: #ff9500;"
      } else {
        style = style() + "; -fx-background-color: #d4d4d2; -fx-text-fill: black;"
      }
      onAction = _ => handleButtonClick(label)
    }
    btn
  }

  def handleButtonClick(label: String): Unit = label match {
    case digit if "0123456789".contains(digit) =>
      calculator.inputDigit(digit)
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
    case op if "+-*/".contains(op) =>
      calculator.setOperation(op)
      updateDisplay()
  }

  stage = new PrimaryStage {
    title = "Calculator"
    scene = new Scene {
      fill = Color.Black
      content = new VBox {
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
