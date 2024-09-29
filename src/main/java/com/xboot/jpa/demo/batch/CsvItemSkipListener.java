package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.BatchErrorLog;
import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import com.xboot.jpa.demo.dal.h2.BatchLogErrorRepository;
import com.xboot.jpa.demo.util.GsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释
 *
 * @author xboot
 **/
@Slf4j
@Component
public class CsvItemSkipListener implements SkipListener<CsvItem, CsvItem> {
    @Resource
    BatchLogErrorRepository batchLogErrorRepository;

    @Override
    public void onSkipInRead(Throwable t) {
        log.error("onSkipInRead {}", t.getMessage());
        Map<String, Object> errorMessage = new HashMap<>();
        errorMessage.put("@clazz", t.getClass().getName());
        if (t instanceof FlatFileParseException flatfileparseexception) {
            errorMessage.put("lineNumber", flatfileparseexception.getLineNumber());
            errorMessage.put("input", flatfileparseexception.getInput());
        }
        batchLogErrorRepository.save(new BatchErrorLog(null, "importUserJob",
                GsonUtils.toJson(errorMessage), ExceptionUtils.getStackTrace(t),false));
    }
}
