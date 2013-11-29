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

