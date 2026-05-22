package api

import cats.effect.{IO, Ref}
import cats.implicits.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import io.circe.generic.auto.*
import model.User

object UserRoutes :
  // Use Map[Int, User] for efficient lookup by ID
  private val initialUsers = Map(
    1 -> User(1, "Hans"),
    2 -> User(2, "Anna", Some("Müller")),
    3 -> User(3, "Peter", Some("Schmidt"))
  )
  
  private val usersRef = Ref.unsafe[IO, Map[Int, User]](initialUsers)

  given EntityEncoder[IO, List[User]] = jsonEncoderOf[IO, List[User]]
  given EntityEncoder[IO, User] = jsonEncoderOf[IO, User]
  given EntityDecoder[IO, User] = jsonOf[IO, User]

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case GET -> Root / "users" =>
      usersRef.get.flatMap(users => Ok(users.values.toList))
    
    case req @ POST -> Root / "users" =>
      req.as[User].flatMap { user =>
        usersRef.update(_.updated(user.id, user)) *> Created(user)
      }
    
    case DELETE -> Root / "users" / IntVar(id) =>
      usersRef.get.flatMap { users =>
        users.get(id) match
          case Some(user) =>
            usersRef.update(_ - id) *> Ok(s"""{"message": "User ${user.name} deleted"}""")
          case None =>
            NotFound(s"""{"error": "User with ID $id not found"}""")
      }
