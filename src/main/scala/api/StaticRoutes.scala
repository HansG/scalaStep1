package api

import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.headers.`Content-Type`
import scala.io.Source

object StaticRoutes:
  // Lade HTML-Content aus resources/index.html
  private def loadHtmlContent: String =
    val source = Source.fromResource("index.html")
    try source.mkString
    finally source.close()

  private lazy val htmlContent: String = loadHtmlContent

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case GET -> Root =>
      Ok(htmlContent, `Content-Type`(MediaType.text.html))
