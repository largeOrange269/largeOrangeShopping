package com.largeorange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.largeorange.common.R;
import com.largeorange.myexception.NumberIsNotEnoughException;
import com.largeorange.pojo.Users;
import com.largeorange.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<Users> login(Users user, HttpSession session) {
        log.info(user.toString());
        //实例条件构造器
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        //判断用户是否存在
        wrapper.eq(Users::getUsername, user.getUsername());
        Users getUser = userService.getOne(wrapper);
        //如果不存在，返回用户不存在信息
        if (getUser == null) {
            return R.error("用户不存在！");
        }
        //如果存在,校验密码
        if (getUser.getPassword().equals(user.getPassword())) {
            //放入Session域
            session.setAttribute("user",getUser);
            return R.success(getUser,"登录成功！");
        }
        //密码错误
        return R.error("密码错误！");
    }

    /**
     * 用户充值
     * @param balance
     * @param id
     * @return
     */
    @PutMapping("/amount")
    public R<String> amount(Double balance,Integer id){
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getId,id);
        Users user = userService.getOne(wrapper);
        user.setRecharge(balance+user.getRecharge());
        if (userService.updateById(user)){
            return R.success("充值成功");
        }
        return R.error("后台错误，请联系管理员！");
    }

    /**
     * 用户支付
     * @param
     * @param userid
     * @return
     */
    @PostMapping("/payFor")
    public R<String> payFor(Double balance,Integer userid) throws NumberIsNotEnoughException {
        //进行支付
        String message=userService.payFor(balance,userid);
        return R.success(message);
    }
}
