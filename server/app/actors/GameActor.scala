package actors 

import akka.actor.Actor 
import akka.actor.Props
import akka.actor.ActorRef 

class GameActor(out: ActorRef, manager: ActorRef) extends Actor {
    manager ! GameManager.NewPlayer(self)
    println("new actor")
    
    import GameActor._
    def receive = {
        case s: String => 
            println(s)
            manager ! GameManager.Coordinates(s)
        case SendCoordinates(coord) => 
            out ! coord 
            println("SendCoordinates is reached")
        case m => println("Unhandled player: " + m)
        
    }
    
}

object GameActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new GameActor(out, manager))

    case class SendCoordinates(coord: String)
}