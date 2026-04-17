package api

import cats.effect.IO
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import io.circe.generic.auto.*
import model.User

object UserRoutes :
  private val users = List(User(1, "Hans"), User(2, "Anna"))

  given EntityEncoder[IO, List[User]] = jsonEncoderOf[IO, List[User]]

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case GET -> Root / "users" =>
      Ok(users)
