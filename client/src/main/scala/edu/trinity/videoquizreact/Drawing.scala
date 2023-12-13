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

object Drawing {
    // val canvas = document.getElementById("canvas2").asInstanceOf[html.Canvas]
    // val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    // canvas.style.background = "#ff8"

    // var windowWidth = dom.window.innerWidth
    // val windowHeight = 600
    // canvas.width = windowWidth.toInt
    // canvas.height = windowHeight

    // var drawingPaths: List[DrawingPath] = List.empty

    // case class DrawingPath(points: List[Point], color: String)
    // case class Point(x: Double, y: Double)

    // canvas.focus()

    // def updateCanvas(): Unit = {
    //     context.clearRect(0, 0, windowWidth, windowHeight)
    //     drawingPaths.foreach { path =>
    //         context.beginPath()
    //         context.strokeStyle = path.color
    //         context.lineWidth = 3
    //         path.points.sliding(2).foreach {
    //             case List(p1, p2) =>
    //                 context.moveTo(p1.x, p1.y)
    //                 context.lineTo(p2.x, p2.y)
    //                 context.stroke()
    //         }
    //         context.closePath()
    //     }
    // }

    // updateCanvas()

    // document.addEventListener("keydown", (event: dom.KeyboardEvent) => {
    //     val step = 5
    //     val color = "#00FF00"

    //     val x = canvas.width
    //     val y = canvas.height

    //     event.code match {
    //         case "ArrowUp" => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x, y - step)), color)
    //         case "ArrowDown" => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x, y + step)), color)
    //         case "ArrowLeft" => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x - step, y)), color)
    //         case "ArrowRight" => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x + step, y)), color)
    //         case _ =>
    //     }
    //     updateCanvas()
    // })

  //def want = {}
  val canvas = document.getElementById("canvas2").asInstanceOf[html.Canvas]
  val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  context.fillStyle = "#ff8"
  context.fillRect(0, 0, canvas.width, canvas.height)

  var drawingPaths: List[DrawingPath] = List.empty

  case class DrawingPath(points: List[Point], color: String)
  case class Point(x: Double, y: Double)

  canvas.focus()

  def updateCanvas(): Unit = {
    context.fillStyle = "#ff8"
    context.fillRect(0, 0, canvas.width, canvas.height)

    drawingPaths.foreach { path =>
      path.points.sliding(2).foreach {
        case List(Point(x1, y1), Point(x2, y2)) =>
          context.beginPath()
          context.strokeStyle = path.color
          context.lineWidth = 3
          context.moveTo(x1, y1)
          context.lineTo(x2, y2)
          context.stroke()
          context.closePath()
        case _ => // Handle other cases if necessary
      }
    }
  }

  document.addEventListener("keydown", (event: dom.KeyboardEvent) => {
    val step = 5
    val color = "#00FF00"

    // val x = drawingPaths.lastOption.map(_.points.last.x).getOrElse(0)
    // val y = drawingPaths.lastOption.map(_.points.last.y).getOrElse(0)
    val lastPointOption = drawingPaths.lastOption.flatMap(_.points.lastOption)

    val (x, y) = lastPointOption match {
      case Some(lastPoint) => (lastPoint.x, lastPoint.y)
      case None            => (0.0, 0.0)  // Default values if no previous points
    }

    event.code match {
      case "ArrowUp"    => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x, y - step)), color)
      case "ArrowDown"  => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x, y + step)), color)
      case "ArrowLeft"  => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x - step, y)), color)
      case "ArrowRight" => drawingPaths = drawingPaths :+ DrawingPath(List(Point(x + step, y)), color)
      case _            =>
    }
    updateCanvas()
  })


}

