package core

import javax.crypto
import java.nio.charset.Charset
import spray.http.{MediaTypes, ContentType, HttpEntity, HttpRequest}
import spray.http.HttpHeaders.RawHeader
import org.parboiled.common.Base64
import scala.collection.immutable.TreeMap
import java.net.URLEncoder

object OAuth {
  case class Consumer(key: String, secret: String)
  case class Token(value: String, secret: String)

  def oAuthAuthorizer(consumer: Consumer, token: Token): HttpRequest => HttpRequest = {
    identity
  }

}
