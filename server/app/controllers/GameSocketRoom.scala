package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.TaskListInMemoryModel 
import play.api.libs.json._
import akka.actor.Actor
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import actors.ChatActor
import akka.actor.Props
import actors.ChatManager

@Singleton 
class GameSocketRoom @Inject() (cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
    //val manager = system.actorOf(Props[GameManager], "Manager")

    def index = Action { implicit request =>
        Ok(views.html.multiplayer())
    }

    def socket = WebSocket.accept[String, String] { request =>
        println("Getting socket")
        ActorFlow.actorRef { out =>
          //GameActor.props(out, manager)
          ???
        }    
    }
}