package com.xboot.jpa.demo.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@Data
public class OrderReq implements Serializable {
    List<OrderGoods> orderGoodsList;
    // 总金额
    private BigDecimal orderTotalAmount;

    @Data
    private static class OrderGoods implements Serializable {
        private String goodsId;
        private String goodsName;
        private BigDecimal goodsPrice;
        private Integer goodsNum;
    }
}
