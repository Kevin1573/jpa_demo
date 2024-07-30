package com.xboot.jpa.demo.dal.h2;

import com.xboot.jpa.demo.dal.dataobject.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
public interface CityRepository extends Repository<City, Long>{

    City save(City city);

    @Transactional
    @Modifying
    @Query(value = "update city set name = :#{#city.name}, state = :#{#city.state} where id = :#{#city.id}", nativeQuery = true)
    int updateNative(City city);

    @Transactional
    @Modifying
    @Query("update City c set name = :name, state = :state where id = :id")
    int updateNativeV2(@Param("name") String name, @Param("state") String state, @Param("id") Long id);

    Page<City> findAll(Pageable pageable);

    List<City> findByNameAndStateAllIgnoreCase(String name, String state);
}
