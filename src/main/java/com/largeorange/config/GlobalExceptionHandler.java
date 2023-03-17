package com.largeorange.config;

import com.largeorange.common.R;
import com.largeorange.myexception.NumberIsNotEnoughException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/17
 * @since 1.0
 */

/**
 * 异常捕捉器
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 库存不足异常捕获
     * @param exception
     * @return
     */
    @ExceptionHandler(NumberIsNotEnoughException.class)
    public R<String> NumberIsNotEnoughExceptionGlobal(NumberIsNotEnoughException exception){
        return R.error(exception.getMessage());
    }
}
