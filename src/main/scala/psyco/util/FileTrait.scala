package psyco.util

import java.io.{PrintWriter, File}

import scala.io.Source

/**
 * Created by lipeng on 15/8/28.
 */
trait FileTrait {
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
