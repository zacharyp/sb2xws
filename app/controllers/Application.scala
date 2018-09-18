package controllers

import javax.inject.Inject
import model.Squadron2XWS
import model.sb.SquadronFormatter
import play.api.libs.ws._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}


class Application @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  val baseSBUrl = """https://squadbuilder.fantasyflightgames.com/api/squads/"""

  def translate(squadId: String): Action[AnyContent] = Action.async {
    val complexRequest: WSRequest = ws.url(s"$baseSBUrl$squadId")
      .addHttpHeaders("Accept" -> "application/json")
      .withRequestTimeout(10000.millis)

    import model.xws.XWSWrites._

    complexRequest.get().map(_.body)
      .map(SquadronFormatter.parseSB)
      .map(Squadron2XWS.convert)
      .map(s => Ok(Json.toJson(s)))
      .recoverWith{
        case _ => Future.successful(BadRequest(""))
      }
  }

}
