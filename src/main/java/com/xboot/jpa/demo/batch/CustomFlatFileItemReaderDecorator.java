package com.xboot.jpa.demo.batch;

import org.springframework.batch.item.file.transform.FieldSet;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;

public class CustomFlatFileItemReaderDecorator implements ItemReader<CustomFieldSet> {

    private final FlatFileItemReader<CustomFieldSet> delegate;
    private int lineNumber = 0;

    public CustomFlatFileItemReaderDecorator(FlatFileItemReader<CustomFieldSet> delegate) {
        this.delegate = delegate;
    }

    @Override
    public CustomFieldSet read() throws Exception {
        FieldSet fieldSet = delegate.read();

        if (fieldSet != null) {
            lineNumber++;
            CustomFieldSet customFieldSet = new CustomFieldSet(fieldSet.getValues());
            customFieldSet.setLineNumber(lineNumber);
            return customFieldSet;
        }

        return null;
    }
}