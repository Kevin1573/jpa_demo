package com.xboot.jpa.demo.batch;

import lombok.SneakyThrows;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;


public class CustomFlatFileItemReader extends FlatFileItemReaderBuilder<CustomFieldSet> {

    public CustomFlatFileItemReader() {
        name("customReader");
        resource(new ClassPathResource("sample.txt"));
        linesToSkip(1); // 跳过标题行
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("field1", "field2");
        lineTokenizer(tokenizer);
    }

    @SneakyThrows
    @Override
    public FlatFileItemReader<CustomFieldSet> build() {
        FlatFileItemReader<CustomFieldSet> reader = super.build();
        reader.afterPropertiesSet();
        return reader;
    }
}
