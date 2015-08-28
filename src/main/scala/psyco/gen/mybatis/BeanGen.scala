package psyco.gen.mybatis

import java.io.{File, PrintWriter}

import org.fusesource.scalate._
import psyco.JDBCInfo
import psyco.gen.db.{TableBuilder, TableInfo}
import psyco.util.FileTrait


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
  val dirMapperXml: File = new File(dirBase, "xml")

  /** init */
  if (!dirBase.mkdirs())
    throw new RuntimeException(s"Existed directory:${dirBase.getAbsolutePath}")
  dirMapperInterface.mkdir()
  dirMapperBean.mkdir()
  dirMapperXml.mkdir()

  def getMapperClassPath(className: String) = s"${packageMapper}.${className}"

  def getBeanClassPath(className: String) = s"${packageBean}.${className}"
}

object GenEngine extends Engine with FileTrait {

  def gen(): Unit = {
    def writeFileWithLog(f: File, s: String) = {
      println(s)
      writeFile(f, s)
    }
    tables.foreach(t => {
      println(s"[Table]:\t${t.name}--------------------")
      writeFileWithLog(new File(dirMapperBean, t.className.concat(".java")), bean(t))
      writeFileWithLog(new File(dirMapperInterface, t.className.concat("Mapper.java")), mapper(t))
      writeFileWithLog(new File(dirMapperXml, t.className.concat("Mapper.xml")), mapperXml(t))
    })
  }

  def test(): Unit = {
    def writeFileWithLog(f: File, s: String) = {
      println(s)
      //      writeFile(f, s)
    }
    println(tables)
//    tables.foreach(t => {
//      println(s"[Table]:\t${t.name}--------------------")
//      //      writeFileWithLog(new File(dirMapperBean, t.className.concat(".java")), bean(t))
//      //      writeFileWithLog(new File(dirMapperInterface, t.className.concat("Mapper.java")), mapper(t))
//      //      writeFileWithLog(new File(dirMapperXml, t.className.concat("Mapper.xml")), mapperXml(t))
//    })
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
  GenEngine.test()
  //    GenEngine.readFile("/Users/psyco/antxXXXXX.properties")
  //  GenEngine.writeFile(new File("/Users/psyco/tmp/fucker.txt"), "fuck you !")
}
