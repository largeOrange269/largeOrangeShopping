package com.largeorange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.largeorange.common.R;
import com.largeorange.pojo.Products;
import com.largeorange.pojo.Users;
import com.largeorange.service.ProductService;
import com.largeorange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    /**
     * 界面初始化
     * @return
     */
    @GetMapping("/index")
    public R<List<Products>> index(String username){
        //查询用户余额
        LambdaQueryWrapper<Users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Users::getUsername,username);
        Users user = userService.getOne(wrapper);
        //查询product
        List<Products> list = productService.list();
        System.out.println(list);
        //封装返回
        if (list!=null) {
            return R.success(list,user.getRecharge().toString());
        }
        return R.error("暂无商品，请联系管理员！");
    }

    /**
     * 商品详情
     * @param id
     * @return
     */
    @GetMapping("/message")
    public R<Products> message(Integer id){
        //条件构造器
        LambdaQueryWrapper<Products> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id!=null,Products::getId,id);
        //取得产品对象
        Products product = productService.getOne(wrapper);
        if (product!=null){
            return R.success(product);
        }
        return R.error("后台接口错误，请联系管理员！");
    }

}
