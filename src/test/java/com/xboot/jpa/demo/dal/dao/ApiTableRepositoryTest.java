package com.xboot.jpa.demo.dal.dao;

import com.xboot.jpa.demo.JpaDemoApplication;
import com.xboot.jpa.demo.dal.dataobject.ApiTable;
import com.xboot.jpa.demo.service.ApiTableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 注释
 *
 * @author xboot
 **/
@SpringBootTest(classes = JpaDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTableRepositoryTest {

    @Autowired
    ApiTableRepository apiTableRepository;

    @Autowired
    ApiTableService apiTableService;

    @BeforeEach
    void setUp() {
        ApiTable apiTable = new ApiTable();
        apiTable.setTableId(1L);
        apiTable.setTableName("pt_test");
        apiTable.setTableComment("test table");
        apiTableRepository.save(apiTable);
    }


    @Test
    void testLoad() throws Exception {
        apiTableRepository.findById(1L).ifPresent(apiTable -> assertEquals("pt_test", apiTable.getTableName()));
    }

    @Test
    void testDeleteData() {
        apiTableService.deleteApiTableData(1L);

    }

    @Test
    void testDropTable() {
        apiTableService.dropTable("api_table1");
    }
}