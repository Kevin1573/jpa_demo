package com.xboot.jpa.demo.service.impl;

import com.xboot.jpa.demo.dal.entity.Goods;
import com.xboot.jpa.demo.dal.repository.GoodsRepository;
import com.xboot.jpa.demo.dto.GoodsDTO;
import com.xboot.jpa.demo.dto.request.CreateGoodsRequest;
import com.xboot.jpa.demo.dto.request.UpdateGoodsRequest;
import com.xboot.jpa.demo.service.GoodsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository goodsRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public GoodsDTO createGoods(CreateGoodsRequest request) {
        Goods goods = modelMapper.map(request, Goods.class);
        Goods savedGoods = goodsRepository.save(goods);
        return modelMapper.map(savedGoods, GoodsDTO.class);
    }

    @Override
    public Page<GoodsDTO> listGoods(Pageable pageable) {
        return goodsRepository.findAll(pageable)
                .map(goods -> modelMapper.map(goods, GoodsDTO.class));
    }

    @Override
    public GoodsDTO detailGoods(Long id) {
        return goodsRepository.findById(id)
                .map(goods -> modelMapper.map(goods, GoodsDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("商品不存在"));
    }

    @Override
    @Transactional
    public GoodsDTO updateGoods(Long id, UpdateGoodsRequest request) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("商品不存在"));

        Optional.ofNullable(request.getName()).ifPresent(goods::setName);
        Optional.ofNullable(request.getPrice()).ifPresent(goods::setPrice);
        Optional.ofNullable(request.getDescription()).ifPresent(goods::setDescription);
        Optional.ofNullable(request.getStock()).ifPresent(goods::setStock);
        Optional.ofNullable(request.getStatus()).ifPresent(goods::setStatus);

        Goods updatedGoods = goodsRepository.save(goods);
        return modelMapper.map(updatedGoods, GoodsDTO.class);
    }

    @Override
    @Transactional
    public void deleteGoods(Long id) {
        if (!goodsRepository.existsById(id)) {
            throw new EntityNotFoundException("商品不存在");
        }
        goodsRepository.deleteById(id);
    }
}
