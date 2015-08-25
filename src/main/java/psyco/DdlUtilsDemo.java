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
                        System.out.printf("%s  ,   %s\n" , c.getName(),c.getType());
                    });
                }
        );
    }

}
