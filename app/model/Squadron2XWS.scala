package model

import model.sb.Squadron
import model.xws.{Pilot => XWSPilot, Squadron => XWSSquadron}

object Squadron2XWS {

  def xwsFaction(f: Int): String = {
    f match {
      case 1 => "rebel"
      case 2 => "imperial"
      case 3 => "scum"
    }
  }

  def convert(squadron: Squadron): XWSSquadron = {
    XWSSquadron(
      faction = xwsFaction(squadron.faction.id),
      pilots = Seq.empty[XWSPilot],
      name = Some(squadron.name),
      obstacles = None,
      damagedeck = None,
      points = Some(squadron.cost.toInt),
      vendor = None
    )
  }
}
