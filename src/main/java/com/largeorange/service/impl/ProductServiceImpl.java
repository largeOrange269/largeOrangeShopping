package com.largeorange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.largeorange.mapper.ProductMapper;
import com.largeorange.pojo.Products;
import com.largeorange.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Products> implements ProductService {
}
