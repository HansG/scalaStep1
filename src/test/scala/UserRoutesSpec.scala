import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.*
import org.http4s.implicits.*
import org.http4s.headers.`Content-Type`
import org.http4s.MediaType
import api.UserRoutes


class UserRoutesSpec extends CatsEffectSuite:
  test("GET /users returns list of users") {
    val request = Request[IO](Method.GET, uri"/users")
    val response = UserRoutes.routes.orNotFound.run(request)

    // lese die Antwort als String, gib ihn aus und behalte den Wert für die Assertion
    val resp = response
      .flatMap(_.as[String])
      .flatTap(s => IO.println(s))

    assertIO(resp, """[{"id":1,"name":"Hans"},{"id":2,"name":"Anna"}]""")
  }

  test("POST /users adds a new user") {
    val newUserJson = """{"id":3,"name":"Clara"}"""
    val request = Request[IO](Method.POST, uri"/users")
      .withEntity(newUserJson)
      .withHeaders(`Content-Type`(MediaType.application.json))

    val response = UserRoutes.routes.orNotFound.run(request)

    // lese die Antwort als String, gib ihn aus und behalte den Wert für die Assertion
    val resp = response
      .flatMap(_.as[String])
      .flatTap(s => IO.println(s))

    assertIO(resp, """{"id":3,"name":"Clara"}""")
  }
