package psyco.mybatis.gen

import java.sql.ResultSet

import psyco.JDBCInfo
import scala.collection.JavaConversions._

/**
 * Created by lipeng on 15/8/25.
 */
object TableBuilder extends App {

  def fromTable(): List[TableInfo] = {
    val jdbc: JDBCInfo = new JDBCInfo("jdbc:mysql://localhost:3306/project-pro?characterEncoding=UTF-8", "root", "")
    jdbc.init()
    jdbc.getTables.map(en =>
      new TableInfo(en._1, en._1,
        en._2.map(col => {
          val columnName = col.get(0)
          val columnType = col.get(1)
          val columnJavaType = JavaTypeConverter.getJavaType(columnType)
          val columnSize = col.get(2).toInt
          new ColumnInfo(columnName, columnName, columnType, columnJavaType, columnSize)
        }).toList))
      .toList
  }

  fromTable().foreach(println _)
}
