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
//     * ???????????????0????????????????????????????????????????????????????????????????????????????????????
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
//     * ???????????????1????????????????????????????????????????????????????????????????????????????????????
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
//        // ??????????????????
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//
//        // ?????????????????????
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("orderweek_0", dataSource1());
//        dataSourceMap.put("orderweek_1", dataSource2());
//        //log.error("datasourceCCCC:{}",dataSource1().getConnection().getMetaData().getTableTypes().getDate(0).toString());
//        // ?????????????????????????????????
////        DataSource dataSource = dataSource1();
////        PreparedStatement ps = dataSource.getConnection().prepareStatement("select * from orderinfo_1");
////        ResultSet rs = ps.executeQuery();
////        while (rs.next())
////        {
////            System.out.println("cccccccccc:" + rs.getString("order_id"));
////        }
//        // ShardingRule
//        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("orderinfo", "orderweek_${0..1}.orderinfo_${0..15}");
//        // ???????????? + ????????????
//        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "orderweek_${order_id % 2}"));
//        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "orderinfo_${order_id % 16}"));
//        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
//        // ?????????????????????
//        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
//
//        // ?????? t_order ?????????
////        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("orderinfo", "orderweek_${1..2}.orderinfo_${1..16}");
////        // ??????????????????
////        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "dbShardingAlgorithm"));
////        // ??????????????????
////        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("order_id", "tableShardingAlgorithm"));
////
////        // ??????????????????
////        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
////        shardingRuleConfig.getTables().add(orderTableRuleConfig);
////        // ??????????????????
////        Properties dbShardingAlgorithmrProps = new Properties();
////        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "orderweek_${order_id % 2}");
////        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));
////        // ??????????????????
////        Properties tableShardingAlgorithmrProps = new Properties();
////        tableShardingAlgorithmrProps.setProperty("algorithm-expression", "orderinfo_${order_id % 16}");
////        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));
////        // ?????? ShardingSphereDataSource
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
