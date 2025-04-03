package com.ch.clinking.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@TableName("`user_set`")
public class UserSet implements Serializable {

    @TableId
    private String account;

    private Integer showSonMer;

}
