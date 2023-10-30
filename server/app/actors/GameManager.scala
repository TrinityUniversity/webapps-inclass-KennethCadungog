package actors

import akka.actor.Actor 
import akka.actor.ActorRef

class GameManager extends Actor {
    private var players = List.empty[ActorRef]
    private val positions = collection.mutable.Map[ActorRef, String]()
    println("Game Manager is reached")

    import GameManager._
    def receive = {
        case NewPlayer(player) => 
            println("NewPlayer reached")
            players ::= player
        case Coordinates(coord) => 
            positions(sender()) = coord
            val allCoords = positions.values.mkString("[", ",", "]")
            for (p <- players) p ! GameActor.SendCoordinates(allCoords)
        case m => println("Unhandled player in GameManager: " + m)
    }
}

object GameManager {
    case class NewPlayer(chatter: ActorRef) 
    case class Coordinates(coord: String)
}