package com.ch.clinking.entity.Dto;

public class SalesCountDTO {
    private String skcId;  // 对应数据库的 skc_id
    private Long count;    // 对应 COUNT(*) 别名

    public String getSkcId() {
        return skcId;
    }

    public void setSkcId(String skcId) {
        this.skcId = skcId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
