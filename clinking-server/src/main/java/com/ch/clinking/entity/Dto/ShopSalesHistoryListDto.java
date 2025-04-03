package com.ch.clinking.entity.Dto;

import com.ch.clinking.entity.SalesHistoryShop;
import com.ch.clinking.entity.Shop;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class ShopSalesHistoryListDto {

    private String shopId;

    private List<SalesHistoryShop> salesHistoryList;


}
