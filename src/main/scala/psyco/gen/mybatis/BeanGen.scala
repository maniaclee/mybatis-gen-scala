package psyco.gen.mybatis

import java.io.{File, PrintWriter}

import org.fusesource.scalate._
import psyco.JDBCInfo
import psyco.gen.db.{TableBuilder, TableInfo}
import psyco.util.FileTrait

import scala.io.Source

/**
 * Created by lipeng on 15/8/24.
 */
trait Engine {
  val config = Config4mybatis.instance
  val engine = new TemplateEngine(List(new File("")), "production")
  val jdbc: JDBCInfo = new JDBCInfo(config.jdbc.url, config.jdbc.user, config.jdbc.password)
  val tables = TableBuilder.fromJDBCInfo(jdbc)
  /** package */
  val packageBase = config.packageRoot
  val packageMapper = packageBase.concat(".mapper")
  val packageBean = packageBase.concat(".bean")
  /** directory */
  val dirBase: File = new File(config.srcRootDirectory, packageBase.replace(".", "/"))
  val dirMapperInterface: File = new File(dirBase, "mapper")
  val dirMapperBean: File = new File(dirBase, "bean")

  /** init */
  if (!dirBase.mkdirs())
    throw new RuntimeException(s"Existed directory:${dirBase.getAbsolutePath}")

  def getMapperClassPath(className: String) = s"${packageMapper}.${className}"

  def getBeanClassPath(className: String) = s"${packageBean}.${className}"
}

object GenEngine extends Engine with FileTrait {

  def gen(): Unit = {
    tables.foreach(t => {
      println("--------------------")
      println(mapperXml(t))
    })
  }

  def mapper(table: TableInfo): String =
    engine.layout("MapperInterface.ssp", Map(
      "table" -> table,
      "packageName" -> packageMapper
    ))

  def bean(table: TableInfo): String =
    engine.layout("Bean.ssp", Map(
      "table" -> table,
      "packageName" -> packageBean
    ))

  def mapperXml(table: TableInfo) =
    engine.layout("MapperXML.ssp", Map(
      "table" -> table,
      "mapperClass" -> getMapperClassPath(table.className),
      "beanClass" -> getBeanClassPath(table.className),
      "mapperClass" -> getMapperClassPath(table.className)
    ))
}

object Main extends App {
  GenEngine.gen()
  //    GenEngine.readFile("/Users/psyco/antxXXXXX.properties")
  //  GenEngine.writeFile(new File("/Users/psyco/tmp/fucker.txt"), "fuck you !")
}
