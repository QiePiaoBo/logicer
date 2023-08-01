package com.dylan.comm.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.dylan.comm.config.ConfigReader;
import com.dylan.logicer.base.logger.MyLogger;
import com.dylan.logicer.base.logger.MyLoggerFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Dylan
 * @Date : 2022/3/27 - 16:41
 * @Description : 数据源
 * @Function :
 */
public class MybatisCenter {

    private static final MyLogger logger = MyLoggerFactory.getLogger(MybatisCenter.class);

    private SqlSession sqlSession;

    public MybatisCenter() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        MybatisConfiguration configuration = new MybatisConfiguration();
        myMybatisConfig(configuration);
        // mybatis 配置拦截器
        configuration.addInterceptor(initInterceptor());
        // mybatis配置mapper接口的包位置
        configuration.addMappers(ConfigReader.getComplexConfigString("mybatis.mapperScanPackage"));
        // mybatis配置日志实现
        configuration.setLogImpl(Slf4jImpl.class);
        // 给configuration注入GlobalConfig里的配置
        GlobalConfigUtils.setGlobalConfig(configuration, initGlobalConfig());
        // 设置数据源
        Environment environment = new Environment("1", new JdbcTransactionFactory(), initDataSource());
        configuration.setEnvironment(environment);
        // 设置Mapper.xml文件位置
        try {
            registryMapperXml(configuration, ConfigReader.getComplexConfigStringWithDefault("mybatis.mapperFileLocation", "mapper"));
        } catch (IOException e) {
            logger.error("Error parsing xml for mapper. {}", e.getMessage());
        }
        // 构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = builder.build(configuration);
        setSqlSession(sqlSessionFactory.openSession());
    }

    /**
     * 解析Mapper.xml文件
     *
     * @param configuration
     * @param classPath
     */
    private void registryMapperXml(MybatisConfiguration configuration, String classPath) throws IOException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> mapper = contextClassLoader.getResources(classPath);

        while (mapper.hasMoreElements()) {
            URL url = mapper.nextElement();
            if (url.getProtocol().equals("file")) {
                String path = url.getPath();
                File file = new File(path);
                File[] files = file.listFiles();
                if (Objects.nonNull(files) && files.length > 0){
                    for (File f : files) {
                        FileInputStream in = new FileInputStream(f);
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, f.getPath(), configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                        in.close();
                    }
                }
            } else {
                JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
                JarFile jarFile = urlConnection.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    if (jarEntry.getName().endsWith("Mapper.xml")) {
                        InputStream in = jarFile.getInputStream(jarEntry);
                        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, jarEntry.getName(), configuration.getSqlFragments());
                        xmlMapperBuilder.parse();
                        in.close();
                    }
                }
            }
        }
    }

    /**
     * 构造mybatis-plus需要的global config
     *
     * @return
     */
    public GlobalConfig initGlobalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        // 此参数会自动生成baseMapper的基础方法映射
        globalConfig.setSqlInjector(new DefaultSqlInjector());
        // 设置id生成器
        globalConfig.setIdentifierGenerator(new DefaultIdentifierGenerator());
        // 设置超类mapper
        globalConfig.setSuperMapperClass(BaseMapper.class);
        return globalConfig;
    }


    /**
     * MybatisConfiguration的拦截器
     *
     * @return
     */
    private Interceptor initInterceptor() {
        // 创建Mybatis-Plus插件对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 创建分页插件并添加到插件对象中
        PaginationInnerInterceptor page = new PaginationInnerInterceptor();
        page.setDbType(DbType.MYSQL);
        page.setOverflow(true);
        page.setMaxLimit(500L);
        interceptor.addInnerInterceptor(page);
        return interceptor;
    }

    /**
     * MybatisConfiguration的自定义配置
     *
     * @param configuration
     */
    private void myMybatisConfig(MybatisConfiguration configuration) {
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
    }


    /**
     * 构造DataSource
     *
     * @return
     */
    private DataSource initDataSource() {
        MysqlCenter mysqlCenter = initMysqlConfig();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(mysqlCenter.getUrl());
        dataSource.setDriverClassName(mysqlCenter.getDriverClassName());
        dataSource.setUsername(mysqlCenter.getUserName());
        dataSource.setPassword(mysqlCenter.getPassword());
        // 空闲时间超时时间  600000 10分钟
        dataSource.setIdleTimeout(600000);
        // 最小空闲连接数量
        dataSource.setMinimumIdle(10);
        // 连接超时时间
        dataSource.setConnectionTimeout(60000);
        dataSource.setConnectionTestQuery("SELECT 1");
        return dataSource;
    }

    /**
     * 获取配置中的mysql配置并将其赋值给MysqlConfig
     *
     * @return
     */
    public MysqlCenter initMysqlConfig() {
        Map mysqlConfig = ConfigReader.getComplexConfigMap("db.mysql");
        String url = (String) mysqlConfig.getOrDefault("url", "jdbc:mysql://192.168.2.111:3306/mylog?useUnicode=true&characterEncoding=utf-8&relaxAutoCommit=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true");
        String username = (String) mysqlConfig.getOrDefault("username", "root");
        String password = mysqlConfig.getOrDefault("password", "19970413") + "";
        String driverClass = (String) mysqlConfig.getOrDefault("driverClassName", "com.mysql.cj.jdbc.Driver");
        return new MysqlCenter(url, driverClass, username, password);
    }


    /**
     * 对外暴露 sqlSession
     *
     * @return
     */
    public SqlSession getSqlSession() {
        return sqlSession;
    }

    /**
     * 设置sqlSession的注入方法为私有
     *
     * @param sqlSession
     */
    private void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
}
