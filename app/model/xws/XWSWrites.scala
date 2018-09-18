package model.xws

import play.api.libs.json._

object XWSWrites {

  implicit val pWrites = Json.writes[Pilot]
  implicit val sWrites = Json.writes[Squadron]

}
