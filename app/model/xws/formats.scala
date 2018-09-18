package model.xws

case class Pilot(
  name: String,
  ship: String,
  upgrades: Option[Map[String, Seq[String]]],
  multisection_id: Option[Int],
  points: Option[Int],
  vendor: Option[Map[String, Map[String, String]]]
)

case class Squadron(
  faction: String,
  pilots: Seq[Pilot],
  name: Option[String],
  obstacles: Option[Seq[String]],
  damagedeck: Option[String],
  points: Option[Int],
  vendor: Option[Map[String, Map[String, String]]]
)
