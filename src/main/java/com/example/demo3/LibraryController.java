package com.example.demo3;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;


@Path("/library")
public class LibraryController {

    DBQueries DBQueries = new DBQueries();
    @GET
    @Produces("text/plain")
    public String HomePageText() {

        return "welcome to my Library app home page. The app provides the following different api please choose your preference:" +
                "\nplease notice if the Api ends with /ID or /ISBN that you need to replace this value with the value you are searching for\n"+
                "\n1-  View Books                  : app/library/books" +
                "\n2-  Find a  book by ISBN        : app/library/book/ISBN " +
                "\n3-  Add  a book                 : app/library/add-book " +
                "\n4-  Modify a book               : app/library/update-book " +
                "\n5- Delete a book                : app/library/delete-book/ISBN " +
                "\n6-  View Authors                : app/library/authors" +
                "\n7-  Find an author by ID        : app/library/author/ID " +
                "\n8-  Add author                  : app/library/add-author " +
                "\n9-  Modify an author            : app/library/update-author " +
                "\n10- Delete an author            : app/library/delete-author/ID "+
                "\n11- Associate author with book  : app/library/associate-author " ;

    }


//Book Requests

    //    get all books request
    //    passed testing
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Books> getBooks() throws SQLException {
        return com.example.demo3.DBQueries.getBooks();
    }

    //    get a book by isbn
    //    passed testing
    @GET
    @Path("/book/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Books getBookById(@PathParam("isbn") String isbn) throws SQLException {
        return DBQueries.getABook(isbn);
    }

    //    delete a book using isbn
    //    passed testing
    @DELETE
    @Path("/delete-book/{isbn}")
    @Produces("text/plain")
    public String delBook(@PathParam("isbn") String isbn) throws SQLException {
        return DBQueries.deleteABookFromTitlesTable(isbn);
    }

    //    add a new book
    //    passed testing
    @POST
    @Path("/add-book")
    @Produces(MediaType.APPLICATION_JSON)
    public Books addBook(Books book) throws SQLException {
        return DBQueries.AddABook(book);
    }

    //    modify existing book
    //    passed testing
    @PUT
    @Path("/update-book")
    @Produces(MediaType.APPLICATION_JSON)
    public Books modBook(Books book) throws SQLException {
        return DBQueries.updateABook(book);
    }


//Author Requests

    // get all authors
    //    passed testing
    @GET
    @Path("/authors")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() throws SQLException {
        return com.example.demo3.DBQueries.getAuthors();
    }

    //    get author by id
    //    passed testing
    @GET
    @Path("/author/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author getAuthorById(@PathParam("id") int id) throws SQLException {
        return DBQueries.getAuthor(id);
    }

    //add a new author
    //    passed testing
    @POST
    @Path("/add-author")
    @Produces(MediaType.APPLICATION_JSON)
    public Author addAuthor(Author author) throws SQLException {
        return DBQueries.addAuthor(author);
    }

    //  delete an existing author
    //    passed testing
    @DELETE
    @Path("/delete-author/{id}")
    @Produces("text/plain")
    public String delAuthor(@PathParam("id") int id) throws SQLException {
        return DBQueries.deleteAnAuthorFromAuthorsTable(id);
    }

    //    modify an existing author
    //    passed testing
    @PUT
    @Path("/update-author")
    @Produces(MediaType.APPLICATION_JSON)
    public Author modAuthor(Author author) throws SQLException {
        return DBQueries.updateAnAuthor(author);
    }



    //    Associate authors with Books
    //    passed testing
    @POST
    @Path("/associate-author-book")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorISBN associateAuthor(AuthorISBN authorISBN) throws SQLException {
        return DBQueries.addAuthorISBN(authorISBN);
    }





}