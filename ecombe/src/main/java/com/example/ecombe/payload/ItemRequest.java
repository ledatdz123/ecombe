package com.example.ecombe.payload;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {
    private int productId;
    private int quantity;
}
