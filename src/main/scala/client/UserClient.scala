package client

import cats.effect.*
import org.http4s.client.*
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.*
import org.http4s.circe.*
import org.http4s.circe.CirceEntityCodec._  // stellt EntityDecoder/Encoder für Circe bereit
import io.circe.generic.auto.*
import model.User

object UserClient :
  def fetchUsers(client: Client[IO]): IO[List[User]] =
    val uri = Uri.unsafeFromString("http://localhost:8080/users")
    client.expect[List[User]](uri)

  def putUser(client: Client[IO], user: User): IO[String] =
    val uri = Uri.unsafeFromString("http://localhost:8080/users")
    val request = Request[IO](Method.PUT, uri).withEntity(user)
    client.expect[String](request)

  def runClient: IO[List[User]] =
    EmberClientBuilder.default[IO].build.use: client =>
      fetchUsers(client).flatTap(users => IO(println(users)))

  def runPut: IO[String] =
    EmberClientBuilder.default[IO].build.use: client =>
      val user = User(4, "Mirijam")
      putUser(client, user).flatTap(response => IO(println(response)))
