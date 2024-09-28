package com.xboot.jpa.demo.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

// 自定义的FieldSetMapper实现
public class PassThroughFieldSetMapper implements FieldSetMapper<String[]> {

    @Override
    public String[] mapFieldSet(FieldSet fieldSet) throws BindException {
        String field2 = fieldSet.readString("field2");
        // field2 最后一个字符为 a时抛出异常
        if (field2.charAt(field2.length() - 1) == 'a') {
            int lineNumber = 0;//fieldSet.getLineNumber();  // 获取当前行号
            throw new BindException(field2, "Invalid field2 at line " + lineNumber);
        }


        return new String[]{fieldSet.readString("field1"), field2};
    }
}