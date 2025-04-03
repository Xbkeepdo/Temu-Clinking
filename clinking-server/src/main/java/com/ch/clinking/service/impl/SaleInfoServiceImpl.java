package com.ch.clinking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.clinking.entity.Dto.MerSaleDTO;
import com.ch.clinking.entity.Order;
import com.ch.clinking.entity.SaleInfo;
import com.ch.clinking.mapper.SaleInfoMapper;
import com.ch.clinking.service.SaleInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Service("saleInfoServer")
public class SaleInfoServiceImpl extends ServiceImpl<SaleInfoMapper, SaleInfo> implements SaleInfoService {

    @Resource
    private SaleInfoMapper saleInfoMapper;

    @Override
    public Map<String, String> autoMakeOrderOneSkc(MerSaleDTO merSaleDTO) {
        String today = "", auto1Day = "", auto2Day = "", auto3Day = "";
        try {

            // 需要处理的尺码列表
            String[] sizes = {"XXS", "XS", "S", "M", "L", "XL", "XXL", "Plus_0XL", "Plus_1XL", "Plus_2XL", "Plus_3XL", "Plus_4XL", "Plus_5XL", "Plus_6XL"};

            for (String size : sizes) {

                // 使用反射调用 isActivate_XXS、getInHouseStock_XXS 等方法
                Method isActivateMethod = merSaleDTO.getClass().getMethod("isActivate_" + size);
                boolean isActivated = (boolean) isActivateMethod.invoke(merSaleDTO);

                if (isActivated) {

                    boolean saleFlag = false;

                    // 处理销售信息
                    for (SaleInfo info : merSaleDTO.getSaleInfoList()) {
                        if (info.getClassName().contains("-" + size)) {


                            Method getStockMethod = merSaleDTO.getClass().getMethod("getInHouseStock_" + size);
                            int self_stock = (int) getStockMethod.invoke(merSaleDTO);


                            // 处理订单信息
                            int finalOrderSizeNum = 0;
                            for (Order orderDto : merSaleDTO.getOrderList()) {
                                Method getOrderMethod = orderDto.getClass().getMethod("get" + size);
                                Method getMakeMethod = orderDto.getClass().getMethod("getMake_" + size);

                                if ((int) getMakeMethod.invoke(orderDto) != 0) {
                                    finalOrderSizeNum += (int) getMakeMethod.invoke(orderDto);
                                } else {
                                    finalOrderSizeNum += (int) getOrderMethod.invoke(orderDto);
                                }
                            }


                            String _today = CalculateMakeOrder(info.getWarehouseInventoryNum(), info.getWaitReceiveNum(), self_stock, info.getLastSevenDaysSaleVolume(), 11, 5, finalOrderSizeNum);
                            String _1day = CalculateMakeOrder(info.getWarehouseInventoryNum(), info.getWaitReceiveNum(), self_stock, info.getLastSevenDaysSaleVolume(), 11 + 1, 5, finalOrderSizeNum);
                            String _2day = CalculateMakeOrder(info.getWarehouseInventoryNum(), info.getWaitReceiveNum(), self_stock, info.getLastSevenDaysSaleVolume(), 11 + 2, 5, finalOrderSizeNum);
                            String _3day = CalculateMakeOrder(info.getWarehouseInventoryNum(), info.getWaitReceiveNum(), self_stock, info.getLastSevenDaysSaleVolume(), 11 + 3, 5, finalOrderSizeNum);


                            today = _today;
                            auto1Day=_1day;
                            auto2Day=_2day;
                            auto3Day=_3day;

                        }
                    }
                    if (!saleFlag) {
                        today = "0";
                        auto1Day="0";
                        auto2Day="0";
                        auto3Day="0";
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> make = new HashMap<>();
        make.put("today", today);
        make.put("auto1Day", auto1Day);
        make.put("auto2Day", auto2Day);
        make.put("auto3Day", auto3Day);

        return make;
    }



    private String CalculateMakeOrder(int temu_stock, int on_way, int self_stock, int S_i, int W, int D, int ZZ) {

        double S = 0;
        if (S_i != 0) {
            S = Double.parseDouble(String.valueOf(S_i)) / 7.0;
        }


        // S 平均销售数量
        // W = 7 生产周期
        // D = 5 每单补货量的天数
        // ZZ 真正做的数量
        double Num = 0; // 需要下单的数量

        while ((temu_stock + on_way + self_stock + ZZ + Num - S * W) < 0) {
            Num += D * S;
        }

//        if ((temu_stock + on_way + self_stock - S * W) <= 0) {
//            binding.calculateMakeOrderUrgent.setText("是");
//        } else {
//            binding.calculateMakeOrderUrgent.setText("否");
//        }


        String formattedValue = String.format("%.0f", Num);

        return formattedValue;

    }




}
