package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.BatchLogError;
import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import com.xboot.jpa.demo.dal.h2.BatchLogErrorRepository;
import com.xboot.jpa.demo.util.GsonUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 注释
 *
 * @author xboot
 **/
@Component
public class RecoverItemReader implements ItemReader<List<CsvItem>> {
    private final BatchLogErrorRepository batchLogErrorRepository;

    @Autowired
    public RecoverItemReader(BatchLogErrorRepository batchLogErrorRepository) {
        this.batchLogErrorRepository = batchLogErrorRepository;
    }

    @Override
    public List<CsvItem> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        BatchLogError batchLogError = new BatchLogError();
        // 获取今天或者最近一天的数据进行恢复
        batchLogError.setJobName("importUserJob");
        List<BatchLogError> anyOneOfItem = batchLogErrorRepository.findAll(Example.of(batchLogError));

        if (!anyOneOfItem.isEmpty()) {
            return anyOneOfItem.stream().map(logErr -> {
                Map<String, Object> messageMap = GsonUtils.toMap(logErr);
                return new CsvItem(null, messageMap.get("input")[0],  messageMap.get("input")[1]);
            }).collect(Collectors.toUnmodifiableList());
        }
        return null;
    }
}
