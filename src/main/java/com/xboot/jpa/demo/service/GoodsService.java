package com.xboot.jpa.demo.service;

import com.xboot.jpa.demo.dto.GoodsDTO;
import com.xboot.jpa.demo.dto.request.CreateGoodsRequest;
import com.xboot.jpa.demo.dto.request.UpdateGoodsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsService {
    /**
     * 创建商品
     */
    GoodsDTO createGoods(CreateGoodsRequest request, Object loginId);

    /**
     * 获取商品列表
     */
    Page<GoodsDTO> listGoods(Pageable pageable);

    /**
     * 获取商品详情
     */
    GoodsDTO detailGoods(Long id);

    /**
     * 更新商品
     */
    GoodsDTO updateGoods(Long id, UpdateGoodsRequest request);

    /**
     * 删除商品
     */
    void deleteGoods(Long id);
}
