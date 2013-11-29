package core

import akka.actor.Actor
import domain.Tweet
import scala.io.Source
import scala.collection.parallel.ParSet

class SentimentAnalysisActor extends Actor {
  def receive: Receive = {
    case t: Tweet => println(t)
  }
}