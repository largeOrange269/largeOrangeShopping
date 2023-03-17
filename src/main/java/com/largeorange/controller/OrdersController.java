package com.largeorange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.largeorange.common.R;
import com.largeorange.pojo.Orders;
import com.largeorange.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/17
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 显示订单数据
     * @param userid
     * @return
     */
    @PostMapping("/message")
    public R<List<Orders>> message(Integer userid){
        //按照用户id查询订单
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId,userid);
        List<Orders> orders = ordersService.list(wrapper);
        if (orders!=null) {
            return R.success(orders);
        }
        return R.error("后台接口错误，请联系管理员！");
    }

    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R<String> delete(Integer id){
        boolean result = ordersService.removeById(id);
        if (result){
            return R.success("删除成功！");
        }
        return R.error("后台接口错误，请联系管理员！");
    }

    /**
     * 清空订单
     * @return
     */
    @DeleteMapping("/deleteAll")
    public R<String> deleteAll(){
        boolean result = ordersService.remove(null);
        if (result){
            return R.success("已全部清空！");
        }
        return R.error("暂无订单，请先购买！");
    }

    /**
     * 签收订单
     * @param id
     * @return
     */
    @GetMapping("/sure")
    public R<String> sure(Integer id){
        Orders orders = ordersService.getById(id);
        orders.setProductStatus(1);
        boolean result = ordersService.updateById(orders);
        if (result){
            return R.success("签收成功");
        }
        return R.error("后台接口错误，请联系管理员！");
    }
}
