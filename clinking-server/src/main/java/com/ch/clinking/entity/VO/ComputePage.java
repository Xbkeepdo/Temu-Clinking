package com.ch.clinking.entity.VO;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

public class ComputePage<T> {

    private Page<T> pageData;  // 原分页数据
    private double totalSales;  // 新增的总和字段

    public ComputePage() {
    }

    public Page<T> getPageData() {
        return pageData;
    }

    public void setPageData(Page<T> pageData) {
        this.pageData = pageData;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}
