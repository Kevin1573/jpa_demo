package com.xboot.jpa.demo.batch;

import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.concurrent.atomic.AtomicLong;

// 自定义的FieldSetMapper实现
@Slf4j
public class CustomFieldSetMapper implements FieldSetMapper<CsvItem> {
    private final AtomicLong lineNumber = new AtomicLong(0);

    @Override
    public CsvItem mapFieldSet(FieldSet fieldSet) throws BindException {
        long number = lineNumber.incrementAndGet();
        String field2 = fieldSet.readString("field2");
        // field2 最后一个字符为 a时抛出异常
        if (field2.charAt(field2.length() - 1) == 'a') {
            //fieldSet.getLineNumber();  // 获取当前行号
            log.error("===>Invalid field2 at line {}", number);
            throw new BindException(field2, "Invalid field2 at line " + number);
        }

        return new CsvItem(null, fieldSet.readString("field1"), field2);
    }
}