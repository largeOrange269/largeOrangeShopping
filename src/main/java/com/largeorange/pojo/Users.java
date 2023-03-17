package com.largeorange.pojo;


import lombok.Data;

@Data
/**
 * 用户实体类
 */
public class Users {
  private Integer id;
  private String username;
  private String password;
  private Double recharge;

}
