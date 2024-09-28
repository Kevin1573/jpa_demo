package com.xboot.jpa.demo.dal.h2;

import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 注释
 *
 * @author xboot
 **/
@Repository
public interface CsvItemRepository extends JpaRepository<CsvItem, String> {

}
