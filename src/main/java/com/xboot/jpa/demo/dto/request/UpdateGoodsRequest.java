package com.xboot.jpa.demo.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 更新商品请求DTO
 */
@Data
public class UpdateGoodsRequest {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品库存
     */
    @Min(value = 0, message = "商品库存不能小于0")
    private Integer stock;

    /**
     * 商品状态（0：下架，1：上架）
     */
    private Integer status;
}