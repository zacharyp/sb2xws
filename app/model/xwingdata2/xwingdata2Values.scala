package model.xwingdata2

case class XWD2Side(ffg: Option[Int])

case class XWD2Upgrade(
  xws: String,
  sides: Seq[XWD2Side]
) {
  def ffg(): Option[Int] = sides.headOption.flatMap(_.ffg)
}

case class XWD2Pilot(
  name: String,
  ffg: Int,
  xws: String
)

case class XWD2Ship(
  name: String,
  faction: String,
  pilots: List[XWD2Pilot]
){
  def shipXWS(): String = name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase
}
