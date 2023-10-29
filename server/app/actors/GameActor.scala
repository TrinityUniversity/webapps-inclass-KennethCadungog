package actors 

import akka.actor.Actor 
import akka.actor.Props
import akka.actor.ActorRef 

class GameActor(out: ActorRef, manager: ActorRef) extends Actor {
    manager ! GameManager.NewPlayer(self)

    import GameActor._
    def receive = {
        case s: String => manager ! GameManager.Message(s)
        case SendMessage(msg) => out ! msg 
        case m => println("Unhandled player: " + m)
    }
}

object GameActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new GameActor(out, manager))

    case class SendMessage(msg: String)
}