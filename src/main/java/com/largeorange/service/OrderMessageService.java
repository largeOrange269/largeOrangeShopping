package com.largeorange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.largeorange.pojo.OrderMessage;

public interface OrderMessageService extends IService<OrderMessage> {
    /**
     * 添加至购物车
     * @param id
     * @param username
     * @return
     */
    OrderMessage addOrder(Integer id,String username);

    /**
     * 从购物车移除
     * @param id
     * @return
     */
    OrderMessage pullOrder(Integer id);
    /**
     * 批量移除购物车产品
     * @param productId
     * @return
     */
    boolean removeByProductId(Integer productId);
}
