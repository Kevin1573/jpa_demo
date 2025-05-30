package com.xboot.jpa.demo.controller.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsRequest {

    @Data
    public static class GoodsCreateReq implements Serializable {
        private String name;
        private String desc;
        private int state; // 0:初始化 1:下架 2:上架
        private BigDecimal price;
        private String remark;
        private String imgUrl;
        private String createTime;
        private Long createUser; // 创建人(商户或者管理员)
    }

    @Data
    public static class GoodsSearchReq implements Serializable {
        private String name;
        private String desc;
        private int state;
        // 分类
        private String category;
        // 品牌
        private String brand;
        // 供应商
        private String supplier;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class GoodsUpdateReq extends GoodsCreateReq {
        private Long id;
        private String updateTime;
        private Long updateUser;
    }
}
