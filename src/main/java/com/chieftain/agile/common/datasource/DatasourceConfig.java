package com.chieftain.agile.common.datasource;


import java.io.Serializable;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * com.chieftain.junite.common.druid [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
@Configuration
public class DatasourceConfig implements Serializable {

    private static final long serialVersionUID = 1056707453868150142L;

    private Logger logger = LogManager.getLogger(DatasourceConfig.class);

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.type}")
    private String dbType;

    @Value("${spring.datasource.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.filters}")
    private String filters;

    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * 配置数据源
     * @return DataSource
     */
    @Bean(destroyMethod = "close", initMethod = "init")     // 声明其为 Bean 实例
    @Primary  // 在同样的 DataSource 中，首先使用被标注的 DataSource
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
//        datasource.setDbType(dbType);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

//        List<Filter> filters = new ArrayList<>();
//        filters.add(druidLog4j2Filter());
//        datasource.setProxyFilters(filters);

        return datasource;
    }

    /**
     * 需要log4j2 jar依赖
     * @return Log4j2Filter
     */
    @Bean
    public Log4j2Filter druidLog4j2Filter(){
        Log4j2Filter logFilter = new Log4j2Filter();
        logFilter.setConnectionLogEnabled(false);
        logFilter.setStatementLogEnabled(false);
        logFilter.setResultSetLogEnabled(true);
        logFilter.setStatementExecutableSqlLogEnable(true);
        return logFilter;
    }

    /**
     * 需要slf4j jar依赖
     * @return Slf4jLogFilter
     */
    @Bean
    public Slf4jLogFilter druidLogFilter(){
        Slf4jLogFilter logFilter = new Slf4jLogFilter();
        logFilter.setConnectionLogEnabled(false);
        logFilter.setStatementLogEnabled(false);
        logFilter.setResultSetLogEnabled(true);
        logFilter.setStatementExecutableSqlLogEnable(true);
        return logFilter;
    }

//    // 需要log4j jar依赖
//    @Bean
    public Log4jFilter druidLog4jFilter(){
        Log4jFilter logFilter = new Log4jFilter();
        logFilter.setConnectionLogEnabled(false);
        logFilter.setStatementLogEnabled(false);
        logFilter.setResultSetLogEnabled(true);
        logFilter.setStatementExecutableSqlLogEnable(true);
        return logFilter;
    }
}
