package model.xwingdata2

import java.io.File

import javax.inject.{Inject, Singleton}
import org.apache.commons.io.FileUtils
import play.Environment

import scala.collection.JavaConverters._
import scala.io.Source
import play.api.libs.json._

import scala.util.Try

object XWingDataLoader {
  implicit val pReads = Json.reads[XWD2Pilot]
  implicit val pWrites = Json.writes[XWD2Pilot]
  implicit val sReads = Json.reads[XWD2Ship]
  implicit val sWrites = Json.writes[XWD2Ship]
}

@Singleton
class XWingDataLoader @Inject()(env: Environment) {
  import XWingDataLoader._

  private def pilots(relativePath: String): List[XWD2Ship] = {
    val pilotDir: File = env.getFile(relativePath)
    val pilotFiles = FileUtils.listFiles(pilotDir, Array("json"), false).asScala

    pilotFiles
      .map(file => Source.fromFile(file).getLines().mkString(""))
      .flatMap(str => {

        val t = Try(Json.parse(str).validate[XWD2Ship].get)

        if (t.isFailure) {
          println(s"failed to parse: \n$str\n")
        }

        t.toOption
      }).toList
  }

  private val rebelPilots: List[XWD2Ship] = pilots("/xwing-data2/data/pilots/rebel-alliance/")
  private val imperialPilots: List[XWD2Ship] = pilots("/xwing-data2/data/pilots/galactic-empire/")
  private val scumPilots: List[XWD2Ship] = pilots("/xwing-data2/data/pilots/scum-and-villainy/")
  private val resistancePilots: List[XWD2Ship] = pilots("/xwing-data2/data/pilots/resistance/")
  private val firstOrderPilots: List[XWD2Ship] = pilots("/xwing-data2/data/pilots/first-order/")

  private def shipsToMap(pilots: List[XWD2Ship]): Map[String, (String, String)] = {
    pilots.flatMap(ship => {
      ship.pilots.map(pilot => pilot.ffg_id -> (pilot.xws, ship.shipXWS()))
    }).toMap
  }

  /*
   * Map with key of ffg_id to value (for that pilot) of a tuple of (xwsId, shipXWS), for ease of lookup for translation
   */
  lazy val pilotMap: Map[String, (String, String)] = {
    shipsToMap(rebelPilots) ++
      shipsToMap(imperialPilots) ++
      shipsToMap(scumPilots) ++
      shipsToMap(resistancePilots) ++
      shipsToMap(firstOrderPilots)
  }
}
