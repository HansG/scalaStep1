package api

import cats.effect.{IO, Ref}
import cats.implicits.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import io.circe.generic.auto.*
import model.User

object UserRoutes :
  // Use mutable reference for thread-safe operations
  private val usersRef = Ref.unsafe[IO, List[User]](List(User(1, "Hans"), User(2, "Anna")))

  given EntityEncoder[IO, List[User]] = jsonEncoderOf[IO, List[User]]
  given EntityEncoder[IO, User] = jsonEncoderOf[IO, User]
  given EntityDecoder[IO, User] = jsonOf[IO, User]

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case GET -> Root / "users" =>
      usersRef.get.flatMap(users => Ok(users))
    
    case req @ POST -> Root / "users" =>
      req.as[User].flatMap { user =>
        usersRef.update(_ :+ user) *> Created(user)
      }
