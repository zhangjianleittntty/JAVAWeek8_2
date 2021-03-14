package com.java.week.eight.weekdatabase;

import com.java.week.eight.weekdatabase.question3.mapper.OrderInfoMapper;
import com.java.week.eight.weekdatabase.question3.model.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;

//@SpringBootApplication
@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
public class WeekdatabaseApplication implements CommandLineRunner {

	@Autowired
	private DataSource dataSource;

	@Resource
	private OrderInfoMapper orderInfoMapper;

	public static void main(String[] args) {
		SpringApplication.run(WeekdatabaseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("DatabaseweekApplicationcc:{}",dataSource.toString());
		OrderInfo orderInfo = OrderInfo.builder().orderId(33445569l).skuId(33445568l).orderName("ccccca")
				.salePrice(new BigDecimal("342.3")).build();
		OrderInfo orderInfoA= OrderInfo.builder().orderId(334455681l).skuId(334455681l).orderName("cccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccacccccaccccca")
				.salePrice(new BigDecimal("454")).build();
		int count = orderInfoMapper.save(orderInfo);
		int countA = orderInfoMapper.save(orderInfoA);
		log.info("Save {}, dataBase:{}, tableName:{} OrderInfo: {}", count,(orderInfo.getOrderId() % 2),(orderInfo.getOrderId() % 16) ,orderInfo);
//		log.info("Save11111111 {}, dataBase:{}, tableName:{} OrderInfo: {}", 1,(33445569l % 2),(33445569l % 16) ,null);
//		log.info("Save22222222 {}, dataBase:{}, tableName:{} OrderInfo: {}", 1,(334455681l % 2),(334455681l % 16) ,null);
	}

}
