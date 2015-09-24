package psyco.gen.mybatis

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

/**
 * Created by lipeng on 15/8/28.
 */

case class Config4mybatis(jdbc: Jdbc, srcRootDirectory: String, packageRoot: String, mapperXmlDirectory: Option[String]) {}

object Config4mybatis {
  val instance: Config4mybatis = ConfigFactory.load().as[Config4mybatis]("mybatis")
}


case class Jdbc(url: String, user: String, password: String)
