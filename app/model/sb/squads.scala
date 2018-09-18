package model.sb

//case class Kwargs(
//  pk: Option[Int],
//  name: String,
//  side_effect_name: Option[String]
//)

case class Restriction(
  `type`: String,
  kwargs: Map[String, Any]
)

//case class BehaviourData(
//  base_action_id: Int,
//  related_action_id: Option[Int],
//  base_action_side_effect: Option[String],
//  related_action_side_effect: Option[String]
//)

case class Behaviour(
  data: Map[String, Any],
  `type`: String
)

case class Stat(
  id: Int,
  statistic_id: Int,
  value: String,
  recurring: Boolean
)

case class Action(
  id: Int,
  base_action_id: Int,
  related_action_id: Option[Int],
  base_action_side_effect: Option[Int],
  related_action_side_effect: Option[String]
)

case class Pilot(
  id: Int,
  faction_id: Int,
  card_set_ids: Seq[Int],
  card_type_id: Int,
  available_actions: Seq[Action],
  statistics: Seq[Stat],
  available_upgrades: Seq[Int],
  image: String,
  card_image: String,
  name: String,
  subtitle: String,
  ability_text: String,
  cost: String,
  ship_ability_text: String,
  ship_size: Int,
  force_side: Option[Int],
  initiative: Int,
  ffg_id: String,
  is_unique: Boolean,
  ship_type: Int
)


case class Slot(

  id: Int,
  card_set_ids: Seq[Int],
  card_type_id: Int,
  weapon_no_bonus: Boolean,
  weapon_range: Option[Int],
  upgrade_types: Seq[Int],
  image: String,
  name: String,
  ability_text: String,
  cost: String,
  is_unique: Boolean,
  available_actions: Seq[Action],
  statistics: Seq[Stat],
  restrictions_raw: Option[String],
  restrictions: Seq[Seq[Restriction]],
  card_image: String,
  behaviours: Seq[Behaviour]
)

case class Ship(
  pilot_card: Pilot,
  slots: Seq[Slot],
  cost: Int
)

case class GameFormat(
  id: String,
  name: String,
  description: Option[String],
  game_mode: Int,
  maximum_squad_cost: Int,
  created_by: Option[String],
  created_at: Option[String],
  factions: Seq[Int]
)

case class Faction(
  id: Int,
  description: String,
  icon: Option[String],
  banner_mobile: Option[String],
  banner: Option[String],
  ship_art: Option[String],
  ship_art_mobile: Option[String]
)

case class GameMode(
  id: Int,
  name: String,
  description: Option[String],
  art: Option[String]
)

case class Squadron(
  id: String,
  name: String,
  description: String,
  deck: Seq[Ship],
  game_format: GameFormat,
  faction: Faction,
  game_mode: GameMode,
  cost: String
)

