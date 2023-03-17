package com.largeorange.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/17
 * @since 1.0
 * 公共字段填充
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    //Insert字段填充
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert字段填充。。。。");
        metaObject.setValue("payTime", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
