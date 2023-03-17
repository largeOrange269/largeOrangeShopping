package com.largeorange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.largeorange.mapper.OrderMessageMapper;
import com.largeorange.pojo.OrderMessage;
import com.largeorange.pojo.Products;
import com.largeorange.pojo.Users;
import com.largeorange.service.OrderMessageService;
import com.largeorange.service.ProductService;
import com.largeorange.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@Service
public class OrderMessageServiceImpl extends ServiceImpl<OrderMessageMapper, OrderMessage> implements OrderMessageService {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    /**
     * 添加产品到购物车
     * @param id
     * @param username
     * @return
     */
    @Override
    @Transactional
    public OrderMessage addOrder(Integer id, String username) {
        //准备载入购物车信息
        OrderMessage orderMessage = new OrderMessage();
        //查询产品
        Products product = productService.getById(id);
        if (product.getStock()==0){
            return null;
        }
        //购物车有没有
        LambdaQueryWrapper<OrderMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderMessage::getProductId,id);
        OrderMessage message = this.getOne(queryWrapper);
        //没有
        if (message == null) {
            //查询用户
            LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Users::getUsername, username);
            Users user = userService.getOne(wrapper);
            //产品id
            orderMessage.setProductId(product.getId());
            //用户id
            orderMessage.setUserId(user.getId());
            //产品状态
            orderMessage.setProductStatus(0);
            //产品名称
            orderMessage.setProductName(product.getName());
            //产品价格
            orderMessage.setProductBalance(product.getPrice());
            //产品数量
            orderMessage.setProductNumber(1);
            /*//产品数量减一
            product.setStock(product.getStock()-1);
            //更新产品
            productService.updateById(product);*/
            //添加至购物车
            this.save(orderMessage);
        } else {
            //购物车有
            message.setProductNumber(message.getProductNumber()+1);
            //产品数量减一
            /*product.setStock(product.getStock()-1);
            //更新产品
            productService.updateById(product);*/
            this.updateById(message);
            return message;
        }
        return orderMessage;
    }

    /**
     * 购物车商品数量减一
     * @param id
     * @return
     */
    @Override
    @Transactional
    public OrderMessage pullOrder(Integer id) {
        //通过产品id查询购物车信息
        LambdaQueryWrapper<OrderMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderMessage::getProductId,id);
        OrderMessage message = this.getOne(queryWrapper);
        //如果产品数量等于0或者订单不存在
        if (message==null||message.getProductNumber()==0){
            return null;
        }
        //购物车产品数量减一
        message.setProductNumber(message.getProductNumber()-1);
        /*//查询产品
        Products product = productService.getById(id);
        //产品数量加一
        product.setId(product.getStock()+1);
        //更新产品
        productService.updateById(product);*/
        //保存
        this.updateById(message);
        return message;
    }

    /**
     * 批量移除
     * @param productId
     * @return
     */
    @Override
    public boolean removeByProductId(Integer productId) {
        //根据产品id查询订单
        LambdaQueryWrapper<OrderMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderMessage::getProductId,productId);
        OrderMessage orderMessage = this.getOne(wrapper);
        //根据产品id移除购物车订单
        boolean remove = this.removeById(orderMessage);
        return remove;
    }
}
