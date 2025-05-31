package com.xboot.jpa.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品信息DTO
 */
@Data
public class GoodsDTO {
    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品状态（0：下架，1：上架）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;
}
