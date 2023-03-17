package com.largeorange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.largeorange.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
