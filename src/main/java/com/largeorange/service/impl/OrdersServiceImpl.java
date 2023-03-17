package com.largeorange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.largeorange.mapper.OrdersMapper;
import com.largeorange.pojo.Orders;
import com.largeorange.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/17
 * @since 1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
