package psyco.mybatis.gen

import java.io.File

import org.apache.ddlutils.model.Table
import org.fusesource.scalate._
import psyco.DdlUtilsDemo

/**
 * Created by lipeng on 15/8/24.
 */

trait Engine {
  val engine = new TemplateEngine(List(new File("")), "production")
  val tables = DdlUtilsDemo.database.getTables
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
}
