package com.example.calculator

class Calculator {
  private var currentResult: Double = 0.0
  private var currentInput: String = ""
  private var currentExpression: String = ""
  private var lastOperation: Option[String] = None

  def inputDigit(digit: String): Unit = {
    currentInput += digit
    currentExpression += digit
  }

  def inputDecimal(): Unit = {
    if (!currentInput.contains(".")) {
      currentInput += "."
      currentExpression += "."
    }
  }

  def clear(): Unit = {
    currentResult = 0.0
    currentInput = ""
    currentExpression = ""
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
          case "^" => Math.pow(currentResult, currentInput.toDouble)
        }
      case None =>
        currentResult = currentInput.toDouble
    }
    currentInput = ""
    lastOperation = None
    currentExpression = currentResult.toString
    currentResult
  }

  def setOperation(op: String): Unit = {
    if (currentInput.nonEmpty) {
      if (lastOperation.isDefined) calculate()
      else currentResult = currentInput.toDouble
    }
    lastOperation = Some(op)
    currentInput = ""
    currentExpression += s" $op "
  }

  def inputSine(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = Math.sin(Math.toRadians(currentInput.toDouble))
      currentInput = currentResult.toString
      currentExpression += s" sin($currentInput) "
    }
  }

  def inputCosine(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = Math.cos(Math.toRadians(currentInput.toDouble))
      currentInput = currentResult.toString
      currentExpression += s" cos($currentInput) "
    }
  }

  def inputTangent(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = Math.tan(Math.toRadians(currentInput.toDouble))
      currentInput = currentResult.toString
      currentExpression += s" tan($currentInput) "
    }
  }

  def inputLog(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = Math.log10(currentInput.toDouble)
      currentInput = currentResult.toString
      currentExpression += s" log($currentInput) "
    }
  }

  def inputLn(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = Math.log(currentInput.toDouble)
      currentInput = currentResult.toString
      currentExpression += s" ln($currentInput) "
    }
  }

  def inputSquareRoot(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = Math.sqrt(currentInput.toDouble)
      currentInput = currentResult.toString
      currentExpression += s" √($currentInput) "
    }
  }

  def inputPercentage(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = currentInput.toDouble / 100
      currentInput = currentResult.toString
      currentExpression += s" %($currentInput) "
    }
  }

  def inputFactorial(): Unit = {
    if (currentInput.nonEmpty) {
      currentResult = factorial(currentInput.toDouble.toInt)
      currentInput = currentResult.toString
      currentExpression += s" !($currentInput) "
    }
  }

  private def factorial(n: Int): Double = {
    if (n == 0) 1
    else n * factorial(n - 1)
  }

  def getCurrentInput: String = currentInput
  def getCurrentResult: Double = currentResult
  def getCurrentExpression: String = currentExpression
}
