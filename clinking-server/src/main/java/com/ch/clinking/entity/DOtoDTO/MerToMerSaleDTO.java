package com.ch.clinking.entity.DOtoDTO;

import com.ch.clinking.entity.Dto.MerSaleDTO;
import com.ch.clinking.entity.Merchandise;
import com.ch.clinking.platform.util.Config;
import lombok.Getter;

public class MerToMerSaleDTO {

    @Getter
    private static final MerToMerSaleDTO instance = new MerToMerSaleDTO();

    public MerSaleDTO doToDTO(Merchandise merchandise) {
        if (merchandise == null) {
            return null;
        } else {
            MerSaleDTO dto = new MerSaleDTO();
            dto.setId(merchandise.getId());
            dto.setShopId(merchandise.getShopId());
            dto.setSpuId(merchandise.getSpuId());
            dto.setSkcId(merchandise.getSkcId());
            dto.setProductNumber(merchandise.getProductNumber());
            dto.setTitle(merchandise.getTitle());
            dto.setFirstImage(merchandise.getFirstImage());
            dto.setColor(merchandise.getColor());
            dto.setState(merchandise.getState());
            dto.setPrice(merchandise.getPrice());
            dto.setCreateTime(merchandise.getCreateTime());
            dto.setShopType(merchandise.getShopType());
            dto.setSizeInfo(merchandise.getSizeInfo());
            dto.setWeight(merchandise.getWeight());

            dto.setInHouseStock_XXS(merchandise.getInHouseStock_XXS());
            dto.setInHouseStock_XS(merchandise.getInHouseStock_XS());
            dto.setInHouseStock_S(merchandise.getInHouseStock_S());
            dto.setInHouseStock_M(merchandise.getInHouseStock_M());
            dto.setInHouseStock_L(merchandise.getInHouseStock_L());
            dto.setInHouseStock_XL(merchandise.getInHouseStock_XL());
            dto.setInHouseStock_XXL(merchandise.getInHouseStock_XXL());

            dto.setInHouseStock_Plus_0XL(merchandise.getInHouseStock_Plus_0XL());
            dto.setInHouseStock_Plus_1XL(merchandise.getInHouseStock_Plus_1XL());
            dto.setInHouseStock_Plus_2XL(merchandise.getInHouseStock_Plus_2XL());
            dto.setInHouseStock_Plus_3XL(merchandise.getInHouseStock_Plus_3XL());
            dto.setInHouseStock_Plus_4XL(merchandise.getInHouseStock_Plus_4XL());
            dto.setInHouseStock_Plus_5XL(merchandise.getInHouseStock_Plus_5XL());
            dto.setInHouseStock_Plus_6XL(merchandise.getInHouseStock_Plus_6XL());

            dto.setPlatformStock_XXS(merchandise.getPlatformStock_XXS());
            dto.setPlatformStock_XS(merchandise.getPlatformStock_XS());
            dto.setPlatformStock_S(merchandise.getPlatformStock_S());
            dto.setPlatformStock_M(merchandise.getPlatformStock_M());
            dto.setPlatformStock_L(merchandise.getPlatformStock_L());
            dto.setPlatformStock_XL(merchandise.getPlatformStock_XL());
            dto.setPlatformStock_XXL(merchandise.getPlatformStock_XXL());

            dto.setPlatformStock_Plus_0XL(merchandise.getPlatformStock_Plus_0XL());
            dto.setPlatformStock_Plus_1XL(merchandise.getPlatformStock_Plus_1XL());
            dto.setPlatformStock_Plus_2XL(merchandise.getPlatformStock_Plus_2XL());
            dto.setPlatformStock_Plus_3XL(merchandise.getPlatformStock_Plus_3XL());
            dto.setPlatformStock_Plus_4XL(merchandise.getPlatformStock_Plus_4XL());
            dto.setPlatformStock_Plus_5XL(merchandise.getPlatformStock_Plus_5XL());
            dto.setPlatformStock_Plus_6XL(merchandise.getPlatformStock_Plus_6XL());

            dto.setActivate_XXS(merchandise.isActivate_XXS());
            dto.setActivate_XS(merchandise.isActivate_XS());
            dto.setActivate_S(merchandise.isActivate_S());
            dto.setActivate_M(merchandise.isActivate_M());
            dto.setActivate_L(merchandise.isActivate_L());
            dto.setActivate_XL(merchandise.isActivate_XL());
            dto.setActivate_XXL(merchandise.isActivate_XXL());

            dto.setActivate_Plus_0XL(merchandise.isActivate_Plus_0XL());
            dto.setActivate_Plus_1XL(merchandise.isActivate_Plus_1XL());
            dto.setActivate_Plus_2XL(merchandise.isActivate_Plus_2XL());
            dto.setActivate_Plus_3XL(merchandise.isActivate_Plus_3XL());
            dto.setActivate_Plus_4XL(merchandise.isActivate_Plus_4XL());
            dto.setActivate_Plus_5XL(merchandise.isActivate_Plus_5XL());
            dto.setActivate_Plus_6XL(merchandise.isActivate_Plus_6XL());

            return dto;
        }
    }

}
