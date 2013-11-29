package core

import spray.httpx.unmarshalling.{MalformedContent, Unmarshaller, Deserialized}
import spray.http._
import spray.json._
import spray.client.pipelining._
import akka.actor.{OneForOneStrategy, SupervisorStrategy, ActorRef, Actor}
import spray.http.HttpRequest
import scala.Some
import domain.{Place, User, Tweet}
import scala.io.Source
import akka.actor.SupervisorStrategy.Restart
import scala.util.Try
import spray.can.Http
import akka.io.IO

object TweetStreamerActor {
  val twitterUri = Uri("https://stream.twitter.com/1.1/statuses/filter.json")
}

class TweetStreamerActor(uri: Uri, processor: ActorRef) extends Actor {
  val io = IO(Http)(context.system)

  def receive: Receive = {
    case query: String =>
      val post = HttpEntity(ContentType(MediaTypes.`application/x-www-form-urlencoded`), s"track=$query")
      val rq = HttpRequest(HttpMethods.POST, uri = uri, entity = post)
      sendTo(io).withResponsesReceivedBy(self)(rq)
    case ChunkedResponseStart(_) =>
    case MessageChunk(entity, _) =>
    case _ =>
  }
}
