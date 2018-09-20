package model.xwingdata2

case class XWD2Pilot(
  name: String,
  ffg_id: String,
  xws: String
)


case class XWD2Ship(
  name: String,
  faction: String,
  pilots: List[XWD2Pilot]
){
  def shipXWS(): String = name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase
}
