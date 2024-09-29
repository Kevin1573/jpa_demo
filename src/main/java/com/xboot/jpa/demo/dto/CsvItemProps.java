package com.xboot.jpa.demo.dto;

import com.xboot.jpa.demo.dal.dataobject.CsvItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 注释
 *
 * @author xboot
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CsvItemProps extends CsvItem {
    private Long refId;
}
