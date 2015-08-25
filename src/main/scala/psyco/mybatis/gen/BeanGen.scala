package psyco.mybatis.gen

import java.io.File

import org.apache.ddlutils.model.Table
import org.fusesource.scalate._
import psyco.DdlUtilsDemo
import psyco.util.CaseUtil

/**
 * Created by lipeng on 15/8/24.
 */

trait Engine {
  val engine = new TemplateEngine(List(new File("")), "production")
  val tables = DdlUtilsDemo.database.getTables
}

case class FieldInfo(javaType: String, name: String ,description:Option[String])

case class Bean(className: String, packageName: String, fields: List[FieldInfo])

object BeanBuilder {

  def fromTable(table: Table, packageName: String): Bean = new Bean(
    CaseUtil.underscore2camelUppercase(table.getName),
    packageName,
    table.getColumns.map(c => FieldInfo(c.getType, c.getName,None)).toList)
}

object BeanGen extends Engine {

  def gen(): Unit = {
    tables.foreach(t => {
      val tableName = t.getName
      println(s"${tableName}")
      val output = engine.layout("Bean.jade", Map("table" -> t))
      println(output)
    })
    val output = engine.layout("Bean.jade", Map("user" -> "fucker"))
    //    val tables = engine.layout("Bean.jade", Map("tables" -> DdlUtilsDemo.database.getTables))
    println(output)
  }

  tables.foreach(t => {
    val tableName = t.getName
    println(s"----------------${tableName}")
    t.getColumns.foreach(c => {
      println(c.getType)
    })
  })
}
