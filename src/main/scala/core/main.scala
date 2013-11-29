package core

import akka.actor.{Props, ActorSystem}
import scala.annotation.tailrec
import spray.can.Http
import akka.io.IO

object Main extends App {
  import Commands._

  @tailrec
  private def commandLoop(): Unit = {
    Console.readLine() match {
      case _                   => println("WTF??!!")
    }

    commandLoop()
  }

  // start processing the commands
  commandLoop()

}

object Commands {

  val QuitCommand   = "quit"
  val TrackCommand = "track (.*)".r

}
