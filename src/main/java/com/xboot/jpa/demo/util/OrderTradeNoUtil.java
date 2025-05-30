package com.xboot.jpa.demo.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderTradeNoUtil {
    private static final String ORDER_TRADE_NO_PREFIX = "ST0";

    public String genOrderTradeNo() {
        return ORDER_TRADE_NO_PREFIX + System.currentTimeMillis() + (int) (Math.random() * 9000 + 1000);
    }
}

class OrderTradeNoUtilTest {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String tradeNo = OrderTradeNoUtil.genOrderTradeNo();
            System.out.println(tradeNo + "  " + tradeNo.length());
        }
    }
}