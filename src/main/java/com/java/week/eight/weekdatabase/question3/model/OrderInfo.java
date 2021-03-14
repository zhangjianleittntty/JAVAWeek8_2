package com.java.week.eight.weekdatabase.question3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Program: databaseweek
 * @ClassName: OrderInfo
 * @Author: zhangjl
 * @Date: 2021-03-11 12:02
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfo {
    private Long id;
    private Long orderId;
    private Long skuId;
    private String orderPin;
    private String orderName;
    private BigDecimal salePrice;
    private Date created;
    private Date modified;

}
