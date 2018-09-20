package model.xws

import play.api.libs.json._

object XWSLoads {

  implicit val pReads = Json.reads[XWSPilot]
  implicit val pWrites = Json.writes[XWSPilot]
  implicit val sReads = Json.reads[XWSSquadron]
  implicit val sWrites = Json.writes[XWSSquadron]
}
