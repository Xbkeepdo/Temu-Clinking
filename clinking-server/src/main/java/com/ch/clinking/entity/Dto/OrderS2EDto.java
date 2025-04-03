package com.ch.clinking.entity.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class OrderS2EDto {

    private String shopId;

    private String skcId;

    private String state;

    @TableField(select = false,exist = false)
    private String title;

    @TableField(select = false,exist = false)
    private String firstImage = "default.png";

    @TableField(select = false,exist = false)
    private String productNumber="";

    @JsonProperty("XXS")
    private int XXS = 0;

    @JsonProperty("XS")
    private int XS = 0;

    @JsonProperty("S")
    private int S = 0;

    @JsonProperty("M")
    private int M = 0;

    @JsonProperty("L")
    private int L = 0;

    @JsonProperty("XL")
    private int XL = 0;

    @JsonProperty("XXL")
    private int XXL = 0;

    @JsonProperty("Plus_0XL")
    private int Plus_0XL = 0;

    @JsonProperty("Plus_1XL")
    private int Plus_1XL = 0;

    @JsonProperty("Plus_2XL")
    private int Plus_2XL = 0;

    @JsonProperty("Plus_3XL")
    private int Plus_3XL = 0;

    @JsonProperty("Plus_4XL")
    private int Plus_4XL = 0;

    @JsonProperty("Plus_5XL")
    private int Plus_5XL = 0;

    @JsonProperty("Plus_6XL")
    private int Plus_6XL = 0;

    @JsonProperty("make_XXS")
    private int make_XXS = 0;

    @JsonProperty("make_XS")
    private int make_XS = 0;

    @JsonProperty("make_S")
    private int make_S = 0;

    @JsonProperty("make_M")
    private int make_M = 0;

    @JsonProperty("make_L")
    private int make_L = 0;

    @JsonProperty("make_XL")
    private int make_XL = 0;

    @JsonProperty("make_XXL")
    private int make_XXL = 0;

    @JsonProperty("make_Plus_0XL")
    private int make_Plus_0XL = 0;

    @JsonProperty("make_Plus_1XL")
    private int make_Plus_1XL = 0;

    @JsonProperty("make_Plus_2XL")
    private int make_Plus_2XL = 0;

    @JsonProperty("make_Plus_3XL")
    private int make_Plus_3XL = 0;

    @JsonProperty("make_Plus_4XL")
    private int make_Plus_4XL = 0;

    @JsonProperty("make_Plus_5XL")
    private int make_Plus_5XL = 0;

    @JsonProperty("make_Plus_6XL")
    private int make_Plus_6XL = 0;

    @JsonProperty("activate_XXS")
    private int activate_XXS = 0;

    @JsonProperty("activate_XS")
    private int activate_XS = 0;

    @JsonProperty("activate_S")
    private int activate_S = 0;

    @JsonProperty("activate_M")
    private int activate_M = 0;

    @JsonProperty("activate_L")
    private int activate_L = 0;

    @JsonProperty("activate_XL")
    private int activate_XL = 0;

    @JsonProperty("activate_XXL")
    private int activate_XXL = 0;

    @JsonProperty("activate_Plus_0XL")
    private int activate_Plus_0XL = 0;

    @JsonProperty("activate_Plus_1XL")
    private int activate_Plus_1XL = 0;

    @JsonProperty("activate_Plus_2XL")
    private int activate_Plus_2XL = 0;

    @JsonProperty("activate_Plus_3XL")
    private int activate_Plus_3XL = 0;

    @JsonProperty("activate_Plus_4XL")
    private int activate_Plus_4XL = 0;

    @JsonProperty("activate_Plus_5XL")
    private int activate_Plus_5XL = 0;

    @JsonProperty("activate_Plus_6XL")
    private int activate_Plus_6XL = 0;

}
