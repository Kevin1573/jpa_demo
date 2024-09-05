package com.xboot.jpa.demo.service;

/**
 * 注释
 *
 * @author xboot
 **/
public interface ApiTableService {

    void deleteApiTableData(Long tableId);

    void dropTable(String tableName);
}
