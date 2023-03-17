package com.largeorange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.largeorange.mapper.UserMapper;
import com.largeorange.myexception.NumberIsNotEnoughException;
import com.largeorange.pojo.OrderMessage;
import com.largeorange.pojo.Orders;
import com.largeorange.pojo.Products;
import com.largeorange.pojo.Users;
import com.largeorange.service.OrderMessageService;
import com.largeorange.service.OrdersService;
import com.largeorange.service.ProductService;
import com.largeorange.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService {
    @Autowired
    //延迟加载
    @Lazy
    private OrderMessageService orderMessageService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private ProductService productService;

    /**
     * 支付功能
     *
     * @param allBalance
     * @param userid
     * @return
     */
    @SneakyThrows
    @Override
    @Transactional
    public String payFor(Double allBalance, Integer userid){
        //查看用户余额
        Users user = this.getById(userid);
        //余额不足
        if (user.getRecharge() < allBalance) {
            return "余额不足，请先充值!";
        }
        //余额充足，开始付款
        user.setRecharge(user.getRecharge() - allBalance);
        //更改用户余额
        boolean ok = this.updateById(user);
        //付款成功
        if (ok) {
            //查询购物车
            List<OrderMessage> orderMessages = orderMessageService.list();
            //遍历购物车拷贝至订单列表
            for (OrderMessage orderMessage : orderMessages) {
                //创建订单对象
                Orders orders = new Orders();
                //设置用户id
                orders.setUserId(orderMessage.getUserId());
                //设置产品名称
                orders.setProductName(orderMessage.getProductName());
                //设置产品总价
                orders.setBalance(orderMessage.getProductBalance() * orderMessage.getProductNumber());
                //设置产品数量
                orders.setProductNumber(orderMessage.getProductNumber());
                //设置订单状态
                orders.setProductStatus(0);
                //添加至订单列表
                ordersService.save(orders);
                //获取产品对象
                LambdaQueryWrapper<Products> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Products::getId, orderMessage.getProductId());
                Products products = productService.getOne(wrapper);
                //设置产品剩余数量
                //查看剩余数量
                int number = products.getStock() - orderMessage.getProductNumber();
                if (number<0){
                    throw new NumberIsNotEnoughException("商家"+products.getName()+"库存不足，请减少购买，支付失败");
                }
                //产品数量减少
                products.setStock(number);
                productService.updateById(products);
            }
            /*orderMessages.forEach(orderMessage -> {
                //创建订单对象
                Orders orders = new Orders();
                //设置用户id
                orders.setUserId(orderMessage.getUserId());
                //设置产品名称
                orders.setProductName(orderMessage.getProductName());
                //设置产品总价
                orders.setBalance(orderMessage.getProductBalance() * orderMessage.getProductNumber());
                //设置产品数量
                orders.setProductNumber(orderMessage.getProductNumber());
                //设置订单状态
                orders.setProductStatus(0);
                //添加至订单列表
                ordersService.save(orders);
                //获取产品对象
                LambdaQueryWrapper<Products> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Products::getId, orderMessage.getProductId());
                Products products = productService.getOne(wrapper);
                //设置产品剩余数量
                //查看剩余数量
                int number = products.getStock() - orderMessage.getProductNumber();
                //产品数量减少
                products.setStock(number);
                productService.updateById(products);
            });*/

            //模拟异常，测试事务
            /*String s=null;
            s.toString();*/
            //清空购物车
            orderMessageService.remove(null);
            return "付款成功！";
        }
        return "付款失败，请联系管理员！";
    }
}
