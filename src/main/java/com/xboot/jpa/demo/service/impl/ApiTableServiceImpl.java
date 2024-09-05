package com.xboot.jpa.demo.service.impl;

import com.xboot.jpa.demo.dal.dataobject.ApiTable;
import com.xboot.jpa.demo.service.ApiTableService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 注释
 *
 * @author xboot
 **/
@Service
public class ApiTableServiceImpl implements ApiTableService {
    @Autowired
    EntityManager em;

    @Modifying
    @Transactional
    @Override
    public void deleteApiTableData(Long tableId) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaDelete<ApiTable> criteriaDelete = builder.createCriteriaDelete(ApiTable.class);
        Root<ApiTable> apiTableRoot = criteriaDelete.from(ApiTable.class);
        criteriaDelete.where(builder.equal(apiTableRoot.get("tableId"), tableId));
        em.createQuery(criteriaDelete).executeUpdate();
    }

    @Modifying
    @Transactional
    @Override
    public void dropTable(String tableName) {
        em.createNativeQuery("drop table " + tableName).executeUpdate();
    }
}
