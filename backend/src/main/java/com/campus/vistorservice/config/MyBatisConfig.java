package com.campus.vistorservice.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    // 1. 手动创建SqlSessionFactory
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 绑定数据源
        sessionFactory.setDataSource(dataSource);

        // 指定Mapper XML文件路径
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:mapper/*.xml")
        );

        // 指定实体类别名包
        sessionFactory.setTypeAliasesPackage("com.campus.vistorservice.model");

        // 【新增这一行！！！】手动指定 TypeHandler 所在的包
        // 这里必须对应 JtsGeometryTypeHandler.java 所在的包名
        sessionFactory.setTypeHandlersPackage("com.campus.vistorservice.handler");

        // 开启驼峰转换
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(config);

        return sessionFactory;
    }

    // 下面的 Bean 保持不变...
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory.getObject());
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setBasePackage("com.campus.vistorservice.dao");
        scanner.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        return scanner;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}