package com.example.ecombe.payload;

public class CategoryDto {
    private int categoryId;
    private String title;

    public CategoryDto() {
        super();
    }

    public CategoryDto(int categoryId, String title) {
        super();
        this.categoryId = categoryId;
        this.title = title;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
