import cats.effect.IO
import cats.effect.unsafe.IORuntime

import io.circe.Json
import io.circe.Encoder
import io.circe.syntax._ // Required for _.asJson

import example.client.models.{given Encoder[?]}
import example.client.models.*
import example.client.apis.DefaultApiEndpointsImpl

@main def main(): Unit =
  val left = MyLeft(
    left = Some("Hello, World"),
    myValue = Some(3.14),
    // typeType = MyLeftType.MyLeft,  // If using mapping
    `type` = MyLeftType.MyLeft,
  )
  val right = MyRight(right = Some("Test"), myValue = Some("String value"))
  val obj =
    MyObject(`@id` = 42, myValue = Some("Test entry"), feature = Some(left))
  val obj2 = obj.copy(feature = Some(right))

  // Print objects
  println(s"left: ${left.asJson}")
  println(s"right: ${right.asJson}")
  println(s"obj: '${obj.asJson}'")
  println(s"obj2: '${obj2.asJson}'")

  // Test request
  given runtime: IORuntime = cats.effect.unsafe.IORuntime.global
  testSend(obj).unsafeRunSync()
  testSend(MyObject(`@id` = 123, feature = Some(right))).unsafeRunSync()

def testSend(obj: MyObject): IO[MyObject] =
  import org.http4s.implicits.uri
  import org.http4s.ember.client.EmberClientBuilder

  EmberClientBuilder.default[IO].build.use { httpClient =>
    val client = DefaultApiEndpointsImpl(
      uri"http://localhost:8000",
      httpClient = httpClient,
    )
    val resp = client.echoPost(obj)
    resp.map(r =>
      println("Request completet:")
      println(s"POST(${obj}) returned:")
      println(s"     ${r}")
      println(r.asJson)
      r,
    )
  }
