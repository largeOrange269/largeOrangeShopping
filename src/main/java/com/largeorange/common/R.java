package com.largeorange.common;

import lombok.Data;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@Data
public class R<T> {
    private Integer code;//状态码
    private String message;//消息
    private T data;//数据

    private R(){}
    /**
     * 成功(有数据)
     */
    public static<T> R<T> success(T data,String message){
        R<T> objectR = new R<>();
        objectR.setMessage(message);
        objectR.data=data;
        objectR.setCode(200);
        return objectR;
    }
    public static<T> R<T> success(T data){
        R<T> objectR = new R<>();
        objectR.data=data;
        objectR.setCode(200);
        return objectR;
    }
    public static<T> R<T> success(String message){
        R<T> objectR = new R<>();
        objectR.message=message;
        objectR.setCode(200);
        return objectR;
    }

    /**
     * 错误
     * @param message
     */
    public static<T> R<T> error(String message){
        R<T> objectR = new R<>();
        objectR.message=message;
        objectR.setCode(201);
        return objectR;
    }
}
