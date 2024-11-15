package lk.codeschool.libry_management_system.controller.entity.custom;

import lk.codeschool.libry_management_system.controller.entity.SuperEntity;

public class SubCategory implements SuperEntity {
    private int bookId;
    private int categoryId;
    public SubCategory() {
    }
    public SubCategory(int bookId, int categoryId) {
        this.bookId = bookId;
        this.categoryId = categoryId;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
