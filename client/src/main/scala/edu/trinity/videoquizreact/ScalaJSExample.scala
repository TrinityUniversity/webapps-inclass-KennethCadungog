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

    val canvas = document.getElementById("canvas").asInstanceOf[html.Canvas]
    val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    canvas.style.background = "#ff8"

    val windowWidth = dom.window.innerWidth
    val windowHeight = 600

    canvas.width = windowWidth.toInt
    canvas.height = windowHeight

    val player = new Player(0, 0, 0, 0, context)
    val dangerZones = (1 to 7).map(_ => new DangerZone(context)).toList

    def updateZone(): Unit = {
      dom.window.requestAnimationFrame((_: Double) => updateZone())

      context.clearRect(0, 0, windowWidth, windowHeight)

      player.update()

      dangerZones.foreach(_.update(context))
    }

    updateZone()

    val destinationX = Math.random() * (windowWidth - 50) + 50
    val destinationY = Math.random() * (windowHeight - 50) + 50
    val destination = new Circle(destinationX, destinationY, 50, "#FF0000", 1)
    destination.draw(context)

    dangerZones.foreach(_.draw(context))

    document.addEventListener("keydown", (key: dom.KeyboardEvent) => {
      key.code match {
        case "KeyW" => player.setVelocity(0, -5)
        case "KeyS" => player.setVelocity(0, 5)
        case "KeyA" => player.setVelocity(-5, 0)
        case "KeyD" => player.setVelocity(5, 0)
        case _ =>
      }
    })

    document.addEventListener("keyup", (key: dom.KeyboardEvent) => {
      key.code match {
        case "KeyW" | "KeyS" => player.setVelocity(0, 0)
        case "KeyA" | "KeyD" => player.setVelocity(0, 0)
        case _ =>
      }
    })
  }

  class Circle(var xpos: Double, var ypos: Double, val radius: Double, val color: String, val speed: Double) {
    var dx = 1 * speed
    var dy = 1 * speed

    def draw(context: dom.CanvasRenderingContext2D): Unit = {
      context.beginPath()
      context.lineWidth = 5
      context.arc(xpos, ypos, radius, 0, Math.PI * 2, false)
      context.stroke()
      context.closePath()
    }

    def update(context: dom.CanvasRenderingContext2D): Unit = {
      //draw(ScalaJSExample.this.context)
      draw(context)

      if ((xpos + radius) > dom.window.innerWidth || (xpos - radius) < 0) {
        dx = -dx
      }
      if ((ypos + radius) > dom.window.innerHeight || (ypos - radius) < 0) {
        dy = -dy
      }

      xpos += dx
      ypos += dy
    }
  }

  class Player(var xpos: Double, var ypos: Double, var vx: Double, var vy: Double, val context: dom.CanvasRenderingContext2D) {
    def update(): Unit = {
      xpos += vx
      ypos += vy
      xpos = Math.max(0, Math.min(xpos, dom.window.innerWidth - 50))
      ypos = Math.max(0, Math.min(ypos, dom.window.innerHeight - 50))
      context.fillRect(xpos, ypos, 50, 50)
    }

    def setVelocity(newVx: Double, newVy: Double): Unit = {
      vx = newVx
      vy = newVy
    }
  }

  class DangerZone(val context: dom.CanvasRenderingContext2D) extends Circle(
    Math.random() * (dom.window.innerWidth - 75) + 75,
    Math.random() * (dom.window.innerHeight - 75) + 75,
    50,
    "black",
    1
  )
  

  // def drawToCanvas(canvas: html.Canvas): Unit = {
  //   val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  //   context.fillRect(100,100,200,150)
  // }

}
