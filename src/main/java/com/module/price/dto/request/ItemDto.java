package com.module.price.dto.request;

import com.module.price.enums.ItemCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {

    private Long id;
    private String itemName;
    private ItemCategory category;
    private double totalAmount;

}
