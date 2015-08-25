package psyco;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lipeng on 15/8/25.
 */
@Deprecated
public class JavaTpyeConvert {

    public static Map<String, Class<?>> MAP_JAVA_CLASS = new HashMap<>();
    public static Map<String, String> MAP = null;

    static {
        MAP_JAVA_CLASS.put("VARCHAR", java.lang.String.class);
        MAP_JAVA_CLASS.put("CHAR", java.lang.String.class);
        MAP_JAVA_CLASS.put("LONGVARCHAR", java.lang.String.class);
        MAP_JAVA_CLASS.put("BIT", boolean.class);
        MAP_JAVA_CLASS.put("NUMERIC", java.math.BigDecimal.class);
        MAP_JAVA_CLASS.put("TINYINT", byte.class);
        MAP_JAVA_CLASS.put("SMALLINT", short.class);
        MAP_JAVA_CLASS.put("INTEGER", int.class);
        MAP_JAVA_CLASS.put("BIGINT", long.class);
        MAP_JAVA_CLASS.put("REAL", float.class);
        MAP_JAVA_CLASS.put("FLOAT", float.class);
        MAP_JAVA_CLASS.put("DOUBLE", double.class);
        MAP_JAVA_CLASS.put("VARBINARY", byte[].class);
        MAP_JAVA_CLASS.put("BINARY", byte[].class);
        MAP_JAVA_CLASS.put("DATE", java.sql.Date.class);
        MAP_JAVA_CLASS.put("TIME", java.sql.Time.class);
        MAP_JAVA_CLASS.put("TIMESTAMP", java.sql.Timestamp.class);
        MAP_JAVA_CLASS.put("CLOB", java.sql.Clob.class);
        MAP_JAVA_CLASS.put("BLOB", java.sql.Blob.class);
        MAP_JAVA_CLASS.put("ARRAY", java.sql.Array.class);
        MAP_JAVA_CLASS.put("REF", java.sql.Ref.class);
        MAP_JAVA_CLASS.put("STRUCT", java.sql.Struct.class);
        /*convert to a plain text version*/
        MAP = MAP_JAVA_CLASS.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().getSimpleName()));
    }

    public static String getJavaTypeName(String jdbcType) {
        String re = MAP.get(jdbcType);
        if (re == null)
            throw new RuntimeException("Unkonw type for jdbc:" + jdbcType);
        return re;
    }

    public static Class<?> getJavaType(String jdbcType) {
        Class<?> re = MAP_JAVA_CLASS.get(jdbcType);
        if (re == null)
            throw new RuntimeException("Unkonw type for jdbc:" + jdbcType);
        return re;
    }
}
