package com.xboot.jpa.demo.dal.h2;

import com.xboot.jpa.demo.dal.dataobject.BatchErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 注释
 *
 * @author xboot
 **/
@Repository
public interface BatchLogErrorRepository extends JpaRepository<BatchErrorLog, Long> {
    @Modifying
    @Query("update BatchErrorLog set isHandled = true where id in (?1)")
    void updateHandleStatus(List<Long> refIds);
}
