package core

import akka.actor.ActorSystem
import org.specs2.mutable.SpecificationLike
import akka.testkit.{TestActorRef, TestKit, ImplicitSender}
import domain.Tweet
import spray.http.{HttpRequest, Uri}
import spray.can.Http
import akka.io.IO

class TweetStreamerActorSpec extends TestKit(ActorSystem()) with SpecificationLike with ImplicitSender {
  sequential

  val port = 12345
  val tweetStream = TestActorRef(new TweetStreamerActor(Uri(s"http://localhost:$port/"), testActor) with TwitterAuthorization {
    def authorize = identity
  })

  "Getting all 'typesafe' tweets" >> {

    "should receive the tweets" in {
      val twitterApi = TwitterApi(port)
      tweetStream ! "typesafe"
      Thread.sleep(1000)
      val tweet = expectMsgType[Tweet]
      tweet.text mustEqual "Aggressive Ponytail #freebandnames"
      tweet.user.lang mustEqual "en"
      tweet.user.id mustEqual "137238150"
      tweet.place mustEqual None
      twitterApi.stop()
      success
    }
  }

}
