package com.xboot.jpa.demo.common;

import org.springframework.data.domain.Sort;
import java.util.List;

/**
 * 简化分页对象，只包含核心分页属性
 * @param <T> 数据类型
 */
public class SimplePage<T> {
    private int size;            // 每页大小
    private int totalPages;      // 总页数
    private long totalElements;  // 总记录数
    private List<T> content;     // 当前页数据列表
    private Sort sort;           // 排序信息

    public SimplePage() {
    }

    public SimplePage(int size, int totalPages, long totalElements, List<T> content, Sort sort) {
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.content = content;
        this.sort = sort;
    }

    // 从Spring Page对象转换
    public static <T> SimplePage<T> fromSpringPage(org.springframework.data.domain.Page<T> page) {
        return new SimplePage<>(
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent(),
                page.getSort()
        );
    }

    // Getter和Setter
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SimplePage{" +
                "size=" + size +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", content=" + content +
                ", sort=" + sort +
                '}';
    }
}
