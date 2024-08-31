package com.xboot.jpa.demo.service.impl;

import com.xboot.jpa.demo.dal.dataobject.City;
import com.xboot.jpa.demo.dal.h2.CityRepository;
import com.xboot.jpa.demo.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注释
 *
 * @author xboot
 **/
@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateCity(City city) {
        return repository.updateNativeV2(city.getName(), city.getState(), city.getId());
    }
}
