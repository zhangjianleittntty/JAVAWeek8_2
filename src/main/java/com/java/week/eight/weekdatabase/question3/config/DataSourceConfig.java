//package com.java.week.eight.weekdatabase.question3.config;//package com.java01.week8.databaseweek.question2.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
//import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
////import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
////import org.apache.shardingsphere.infra.config.RuleConfiguration;
////import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
////import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
////import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
////import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
////import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
//import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
///**
// * @Program: databaseweek
// * @ClassName: DataSourceConfig
// * @Author: zhangjl
// * @Date: 2021-03-13 17:31
// * @Description:
// */
//@Configuration
//@Slf4j
//@MapperScan(basePackages = "com.java.week.eight.weekdatabase.question3.mapper",sqlSessionFactoryRef = "sessionFactory")
//public class DataSourceConfig {
//
//    /**
//     * 配置数据源0，数据源的名称最好要有一定的规则，方便配置分库的计算规则
//     *
//     * @return
//     */
//    @Bean(name = "dataSource1")
//    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.datasource1")
//    public DataSource dataSource1() {
//        return (DataSource) DruidDataSourceBuilder.create().build();
//    }
//
//    /**
//     * 配置数据源1，数据源的名称最好要有一定的规则，方便配置分库的计算规则
//     *
//     * @return
//     */
//    @Bean(name = "dataSource2")
//    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.datasource2")
//    public DataSource dataSource2() {
//        return (DataSource) DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setTypeAliasesPackage("com.java.week.eight.weekdatabase.question3.model");
//
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true);
//        sessionFactory.setConfiguration(configuration);
//
//        return sessionFactory.getObject();
//    }
//
//    @Bean
//    public DataSource dataSource() throws SQLException {
//
//        // 配置分片规则
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//
//        // 配置真实数据源
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("orderweek_0", dataSource1());
//        dataSourceMap.put("orderweek_1", dataSource2());
//        //log.error("datasourceCCCC:{}",dataSource1().getConnection().getMetaData().getTableTypes().getDate(0).toString());
//        // 测试数据源是否连接成功
////        DataSource dataSource = dataSource1();
////        PreparedStatement ps = dataSource.getConnection().prepareStatement("select * from orderinfo_1");
////        ResultSet rs = ps.executeQuery();
////        while (rs.next())
////        {
////            System.out.println("cccccccccc:" + rs.getString("order_id"));
////        }
//        // ShardingRule
//        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("orderinfo", "orderweek_${0..1}.orderinfo_${0..15}");
//        // 配置分库 + 分表策略
//        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "orderweek_${order_id % 2}"));
//        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "orderinfo_${order_id % 16}"));
//        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
//        // 获取数据源对象
//        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
//
//        // 配置 t_order 表规则
////        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("orderinfo", "orderweek_${1..2}.orderinfo_${1..16}");
////        // 配置分库策略
////        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "dbShardingAlgorithm"));
////        // 配置分表策略
////        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "tableShardingAlgorithm"));
////
////        // 配置分片规则
////        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
////        shardingRuleConfig.getTables().add(orderTableRuleConfig);
////        // 配置分库算法
////        Properties dbShardingAlgorithmrProps = new Properties();
////        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "orderweek_${order_id % 2}");
////        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));
////        // 配置分表算法
////        Properties tableShardingAlgorithmrProps = new Properties();
////        tableShardingAlgorithmrProps.setProperty("algorithm-expression", "orderinfo_${order_id % 16}");
////        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));
////        // 创建 ShardingSphereDataSource
////        DataSource dataSource = ShardingSphereDataSourceFactory. createDataSource(dataSourceMap, Collections.singleton(shardingRuleConfig), new Properties());
//
//        return dataSource;
//    }
//
////    @Bean
////    public DataSourceTransactionManager shardTransactionManager(DataSource dataSource) {
////        return new DataSourceTransactionManager(dataSource);
////    }
//
//    @Bean
//    public PlatformTransactionManager txManager(final DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
