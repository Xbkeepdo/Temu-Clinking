package com.ch.clinking.entity;

import java.util.List;

public class PageResult<T> {
    private List<T> records;  // 当前页数据
    private long total;        // 总记录数
    private long totalPages;   // 总页数
    private long current;      // 当前页码

    public PageResult(List<T> records, long total, long totalPages, long current) {
        this.records = records;
        this.total = total;
        this.totalPages = totalPages;
        this.current = current;
    }

    // Getters 和 Setters
    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }
}
