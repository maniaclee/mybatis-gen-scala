package psyco.mybatis.gen

/**
 * Created by lipeng on 15/8/25.
 */
case class TableInfo(name: String, className: String, columns: List[ColumnInfo]) {
}

case class ColumnInfo(columnName: String, fieldName: String, dbType: String, javaType: String, columnSize: Int)

object JavaTypeConverter {

  val map: Map[String, String] = Map(
    "VARCHAR" -> "String",
    "CHAR" -> "String",
    "LONGVARCHAR" -> "String",
    "BIT" -> "boolean",
    "NUMERIC" -> "java.math.BigDecimal",
    "TINYINT" -> "byte",
    "SMALLINT" -> "short",
    "INTEGER" -> "int",
    "BIGINT" -> "long",
    "REAL" -> "float",
    "FLOAT" -> "float",
    "DOUBLE" -> "double",
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
    "INT" -> "int",
    "DATETIME" -> "Date"
  )

  def getJavaType(jdbcType: String): String = {
    val javaType = "^(\\S+)\\s*".r.findFirstMatchIn(jdbcType).map(_.group(1)).getOrElse(jdbcType)
    val re = JavaTypeConverter.map.get(javaType)
    if (re.isDefined)
      re.get
    else
      throw new RuntimeException("Unknown type for:" + jdbcType)
  }


}