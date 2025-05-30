package com.xboot.jpa.demo.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页工具类，提供Page对象转换功能
 */
public class PageUtils {

    /**
     * 将Spring Page转换为SimplePage
     */
    public static <T> SimplePage<T> toSimplePage(Page<T> page) {
        if (page == null) {
            return emptyPage();
        }
        return SimplePage.fromSpringPage(page);
    }

    /**
     * 批量转换Page列表为SimplePage列表
     */
    public static <T> List<SimplePage<T>> toSimplePages(List<Page<T>> pages) {
        return pages.stream()
                .map(PageUtils::toSimplePage)
                .collect(Collectors.toList());
    }

    /**
     * 创建一个空的SimplePage
     */
    public static <T> SimplePage<T> emptyPage() {
        return new SimplePage<>(0, 0, 0, List.of(), Sort.unsorted());
    }

    /**
     * 检查SimplePage是否为空
     */
    public static <T> boolean isEmpty(SimplePage<T> page) {
        return page == null || page.getContent() == null || page.getContent().isEmpty();
    }
}
