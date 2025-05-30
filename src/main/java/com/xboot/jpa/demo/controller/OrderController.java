package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.ApiResult;
import com.xboot.jpa.demo.controller.req.OrderReq;
import com.xboot.jpa.demo.util.OrderTradeNoUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @RequestMapping("/orderQuery/{tradeNo}")
    public ApiResult<String> orderQuery(@PathVariable @NotNull String tradeNo) {
        return ApiResult.ok("order"  + tradeNo);
    }

    /// 下单 (购物车)
    @RequestMapping("/placeYourOrder")
    public ApiResult<String> order(OrderReq orderReq) {

        // 检查商品库存

        // 锁定库存(抢锁)

        // 扣减库存

        // 计算总价(折扣+优惠)

        // 生成订单 (生成订单号)

        //    生成订单号
        String tradeNo = OrderTradeNoUtil.genOrderTradeNo();

        return ApiResult.ok("order");
    }

    /// 支付
    @PostMapping("/orderPay/{tradeNo}")
    public ApiResult<String> payOrder(@PathVariable @NotNull String tradeNo) {

        // 创建支付订单 (微信/支付宝)
        //NativePayService service = new NativePayService.Builder().config(config).build();

        // 返回二维码
        return ApiResult.ok("pay");
    }


    /// 支付回调
    @RequestMapping("/orderPay/callback")
    public ResponseEntity<String> payCallback(HttpServletResponse response) {

        // 根据支付系统返回的参数更新订单状态
        return ResponseEntity.ok("pay");
    }


    /// 订单查询(从支付系统查)
    @GetMapping("/paymentSystem/orderQuery/{tradeNo}")
    public ApiResult<String> orderQueryFromPaymentSystem(@PathVariable @NotNull String tradeNo) {
        // 查询支付系统

        // 更新订单状态
        return ApiResult.ok("order");
    }

    /// 订单状态查询(从业务系统查)
    @GetMapping("/businessSystem/orderQuery/{tradeNo}")
    public ApiResult<String> orderQueryFromBusinessSystem(@PathVariable @NotNull String tradeNo) {
        // 订单查询
        return ApiResult.ok("order");
    }

    // 更新订单状态
    @PostMapping("/updateOrderStatus/{tradeNo}")
    public ApiResult<String> updateOrderStatus(@PathVariable @NotNull String tradeNo) {
        return ApiResult.ok("order");
    }
}
