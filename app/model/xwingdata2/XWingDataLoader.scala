package model.xwingdata2

import java.io.File
import java.nio.file.Paths

import javax.inject.{Inject, Singleton}
import org.apache.commons.io.FileUtils
import play.Environment
import play.api.libs.json._

import scala.collection.JavaConverters._
import scala.io.Source
import scala.util.Try

object XWingDataLoader {

  implicit val sideReads: Reads[XWD2Side] = Json.reads[XWD2Side]
  implicit val uReads: Reads[XWD2Upgrade] = Json.reads[XWD2Upgrade]
  implicit val pReads: Reads[XWD2Pilot] = Json.reads[XWD2Pilot]
  implicit val sReads: Reads[XWD2Ship] = Json.reads[XWD2Ship]
}

@Singleton
class XWingDataLoader @Inject()(env: Environment) {
  import XWingDataLoader._

  // Extracts all the .json files in a directory into list of strings per file
  private def extractStrings(relativePath: String): List[String] = {
    val fileDir: File = Paths.get(env.resource(relativePath).toURI).toFile
    val files = FileUtils.listFiles(fileDir, Array("json"), false).asScala
    files.map(file => Source.fromFile(file).getLines().mkString("")).toList
  }

  private def extractShips(relativePath: String): List[XWD2Ship] = {
    extractStrings(relativePath).flatMap(str => {
      val t = Try(Json.parse(str).validate[XWD2Ship].get)
//      if (t.isFailure) {
//        println(s"failed to parse: \n$str\n") // first order and resistance for now
//      }
      t.toOption
    })
  }

  private val rebelPilots: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/rebel-alliance/")
  private val imperialPilots: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/galactic-empire/")
  private val scumPilots: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/scum-and-villainy/")
  private val resistancePilots: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/resistance/")
  private val firstOrderPilots: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/first-order/")
//  private val separatistAlliance: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/separatist-alliance/")
//  private val galacticRepublic: List[XWD2Ship] = extractShips("/xwing-data2/data/pilots/galactic-republic/")

  /*
   * Map with key of ffg_id to value (for that pilot) of a tuple of (xwsId, shipXWS), for ease of lookup for translation
   */
  lazy val pilotMap: Map[Int, (String, String)] = {
    val allShips = rebelPilots ++ imperialPilots ++ scumPilots ++ resistancePilots ++ firstOrderPilots
//    ++ separatistAlliance ++ galacticRepublic

    allShips.flatMap(ship => {
      ship.pilots.map(pilot => pilot.ffg -> (pilot.xws, ship.xws))
    }).toMap
  }

  private def extractUpgrades(relativePath: String): List[XWD2Upgrade] = {
    extractStrings(relativePath).flatMap(str => {
      val t = Try(Json.parse(str).validate[List[XWD2Upgrade]].get)
      if (t.isFailure) {
        println(s"failed to parse: \n$str\n")
      }
      t.getOrElse(List.empty)
    })
  }

  private val upgrades: List[XWD2Upgrade] = extractUpgrades("/xwing-data2/data/upgrades/")

  lazy val upgradeMap: Map[Int, String] = {
    upgrades.flatMap(upgrade => {
      upgrade.ffg().map(ffg => ffg -> upgrade.xws)
    }).toMap
  }

}
