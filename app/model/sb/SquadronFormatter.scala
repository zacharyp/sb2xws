package model.sb

import org.json4s._
import org.json4s.jackson.JsonMethods._

object SquadronFormatter {
  implicit val formats = DefaultFormats

  def parseSB(sb: String): Squadron = {
    parse(sb).extract[Squadron]
  }
}
