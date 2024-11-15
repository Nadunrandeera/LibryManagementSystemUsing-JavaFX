package lk.codeschool.libry_management_system.controller.entity.custom;

import lk.codeschool.libry_management_system.controller.entity.SuperEntity;

public class BookAuthor implements SuperEntity {
    private int bookId;
    private int authorId;


    public BookAuthor() {
    }

    public BookAuthor(int bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
