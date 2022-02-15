package com.example.demo3;

import java.util.List;

public class Books {

    private String title;
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    private List<Author> authorList;
    public List<Author> getAuthorList() {return authorList;}
    public void setAuthorList(List<Author> authorList) {this.authorList = authorList;}

    private String isbn;
    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    private int edition;
    public int getEdition() {return edition;}
    public void setEdition(int edition) {this.edition = edition;}


    private String copyright;
    public String getCopyright() {return copyright;}
    public void setCopyright(String copyright) {this.copyright = copyright;}

    public Books(String title, String ISBN, int edition, String copyright) {
        this.title = title;
        this.isbn = ISBN;
        this.edition = edition;
        this.copyright = copyright;
    }

    public Books() {

    }



    public Books(String title) {
        this.title = title;
    }
}
