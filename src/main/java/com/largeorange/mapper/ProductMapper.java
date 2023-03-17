package com.largeorange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.largeorange.pojo.Products;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Products> {
}
