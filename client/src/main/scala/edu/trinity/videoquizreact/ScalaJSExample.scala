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
    // dom.document.getElementById("scalajsShoutOut").textContent = SharedMessages.itWorks
    println("Call the react stuff.")
    
    ReactDOM.render(
      h1("Hello, world!"),
      dom.document.getElementById("root")
    )

    
  }

  

  // def drawToCanvas(canvas: html.Canvas): Unit = {
  //   val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  //   context.fillRect(100,100,200,150)
  // }

}
