package psyco.mybatis.gen

import java.io.{File, PrintWriter}

import org.fusesource.scalate._
import psyco.JDBCInfo
import psyco.db.TableBuilder

import scala.io.Source

/**
 * Created by lipeng on 15/8/24.
 */

trait Engine {
  val engine = new TemplateEngine(List(new File("")), "production")
  val jdbc: JDBCInfo = new JDBCInfo("jdbc:mysql://localhost:3306/project-pro?characterEncoding=UTF-8", "root", "")
  val tables = TableBuilder.fromJDBCInfo(jdbc)
  val packageName = "psyco.fuck.you"
}

trait FileWorker {
  def writeFile(file: File, content: String): Unit = {
    if (file.exists())
      throw new RuntimeException("Writing file failed due to existed file : " + file.getAbsolutePath)
    val pw = new PrintWriter(file)
    pw.write(content)
    pw.close
  }

  def readFile(filePath: String): Unit = {
    Source.fromFile(filePath).getLines().foreach(println)
    //    Source.fromFile(filePath).foreach(print)
  }
}

object GenEngine extends Engine with FileWorker {

  def gen(): Unit = {
    tables.foreach(t => {
      val output = engine.layout("Bean.jade", Map(
        "table" -> t,
        "package" -> packageName)
      )
      println("--------------------")
      println(output)
    })
  }

}

object Main extends App {
    GenEngine.gen()
//    GenEngine.readFile("/Users/psyco/antxXXXXX.properties")
//  GenEngine.writeFile(new File("/Users/psyco/tmp/fucker.txt"), "fuck you !")
}
