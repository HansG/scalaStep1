import cats.effect.{IO, IOApp}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.HttpApp
import com.comcast.ip4s.Port
import api.{UserRoutes, StaticRoutes}
import com.comcast.ip4s.Host
import cats.implicits.toSemigroupKOps

object Main extends IOApp.Simple:
  // Kombiniere Static- und User-Routes
  private val routes = StaticRoutes.routes <+> UserRoutes.routes
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