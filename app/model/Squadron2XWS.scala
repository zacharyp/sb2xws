package model

import javax.inject.{Inject, Singleton}
import model.sb.Squadron
import model.xwingdata2.XWingDataLoader
import model.xws.{XWSPilot, XWSSquadron}
import play.Environment

@Singleton
class Squadron2XWS @Inject() (env: Environment, xwingData: XWingDataLoader) {

  // "rebelalliance", "galacticempire", "scumandvillainy", "firstorder", "resistance", "cis", "galacticrepublic"
  def xwsFaction(f: Int): String = {
    f match {
      case 1 => "rebelalliance"
      case 2 => "galacticempire"
      case 3 => "scumandvillainy"
      case 4 => "resistance"
      case 5 => "firstorder"
      case 6 => "cis"
      case 7 => "galacticrepublic"
    }
  }

  def convert(squadron: Squadron): XWSSquadron = {

    val factionId = squadron.faction.id

    val xwsPilots: Seq[XWSPilot] = squadron.deck.flatMap(ship => {
      val ffg_id = ship.pilot_card.ffg_id
      val xwsIdOption = xwingData.pilotMap.get(ffg_id)
      xwsIdOption.map{ case (xwsId, shipXWS) =>
        XWSPilot(
          name = xwsId,
          ship = shipXWS,
          upgrades = None,
          multisection_id = None,
          points = Some(ship.cost),
          vendor = None
        )
      }
    })

    XWSSquadron(
      faction = xwsFaction(squadron.faction.id),
      pilots = xwsPilots,
      name = Some(squadron.name),
      obstacles = None,
      damagedeck = None,
      points = Some(squadron.cost.toInt),
      vendor = None
    )
  }
}
