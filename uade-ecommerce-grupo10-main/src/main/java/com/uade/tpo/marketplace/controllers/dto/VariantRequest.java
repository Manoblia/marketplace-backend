package com.uade.tpo.marketplace.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantRequest {
    private Long productId;
    private int size;
    private int stock;
}
