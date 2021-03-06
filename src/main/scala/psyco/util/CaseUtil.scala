package psyco.util

/**
 * Created by lipeng on 15/8/25.
 */
object CaseUtil extends App {
  def camel2underscore(s: String) = "[A-Z\\d]".r.replaceAllIn(s, "_" + _.group(0).toLowerCase()
  )

  def underscore2camel(s: String) = "_([a-zA-Z]?)".r.replaceAllIn(s, _.group(1).toUpperCase()
  )

  def underscore2camelUppercase(s: String): String = camelUpper(underscore2camel(s))

  def camelUpper(name: String): String = if (name.length == 1) name.toUpperCase() else name.substring(0, 1).toUpperCase + name.substring(1)

  def camelLower(name: String): String = if (name.length == 1) name.toLowerCase() else name.substring(0, 1).toLowerCase() + name.substring(1)


  println(camel2underscore("aFuckShit"))
  println(underscore2camel("a_fuck_shit"))
  println(underscore2camelUppercase(camel2underscore("aFuckShit")))
}
