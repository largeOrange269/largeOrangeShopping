package com.largeorange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.largeorange.common.R;
import com.largeorange.pojo.OrderMessage;
import com.largeorange.service.OrderMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/16
 * @since 1.0
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderMessageController {
    @Autowired
    private OrderMessageService orderMessageService;

    /**
     * 查看用户是否有订单
     * @param userid
     * @return
     */
    @GetMapping("/number")
    public R<List<OrderMessage>> getOrderNumber(Integer userid){
        LambdaQueryWrapper<OrderMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMessage::getUserId,userid);
        //查询当前用户的所有订单数量
        List<OrderMessage> orderMessages = orderMessageService.list(wrapper);
        if (orderMessages!=null){
            return R.success(orderMessages);
        }
        return R.error("该用户没有订单！");
    }

    /**
     * 添加产品
     * @param id
     * @param username
     * @return
     */
    @PutMapping("/add")
    public R<OrderMessage> orderAdd(Integer id,String username){
        OrderMessage order = orderMessageService.addOrder(id, username);
        //如果对象为null
        if (order!=null){
            return R.success(order);
        }
        return R.error("没有那么多库存啦！");
    }

    /**
     * 减少产品
     * @param id
     * @return
     */
    @PutMapping("/pull")
    public R<OrderMessage> orderPull(Integer id){
        OrderMessage order = orderMessageService.pullOrder(id);
        if (order!=null){
            return R.success(order);
        }
        return R.error("购物车库存已清空");
    }
    /**
     * 移除购物车订单
     * @param productId
     * @return
     */
    @DeleteMapping("/remove")
    public R<String> removeByProductId(Integer productId){
        boolean target = orderMessageService.removeByProductId(productId);
        if (target){
            return R.success("移除成功！");
        }
        return R.error("删除失败，请联系管理员！");
    }
}
