package com.example.ecombe.payload;

import com.example.ecombe.entities.Category;

public class ProductDto {
    private int productId;
    private String productName;
    private double productPrice;
    private boolean stock=true;
    private int productQuantity;
    private boolean live;
    private String imageName;
    private String productDesc;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductDto() {
        super();
    }

    public ProductDto(int productId, String productName, double productPrice, boolean stock,
                      int productQuantity, boolean live, String imageName, String productDesc) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.stock = stock;
        this.productQuantity = productQuantity;
        this.live = live;
        this.imageName = imageName;
        this.productDesc = productDesc;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
