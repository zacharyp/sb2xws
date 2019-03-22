package model

import javax.inject.{Inject, Singleton}
import model.sb.Squadron
import model.xwingdata2.XWingDataLoader
import model.xws.{XWSPilot, XWSSquadron}
import play.Environment

@Singleton
class Squadron2XWS @Inject() (env: Environment, xwingData: XWingDataLoader) {
  import Squadron2XWS._

  def convert(squadron: Squadron): XWSSquadron = {

    val factionId = squadron.faction.id

    val xwsPilots: Seq[XWSPilot] = squadron.deck.flatMap(ship => {
      val ffg_id = ship.pilot_card.id
      val xwsIdOption = xwingData.pilotMap.get(ffg_id)
      xwsIdOption.map{ case (xwsId, shipXWS) =>
        val shipUpgradeIds = ship.slots.map(slot => (slot.id, slot.upgrade_types.headOption) )

        val upgradeTuples: Seq[(String, String)] = shipUpgradeIds.flatMap { case (ffg, upgradeTypeOption) =>
          // Attempt to find the xws value in the upgrade Map
          val xwsOption = xwingData.upgradeMap.get(ffg)

          (xwsOption, upgradeTypeOption) match {
            case (Some(xws), Some(upgradeType)) => Some(xwsUpgrade(upgradeType) -> xws)
            case _ => None
          }
        }

        val groupedUpgrades = upgradeTuples.groupBy(_._1).map{ case (k, v) => k -> v.map(_._2)}

        val upgrades: Option[Map[String, Seq[String]]] = if (groupedUpgrades.isEmpty) {
          None
        } else {
          Some(groupedUpgrades)
        }

        XWSPilot(
          id = xwsId,
          ship = shipXWS,
          upgrades = upgrades,
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
      description = Option(squadron.description),
      obstacles = None,
      damagedeck = None,
      points = Some(squadron.cost.toInt),
      vendor = None
    )
  }
}

object Squadron2XWS {

  def xwsFaction(f: Int): String = {
    f match {
      case 1 => "rebelalliance"
      case 2 => "galacticempire"
      case 3 => "scumandvillainy"
      case 4 => "resistance"
      case 5 => "firstorder"
      case 6 => "separatistalliance"
      case 7 => "galacticrepublic"
    }
  }

  // https://squadbuilder.fantasyflightgames.com/api/app-metadata/
  def xwsUpgrade(u: Int): String = u match {
    case 1 => "ept"
    case 2 => "sensor"
    case 3 => "cannon"
    case 4 => "turret"
    case 5 => "torpedo"
    case 6 => "missile"
    case 7 => ""
    case 8 => "crew"
    case 10 => "amd"
    case 12 => "device"
    case 13 => "illicit"
    case 14 => "modification"
    case 15 => "title"
    case 16 => "gunner"
    case 17 => "force-power"
    case 18 => "configuration"
    case 19 => "tech"
    case 1000 => "tactical-relay"
  }

}
