package com.largeorange.pojo;

import lombok.Data;

@Data
public class OrderMessage {

  private Integer id;
  private Integer productId;
  private Integer productNumber;
  private Integer productStatus;
  private Double productBalance;
  private Integer userId;
  private String productName;

}
