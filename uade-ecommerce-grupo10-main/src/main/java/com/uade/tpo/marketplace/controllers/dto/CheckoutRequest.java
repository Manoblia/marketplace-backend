package com.uade.tpo.marketplace.controllers.dto;

import lombok.Data;

@Data
public class CheckoutRequest {
    private String fullName;
    private String shippingAddress;
    private String paymentMethod;
}