package com.largeorange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.largeorange.pojo.Users;

public interface UserService extends IService<Users> {
    String payFor(Double allBalance, Integer userid);
}
