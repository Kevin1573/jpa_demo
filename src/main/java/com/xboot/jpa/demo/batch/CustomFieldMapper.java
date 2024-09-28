package com.xboot.jpa.demo.batch;


import org.springframework.validation.BindException;

public class CustomFieldMapper {

    public String[] mapFieldSet(CustomFieldSet fieldSet) throws BindException {
        String field2 = fieldSet.readString("field2");

        // field2 最后一个字符为 a 时抛出异常
        if (field2.charAt(field2.length() - 1) == 'a') {
            int lineNumber = fieldSet.getLineNumber();  // 使用自定义的 CustomFieldSet
            throw new BindException(field2, "Invalid field2 at line " + lineNumber);
        }

        return new String[] {fieldSet.readString("field1"), fieldSet.readString("field2")};
    }
}