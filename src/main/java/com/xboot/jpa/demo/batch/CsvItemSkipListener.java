package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.BatchLogError;
import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import com.xboot.jpa.demo.dal.h2.BatchLogErrorRepository;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * 注释
 *
 * @author xboot
 **/
@Component
public class CsvItemSkipListener implements SkipListener<CsvItem, CsvItem> {
    @Resource
    BatchLogErrorRepository batchLogErrorRepository;
    @Override
    public void onSkipInRead(Throwable t) {

        batchLogErrorRepository.save(new BatchLogError(null, t.getMessage(),   ExceptionUtils.getStackTrace(t)));
    }
}
