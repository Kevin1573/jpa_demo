package com.xboot.jpa.demo.controller;

import com.xboot.jpa.demo.dto.GoodsDTO;
import com.xboot.jpa.demo.dto.request.CreateGoodsRequest;
import com.xboot.jpa.demo.dto.request.UpdateGoodsRequest;
import com.xboot.jpa.demo.service.GoodsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
@Tag(name = "商品管理", description = "商品相关操作接口")
public class GoodsController {

    private final GoodsService goodsService;

    @PostMapping
    @Operation(summary = "创建商品", description = "创建一个新的商品")
    public ResponseEntity<GoodsDTO> createGoods(@Valid @RequestBody CreateGoodsRequest request) {
        GoodsDTO goods = goodsService.createGoods(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(goods.getId())
                .toUri();
        return ResponseEntity.created(location).body(goods);
    }

    @GetMapping
    @Operation(summary = "获取商品列表", description = "获取分页的商品列表，支持排序")
    public ResponseEntity<Page<GoodsDTO>> listGoods(
            @PageableDefault(size = 10, sort = "createTime,desc") Pageable pageable) {
        Page<GoodsDTO> goodsPage = goodsService.listGoods(pageable);
        return ResponseEntity.ok(goodsPage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取商品详情", description = "根据ID获取单个商品的详细信息")
    public ResponseEntity<GoodsDTO> detailGoods(@PathVariable Long id) {
        GoodsDTO goods = goodsService.detailGoods(id);
        return ResponseEntity.ok(goods);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新商品", description = "根据ID更新商品信息")
    public ResponseEntity<GoodsDTO> updateGoods(
            @PathVariable Long id, @Valid @RequestBody UpdateGoodsRequest request) {
        GoodsDTO goods = goodsService.updateGoods(id, request);
        return ResponseEntity.ok(goods);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品", description = "根据ID删除商品")
    public ResponseEntity<Void> deleteGoods(@PathVariable Long id) {
        goodsService.deleteGoods(id);
        return ResponseEntity.noContent().build();
    }
}
