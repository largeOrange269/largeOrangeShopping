package com.largeorange.myexception;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/17
 * @since 1.0
 */
public class NumberIsNotEnoughException extends Exception{
    public NumberIsNotEnoughException() {
    }
    public NumberIsNotEnoughException(String msg) {
        super(msg);
    }
}
