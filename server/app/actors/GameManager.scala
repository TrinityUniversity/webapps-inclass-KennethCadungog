package actors

import akka.actor.Actor 
import akka.actor.ActorRef

class GameManager extends Actor {
    private var players = List.empty[ActorRef]

    import GameManager._
    def receive = {
        case NewPlayer(chatter) => players ::= player
        case Message(msg) => for (c <- chatters) c ! GameActor.SendMessage(msg)
        case m => println("Unhandled message in ChatManager: " + m)
    }
}

object GameManager {
    case class NewPlayer(chatter: ActorRef) 
    case class Message(msg: String)
}