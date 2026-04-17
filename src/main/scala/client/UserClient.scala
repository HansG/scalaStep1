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
    val uri = Uri.unsafeFromString("http://localhost:8080/api/users")
    client.expect[List[User]](uri)

  def runClient: IO[Unit] =
    EmberClientBuilder.default[IO].build.use: client =>
      fetchUsers(client).flatMap(users => IO(println(users)))