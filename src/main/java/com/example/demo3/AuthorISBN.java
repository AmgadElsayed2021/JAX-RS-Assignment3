package com.example.demo3;

public class AuthorISBN {
    private int authorID;
    private String isbn;

    public AuthorISBN(){
        super();
    };

    public AuthorISBN(int authorID, String ISBN){
        this.authorID = authorID;
        this.isbn = ISBN;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
