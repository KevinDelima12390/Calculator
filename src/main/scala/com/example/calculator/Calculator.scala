package com.example.calculator

class Calculator {
  private var currentResult: Double = 0.0
  private var currentInput: String = ""
  private var lastOperation: Option[String] = None

  def inputDigit(digit: String): Unit = {
    currentInput += digit
  }

  def inputDecimal(): Unit = {
    if (!currentInput.contains(".")) {
      currentInput += "."
    }
  }

  def clear(): Unit = {
    currentResult = 0.0
    currentInput = ""
    lastOperation = None
  }

  def calculate(): Double = {
    if (currentInput.isEmpty) {
      throw new IllegalArgumentException("Input is empty")
    }

    lastOperation match {
      case Some(op) =>
        currentResult = op match {
          case "+" => currentResult + currentInput.toDouble
          case "-" => currentResult - currentInput.toDouble
          case "*" => currentResult * currentInput.toDouble
          case "/" =>
            val divisor = currentInput.toDouble
            if (divisor == 0) throw new ArithmeticException("Division by zero")
            currentResult / divisor
        }
      case None =>
        currentResult = currentInput.toDouble
    }
    currentInput = ""
    lastOperation = None
    currentResult
  }

  def setOperation(op: String): Unit = {
    if (currentInput.nonEmpty) {
      if (lastOperation.isDefined) calculate()
      else currentResult = currentInput.toDouble
    }
    lastOperation = Some(op)
    currentInput = ""
  }

  def getCurrentInput: String = currentInput
  def getCurrentResult: Double = currentResult
}
