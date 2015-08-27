package psyco.db

/**
 * Created by lipeng on 15/8/25.
 */
case class TableInfo(name: String, className: String, columns: List[ColumnInfo]) {

  def getPrimaryKey(): Option[ColumnInfo] = columns.filter(_.isPrimaryKey).headOption
}
