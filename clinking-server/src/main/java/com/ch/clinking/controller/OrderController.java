package com.ch.clinking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ch.clinking.entity.*;
import com.ch.clinking.entity.Dto.OrderS2EDto;
import com.ch.clinking.service.MerService;
import com.ch.clinking.service.OrderService;
import com.ch.clinking.service.ProductionInfoService;
import com.ch.clinking.service.SameKindService;
import com.ch.clinking.util.DateUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private MerService merService;

    @Resource
    private ProductionInfoService productionInfoService;

    @Resource
    private SameKindService sameKindService;

    @Transactional
    @PostMapping("/updateOrder")
    public Msg save(@RequestBody Order order) throws InterruptedException {
        SameKind findSon = sameKindService.getOne(new QueryWrapper<SameKind>().eq("skcId", order.getSkcId()));
        if (findSon != null) {
            order.setSkcId(findSon.getMainSkcId());
        }
        boolean saved = orderService.saveOrUpdate(order);

        System.out.println("-----order--"+order);

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("保存或更新订单失败");
        }
    }

    @Transactional
    @PostMapping("/updateProductionInfo")
    public Msg updateProductionInfo(@RequestBody ProductionInfo productionInfo) throws InterruptedException {
        boolean saved = productionInfoService.saveOrUpdate(productionInfo);

        System.out.println("-----productionInfo--"+productionInfo);

        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("更新生产周期失败");
        }
    }

    /**
     * 查询订单列表
     *
     * @return
     */
    @PostMapping("/selectOrderByShopId")
    public List<Order> selectById(@RequestParam("shopId")String shopId) {
        List<Order> orderList = orderService.list(new QueryWrapper<Order>().eq("shopId", shopId).orderByDesc("createdTime"));
        System.out.println("orderList" + orderList);

        createOrderDto(orderList);

        System.out.println("----------------"+orderList);
        return orderList;
    }

    @Transactional
    @PostMapping("/updateOrderState2Finished")
    public Msg selectBySkcId(@RequestParam("orderId") String orderId) {
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("orderId", orderId));
        order.setState("已出货");
        order.setUpdateTime(DateUtil.getFormatTime());
        boolean saved = orderService.saveOrUpdate(order);
        if (saved) {
            // 确保事务成功提交后，立即返回响应
            return Msg.ok();
        } else {
            return Msg.error("更新状态失败");
        }
    }

    @Transactional
    @PostMapping("/selectOrderTag")
    public List<Order> selectOrderTag(@RequestParam("skcId") String skcId) {
        List<Order> orderList = orderService.list(new QueryWrapper<Order>().eq("skcId", skcId).eq("state", "待出货").orderByDesc("createdTime"));
        return orderList;
    }

    @PostMapping("/delete")
    public Msg delete(@RequestParam("orderId")String orderId){

        orderService.remove(Wrappers.<Order>query().lambda().eq(Order::getOrderId,orderId));

        return Msg.ok();
    }

    /**
     * 通过指定日期查询订单
     *
     * @return
     */
    @PostMapping("/selectOrderOneDay")
    public List<Order> selectOrderOneDay(@RequestParam("shopId")String shopId, @RequestParam("date")String date) {

        // 定义日期格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");

        // 将传递的日期转换为 LocalDate
        LocalDate localDate = LocalDate.parse(date, dateFormatter);

        // 获取当天的起始时间和结束时间
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        // 将 LocalDateTime 格式化为字符串
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startOfDayStr = startOfDay.format(dateTimeFormatter);
        String endOfDayStr = endOfDay.format(dateTimeFormatter);

        // 构建查询条件并执行查询
        List<Order> orderList = orderService.list(
                new QueryWrapper<Order>()
                        .eq("shopId",shopId)
                        .ge("createdTime", startOfDayStr)
                        .le("createdTime", endOfDayStr)
                        .orderByDesc("createdTime")
        );

        createOrderDto(orderList);

        System.out.println("++++++++++++orderList++++++++++++"+orderList);

        return orderList;
    }


    /**
     * 通过指定时间段查询订单
     *
     * @return
     */
    @PostMapping("/selectOrderS2E")
    public List<OrderS2EDto> selectOrderS2E(@RequestParam("shopId")String shopId, @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate) {

        List<OrderS2EDto> orderS2DList = orderService.getOrderS2D(shopId, startDate, endDate);
        for (OrderS2EDto order : orderS2DList) {
            Merchandise merInfo = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", order.getSkcId()));
            order.setTitle(merInfo.getTitle());
            order.setFirstImage(merInfo.getFirstImage());
            order.setProductNumber(merInfo.getProductNumber());
        }

        System.out.println("++++++++++++orderList++++++++++++"+orderS2DList);

        return orderS2DList;
    }

    /**
     * 通过订单状态和店铺Id查询15天内的订单
     *
     * @return
     */
    @PostMapping("/selectOrderByStage")
    public List<Order> selectOrderByStage(@RequestParam("shopId")String shopId, @RequestParam("orderState")String orderState) {

        // 定义日期格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 计算15天前的时间
        LocalDateTime startOfDay = now.minusDays(15).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = now;

        // 将 LocalDateTime 格式化为字符串
        String startOfDayStr = startOfDay.format(dateTimeFormatter);
        String endOfDayStr = endOfDay.format(dateTimeFormatter);

        // 构建查询条件并执行查询
        List<Order> orderList = orderService.list(
                new QueryWrapper<Order>()
                        .eq("shopId",shopId)
                        .eq("state", orderState)
                        .ge("createdTime", startOfDayStr)
                        .le("createdTime", endOfDayStr)
                        .orderByDesc("createdTime")
        );

        createOrderDto(orderList);

        System.out.println("++++++++++++selectOrderByStage++++++++++++"+orderList);

        return orderList;
    }

    /**
     * 通过店铺Id查询15天内的所有订单
     *
     * @return
     */
    @PostMapping("/selectAllOrder15Day")
    public List<Order> selectAllOrder15Day(@RequestParam("shopId")String shopId) {

        // 定义日期格式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 计算15天前的时间
        LocalDateTime startOfDay = now.minusDays(15).toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = now;

        // 将 LocalDateTime 格式化为字符串
        String startOfDayStr = startOfDay.format(dateTimeFormatter);
        String endOfDayStr = endOfDay.format(dateTimeFormatter);

        // 构建查询条件并执行查询
        List<Order> orderList = orderService.list(
                new QueryWrapper<Order>()
                        .eq("shopId",shopId)
                        .ge("createdTime", startOfDayStr)
                        .le("createdTime", endOfDayStr)
                        .orderByDesc("createdTime")
        );

        createOrderDto(orderList);

        System.out.println("++++++++++++selectAllOrder15Day++++++++++++"+orderList);

        return orderList;
    }

    /**
     * 通过SKC查询订单
     *
     * @return
     */
    @PostMapping("/selectOrderByOrderId")
    public Order selectOrderByOrderId(@RequestParam("orderId")String orderId) {


        // 构建查询条件并执行查询
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("orderId",orderId));

        Merchandise merInfo = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", order.getSkcId()));
        order.setTitle(merInfo.getTitle());
        order.setFirstImage(merInfo.getFirstImage());
        order.setProductNumber(merInfo.getProductNumber());

        return order;
    }

    private void createOrderDto(List<Order> orderList) {
        for (Order order : orderList) {
            Merchandise merInfo = merService.getOne(new QueryWrapper<Merchandise>().eq("skcId", order.getSkcId()));
            order.setTitle(merInfo.getTitle());
            order.setFirstImage(merInfo.getFirstImage());
            order.setProductNumber(merInfo.getProductNumber());
        }
    }


}
