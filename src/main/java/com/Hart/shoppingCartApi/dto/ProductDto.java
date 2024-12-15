package com.Hart.shoppingCartApi.dto;

import com.Hart.shoppingCartApi.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category; // Relationship
    private List<ImageDto> images; // Relationship
}
