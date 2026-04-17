import cats.effect.{IO, IOApp}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.HttpApp
import com.comcast.ip4s.Port
import api.UserRoutes
import com.comcast.ip4s.Host

object Main extends IOApp.Simple:
  // einfache Beispiel‑Route; ersetze durch deine echten routes
  private val routes = UserRoutes.routes
/*   private val routes = HttpRoutes.of[IO]:
    case GET -> Root / "api" / "health" => Ok("ok")
 */
  private val httpApp: HttpApp[IO] = routes.orNotFound

  val run: IO[Unit] =
    EmberServerBuilder.default[IO]
      .withHost(Host.fromString("0.0.0.0").get)
      .withPort(Port.fromInt(8080).get)
      .withHttpApp(httpApp)
      .build
      .use(_ => IO.never)