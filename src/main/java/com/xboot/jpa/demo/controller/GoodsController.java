package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.common.resp.ApiResult;
import com.xboot.jpa.demo.controller.req.GoodsRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class GoodsController {

    // 新建商品
    @PostMapping("/goods/create")
    public ApiResult<String> createGoods(@RequestBody GoodsRequest.GoodsCreateReq goodsCreateReq) {
        // 获取当前用户, 更新请求参数(createUser)
        // Long userId = AppContextHolder.getCurrentUserId();
        goodsCreateReq.setCreateUser(1L);

        /// TODO 后期添加检查有没有创建商品的权限 以及根据店铺的经营范围, 将商品归类(便于后期的商品推荐)
        return ApiResult.ok("success");
    }

    @GetMapping("/goods/list")
    public ApiResult<String> listGoods(@RequestBody GoodsRequest.GoodsSearchReq goodsSearchReq) {
        // 根据当前用户查询商品(区分超级管理员/管理员/商户)
        // Long userId = AppContextHolder.getCurrentUserId();
        // Boolean superAdmin = AppContextHolder.isSuperAdmin();
        return ApiResult.ok("success");
    }

    @GetMapping("/goods/detail")
    public ApiResult<String> detailGoods(@RequestParam Long id) {
        // 根据当前用户查询商品(区分超级管理员/管理员/商户)
        // Long userId = AppContextHolder.getCurrentUserId();
        // Boolean superAdmin = AppContextHolder.isSuperAdmin();
        return ApiResult.ok("success");
    }

    @PostMapping("/goods/update")
    public ApiResult<String> updateGoods(@RequestBody GoodsRequest.GoodsUpdateReq goodsUpdateReq) {
        // 获取当前用户, 获取商品创建人, 获取商品更新人
        // Long userId = AppContextHolder.getCurrentUserId();
        goodsUpdateReq.setCreateUser(1L);
        goodsUpdateReq.setUpdateUser(1L);
        return ApiResult.ok("success");
    }
}
