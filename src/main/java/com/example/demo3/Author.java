package com.example.demo3;


import java.util.List;

public class Author {
    private  String firstName;
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}


    private  String lastName;
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    private List<Books> booksList;
    public List<Books> getBooksList() {return booksList;}
    public void setBooksList(List<Books> booksList) {this.booksList = booksList;}


    private  int authorID;
    public int getAuthorID() {return authorID;}
    public void setAuthorID(int authorID) {this.authorID = authorID;}

    public Author() {
    }

}
