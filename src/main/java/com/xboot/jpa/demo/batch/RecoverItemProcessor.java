package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.BatchErrorLog;
import com.xboot.jpa.demo.dto.CsvItemProps;
import com.xboot.jpa.demo.util.GsonUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@Component
public class RecoverItemProcessor implements ItemProcessor<BatchErrorLog, CsvItemProps> {

    @Autowired
    public RecoverItemProcessor(){
        // document why this constructor is empty
    }

    @Override
    public CsvItemProps process(BatchErrorLog item) throws Exception {
        Map<String, Object> messageMap = GsonUtils.toMap(item.getMessage());
        String[] inputs = parseCsvData((String) messageMap.get("input"));
        CsvItemProps csvItemProps = new CsvItemProps();
        csvItemProps.setField1(inputs[0]);
        csvItemProps.setField2(inputs[1]);
        csvItemProps.setRefId(item.getId());
        return csvItemProps;
    }

    private String[] parseCsvData(String csvText) {
        // 将CSV文本转换成数组
        return csvText.split(",");
    }
}
