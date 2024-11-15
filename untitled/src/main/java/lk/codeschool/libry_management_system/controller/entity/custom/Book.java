package lk.codeschool.libry_management_system.controller.entity.custom;

import lk.codeschool.libry_management_system.controller.entity.SuperEntity;

public class Book implements SuperEntity {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int publisherId;
    private int mainCategory;

    public Book() {
    }

    public Book(int id, String name, String isbn, double price, int publisher, int mainCategory) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.price = price;
        this.publisherId = publisher;
        this.mainCategory = mainCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public int getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(int mainCategory) {
        this.mainCategory = mainCategory;
    }
}
