package com.largeorange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.largeorange.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Users> {
}
