package core

import akka.actor.Actor
import domain.Tweet
import scala.io.Source
import scala.collection.parallel.ParSet

trait SentimentSets {
  def positiveWords: Set[String]
  def negativeWords: Set[String]
}

trait SentimentOutput {
  type Category = String

  def outputCount(values: List[Iterable[(Category, Int)]]): Unit
}

class SentimentAnalysisActor extends Actor {
  this: SentimentSets with SentimentOutput =>

  private val counts = collection.mutable.Map[Category, Int]()
  private val languages = collection.mutable.Map[Category, Int]()
  private val places = collection.mutable.Map[Category, Int]()

  private def update(data: collection.mutable.Map[Category, Int])(category: Category, delta: Int): Unit = data.put(category, data.getOrElse(category, 0) + delta)
  val updateCounts = update(counts)_
  val updateLanguages = update(languages)_
  val updatePlaces = update(places)_

  def receive: Receive = {
    case tweet: Tweet =>
      val positive: Int = if (positiveWords.exists(word => tweet.text.toLowerCase contains word)) 1 else 0
      val negative: Int = if (negativeWords.exists(word => tweet.text.toLowerCase contains word)) 1 else 0

      updateCounts("positive", positive)
      updateCounts("negative", negative)
      if (tweet.user.followersCount > 200) {
        updateCounts("positive.gurus", positive)
        updateCounts("negative.gurus", negative)
      }
      updateCounts("all", 1)
      updateLanguages(tweet.user.lang, 1)
      updatePlaces(tweet.place.toString, 1)

      outputCount(List(counts, places, languages))
  }
}