package com.largeorange.pojo;

import lombok.Data;

@Data
/**
 * 产品
 */
public class Products {
  private Integer id;
  private String name;
  private String description;
  private Double price;
  private Integer stock;
}
