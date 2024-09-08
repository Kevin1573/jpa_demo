package com.xboot.jpa.demo.dal.dao;

import com.xboot.jpa.demo.dal.dataobject.ProjectApiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 注释
 *
 * @author xboot
 **/
@Repository
public interface ProjectApiInfoRepository extends JpaRepository<ProjectApiInfo, Long> {
}
