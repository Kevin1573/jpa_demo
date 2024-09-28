package com.xboot.jpa.demo.batch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.batch.item.file.transform.DefaultFieldSet;

@Setter
@Getter
@ToString
public class CustomFieldSet extends DefaultFieldSet {

    private int lineNumber;

    public CustomFieldSet(int lineNumber, String[] values) {
        super(values);
        this.lineNumber = lineNumber;
    }
    public CustomFieldSet(String[] values) {
        super(values);
    }

}