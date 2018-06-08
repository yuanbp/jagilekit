package com.chieftain.agile.common.datasource;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.pagehelper.PageInterceptor;

/**
 * com.chieftain.junite.common.pagehelper [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
@Configuration
@AutoConfigureAfter(DatasourceConfig.class)
@ConfigurationProperties
public class MybatisConfiguration {

    private static Logger logger = LogManager.getLogger(MybatisConfiguration.class);

    /**
     * 配置类型别名
     */
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    /**
     * 配置 mapper 的扫描，找到所有的 mapper.xml 映射文件
     */
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    /**
     * 加载全局的配置文件
     */
    @Value("${mybatis.config-location}")
    private String configLocation;

    /**
     * 分页插件数据库方言
     */
    @Value("${pagehelper.helperDialect}")
    private String helperDialect;

    /**
     * 分页插件 reasonable
     */
    @Value("${pagehelper.reasonable}")
    private String reasonable;

    /**
     * 分页插件 supportMethodsArguments
     */
    @Value("${pagehelper.supportMethodsArguments}")
    private String supportMethodsArguments;

    /**
     * 分页插件 params
     */
    @Value("${pagehelper.params}")
    private String params;

    /**
     * 分页插件 returnPageInfo
     */
    @Value("${pagehelper.returnPageInfo}")
    private String returnPageInfo;

    /**
     * 分页插件 offsetAsPageNum
     */
    @Value("${pagehelper.offsetAsPageNum}")
    private String offsetAsPageNum;

    /**
     * 分页插件 rowBoundsWithCount
     */
    @Value("${pagehelper.rowBoundsWithCount}")
    private String rowBoundsWithCount;

    @Autowired
    private DataSource dataSource;

    /**
     * 提供 SqlSessionFactory
     * @return SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dataSource);

            // 读取配置 （设置别名，ep:parameterType="com.bean.User" 可以写成 parameterType = "User"，但是启动时DefaultVFS会输出乱码 Reader entry: ����，原因是扫描了未编译的pojo，不影响使用，为了美观和简洁，不设置别名，使用全路径）
//            sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

            // 设置 mapper.xml 文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
            sessionFactoryBean.setMapperLocations(resources);
            // 设置 mybatis-config.xml 配置文件位置
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

            // 添加分页插件、打印 sql 插件
            Interceptor[] plugins = new Interceptor[]{pageHelper(),sqlPrintInterceptor()};
            sessionFactoryBean.setPlugins(plugins);

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            logger.error("mybatis resolver mapper*xml is error",e);
            return null;
        } catch (Exception e) {
            logger.error("mybatis sqlSessionFactoryBean create error",e);
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 将要执行的 sql 进行日志打印 (不想拦截，就把这方法注释掉)
     * @return SqlPrintInterceptor
     */
    @Bean
    public SqlPrintInterceptor sqlPrintInterceptor(){
        return new SqlPrintInterceptor();
    }

    /**
     * 分页插件
     */

//        <!-- 分页插件 -->
//      <plugins>
//                    <plugin interceptor="com.github.pagehelper.PageHelper">
//                            <property name="dialect" value="mysql"/>
//                            <!-- 该参数默认为 false -->
//                              <!-- 设置为 true 时，会将 RowBounds 第一个参数 offset 当成 pageNum 页码使用 -->
//                              <!-- 和 startPage 中的 pageNum 效果一样 -->
//                            <property name="offsetAsPageNum" value="true"/>
//                             <!-- 该参数默认为 false -->
//                              <!-- 设置为 true 时，使用 RowBounds 分页会进行 count 查询 -->
//                            <property name="rowBoundsWithCount" value="true"/>
//                            <!-- 设置为 true 时，如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果 -->
//                              <!-- （相当于没有执行分页查询，但是返回结果仍然是 Page 类型） -->
//                            <property name="pageSizeZero" value="true"/>
//                            <!-- 3.3.0 版本可用 - 分页参数合理化，默认 false 禁用 -->
//                              <!-- 启用合理化时，如果 pageNum<1 会查询第一页，如果 pageNum>pages 会查询最后一页 -->
//                              <!-- 禁用合理化时，如果 pageNum<1 或 pageNum>pages 会返回空数据 -->
//                            <property name="reasonable" value="false"/>
//                            <!-- 支持通过 Mapper 接口参数来传递分页参数 -->
//                            <property name="supportMethodsArguments" value="false"/>
//                            <!-- always 总是返回 PageInfo 类型, check 检查返回类型是否为 PageInfo,none 返回 Page -->
//                            <property name="returnPageInfo" value="none"/>
//
//                   </plugin>
//          </plugins>

    /**
     * 分页插件
     * @return PageInterceptor
     */
    @Bean
    public PageInterceptor pageHelper() {
        PageInterceptor pageHelper = new PageInterceptor();
        Properties p = new Properties();
        p.setProperty("helperDialect", helperDialect);
        p.setProperty("reasonable", "true");
        p.setProperty("supportMethodsArguments",supportMethodsArguments);
        p.setProperty("params", params);
        p.setProperty("returnPageInfo", returnPageInfo);
        p.setProperty("offsetAsPageNum", offsetAsPageNum);
        p.setProperty("rowBoundsWithCount", rowBoundsWithCount);
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
