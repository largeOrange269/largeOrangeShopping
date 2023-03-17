package com.largeorange.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/17
 * @since 1.0
 */

@Data
public class Orders {
    private Integer id;
    private Integer userId;
    private Double balance;
    private String productName;
    private Integer productNumber;
    private Integer productStatus;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime payTime;
}
