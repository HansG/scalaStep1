import cats.effect.IO
import munit.CatsEffectSuite
import org.http4s.*
import org.http4s.implicits.*
import api.UserRoutes

class UserRoutesSpec extends CatsEffectSuite:
  test("GET /users returns list of users") {
    val request = Request[IO](Method.GET, uri"/users")
    val response = UserRoutes.routes.orNotFound.run(request)

    assertIO(response.flatMap(_.as[String]), """[{"id":1,"name":"Hans"},{"id":2,"name":"Anna"}]""")
  }