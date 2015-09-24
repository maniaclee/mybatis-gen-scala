package psyco.gen.db

import psyco.JDBCInfo
import psyco.util.CaseUtil

import scala.collection.JavaConversions._

/**
 * Created by lipeng on 15/8/25.
 */
object TableBuilder {

  val map: Map[String, String] = Map(
    "VARCHAR" -> "String",
    "CHAR" -> "String",
    "LONGVARCHAR" -> "String",
    "BIT" -> "Boolean",
    "NUMERIC" -> "java.math.BigDecimal",
    "TINYINT" -> "Byte",
    "SMALLINT" -> "Short",
    "INTEGER" -> "Int",
    "BIGINT" -> "Long",
    "REAL" -> "Float",
    "FLOAT" -> "Float",
    "DOUBLE" -> "Double",
    "VARBINARY" -> "byte[]",
    "BINARY" -> "byte[]",
    "DATE" -> "java.sql.Date",
    "TIME" -> "java.sql.Time",
    "TIMESTAMP" -> "java.sql.Timestamp",
    "CLOB" -> "java.sql.Clob",
    "BLOB" -> "java.sql.Blob",
    "ARRAY" -> "java.sql.Array",
    "REF" -> "java.sql.Ref",
    "STRUCT" -> "java.sql.Struct",
    //extra
    "INT" -> "Integer",
    "DATETIME" -> "Date"
  )

  def getJavaType(jdbcType: String): String = {
    /** filter "INT UNSIGNED" => "INT" */
    val javaType = "^(\\S+)\\s*".r.findFirstMatchIn(jdbcType).map(_.group(1)).getOrElse(jdbcType)
    val re = map.get(javaType)
    if (re.isDefined) re.get
    else throw new RuntimeException("Unknown type for:" + jdbcType)
  }

  def fromJDBCInfo(jdbc: JDBCInfo): List[TableInfo] = {
    jdbc.init()
    jdbc.getTables.map(en =>
      new TableInfo(en._1, CaseUtil.underscore2camelUppercase(en._1),
        en._2.map(col => {
          val columnName = col.get(0)
          val fieldName = CaseUtil.underscore2camel(columnName)
          val columnType = col.get(1)
          val columnJavaType = getJavaType(columnType)
          val columnSize = col.get(2).toInt
          val isPrimaryKey = col.get(3).toBoolean
          new ColumnInfo(columnName, fieldName, columnType, columnJavaType, columnSize, isPrimaryKey)
        }).toList))
      .toList
  }

}
