package psyco;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Database;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by lipeng on 15/8/22.
 */
public class DdlUtilsDemo {
    public static Database database;

    static {
        try {
            database = readDatabase(druidDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/project-pro?characterEncoding=UTF-8");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("");
        druidDataSource.setMaxActive(60);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(60000);//60s
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(3000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        druidDataSource.setFilters("config");
        Properties properties = new Properties();
        properties.put("config.decrypt", "true");
        druidDataSource.setConnectProperties(properties);
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(10000);//10s。。慢
        statFilter.setMergeSql(true);
        statFilter.setLogSlowSql(true);
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter));
        return druidDataSource;
    }

    public static Database readDatabase(DataSource dataSource) {
        Platform platform = PlatformFactory.createNewPlatformInstance(dataSource);
        return platform.readModelFromDatabase("project-pro");
    }


    @Test
    public void test() throws SQLException {
        Database db = readDatabase(druidDataSource());
        Lists.newArrayList(db.getTables()).forEach(t -> {
                    System.out.println(t);
//                    System.out.println(Lists.newArrayList((t.getColumns())));
                    Lists.newArrayList(t.getColumns()).forEach(c -> {
                        System.out.printf("%s  , %s , %s ,  %s\n", c.getName(), c.getType(), c.getTypeCode(), c.getJavaName());
                    });
                }
        );
    }

    @Test
    public void testJDbc() throws Exception {
        JDBCInfo re = new JDBCInfo("jdbc:mysql://localhost:3306/project-pro?characterEncoding=UTF-8", "root", "");
        re.init();
        re.getTablesNames().forEach(e -> {
            try {
                re.getTable(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        System.out.println(re.getTablesNames());
        re.close();
        String s = "VARCHAR\tjava.lang.String\tsetString\tupdateString\n" +
                "CHAR\tjava.lang.String\tsetString\tupdateString\n" +
                "LONGVARCHAR\tjava.lang.String\tsetString\tupdateString\n" +
                "BIT\tboolean\tsetBoolean\tupdateBoolean\n" +
                "NUMERIC\tjava.math.BigDecimal\tsetBigDecimal\tupdateBigDecimal\n" +
                "TINYINT\tbyte\tsetByte\tupdateByte\n" +
                "SMALLINT\tshort\tsetShort\tupdateShort\n" +
                "INTEGER\tint\tsetInt\tupdateInt\n" +
                "BIGINT\tlong\tsetLong\tupdateLong\n" +
                "REAL\tfloat\tsetFloat\tupdateFloat\n" +
                "FLOAT\tfloat\tsetFloat\tupdateFloat\n" +
                "DOUBLE\tdouble\tsetDouble\tupdateDouble\n" +
                "VARBINARY\tbyte[]\tsetBytes\tupdateBytes\n" +
                "BINARY\tbyte[]\tsetBytes\tupdateBytes\n" +
                "DATE\tjava.sql.Date\tsetDate\tupdateDate\n" +
                "TIME\tjava.sql.Time\tsetTime\tupdateTime\n" +
                "TIMESTAMP\tjava.sql.Timestamp\tsetTimestamp\tupdateTimestamp\n" +
                "CLOB\tjava.sql.Clob\tsetClob\tupdateClob\n" +
                "BLOB\tjava.sql.Blob\tsetBlob\tupdateBlob\n" +
                "ARRAY\tjava.sql.Array\tsetARRAY\tupdateARRAY\n" +
                "REF\tjava.sql.Ref\tSetRef\tupdateRef\n" +
                "STRUCT\tjava.sql.Struct\tSetStruct\tupdateStruct";
        String[] ss = s.split("\\s+");
//        for (int i = 0; i < ss.length / 4; i++) {
//            System.out.printf("MAP_JAVA_CLASS.put(\"%s\",%s.class);\n", ss[i * 4], ss[i * 4 + 1]);
//        }
        for (int i = 0; i < ss.length / 4; i++) {
            System.out.printf("\"%s\"->\"%s\",\n", ss[i * 4], ss[i * 4 + 1]);
//            System.out.printf("\"%s\"->%s.class.getSimpleName()\n", ss[i * 4], ss[i * 4 + 1]);
        }
    }

}
