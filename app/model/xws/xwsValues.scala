package model.xws

case class XWSPilot(
  id: String,
  ship: String,
  upgrades: Option[Map[String, Seq[String]]],
  multisection_id: Option[Int],
  points: Option[Int],
  vendor: Option[Map[String, Map[String, String]]]
)

case class XWSSquadron(
  faction: String,
  pilots: Seq[XWSPilot],
  name: Option[String],
  description: Option[String],
  obstacles: Option[Seq[String]],
  damagedeck: Option[String],
  points: Option[Int],
  vendor: Option[Map[String, Map[String, String]]]
)
