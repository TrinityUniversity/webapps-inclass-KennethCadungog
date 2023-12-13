package edu.trinity.videoquizreact

import shared.SharedMessages
import org.scalajs.dom

import org.scalajs.dom.document 
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.html 

import slinky.core._
import slinky.web.ReactDOM
import slinky.web.html._

object ScalaJSExample {

  def main(args: Array[String]): Unit = {
    println("Something else.")
    

    val drawing = document.getElementById("canvas")
    if (drawing != null) {
      new Task10
    }

    val drawing2 = document.getElementById("canvas2")
    if (drawing2 != null) {
      Drawing
    }
    
  }

}
