package com.example.demo3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBQueries {
    public static List<Books> getBooks() throws SQLException {
        String query1 = "select * from titles";
        List<Books> books = new ArrayList<>();
        try (Connection conn = DBConnection.initDB()) {
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(query1);

            while (set.next()) {
                Books book = new Books();
                book.setIsbn(set.getString("isbn"));
                book.setTitle(set.getString("title"));
                book.setEdition(set.getInt("editionNumber"));
                book.setCopyright(set.getString("copyright"));

                String query2 = "select a.authorID, a.firstName, a.lastName from authors a " +
                        "join authorisbn ai on a.authorID = ai.authorID join titles t on ai.isbn = t.isbn " +
                        "where t.isbn = '" + book.getIsbn() + "' ";
                GetBooksAuthors(conn, book, query2);
                books.add(book);
            }
        }
        return books;
    }

    public Books getABook(String ISBN) throws SQLException {
        String query1 = "select * from titles where isbn = '"+ISBN+"'";
        try (Connection conn = DBConnection.initDB()) {
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(query1);

            if (set.next()) {
                Books book = new Books();
                book.setIsbn(set.getString("isbn"));
                book.setTitle(set.getString("title"));
                book.setEdition(set.getInt("editionNumber"));
                book.setCopyright(set.getString("copyright"));

                String query2 = "select a.authorID, a.firstName, a.lastName from authors a join authorisbn ai on a.authorID = ai.authorID join titles t on ai.isbn = t.isbn where t.isbn = '" + ISBN + "' ";
                GetBooksAuthors(conn, book, query2);

                return book;
            } else return null;
        }
    }

    public boolean BookDuplication(String ISBN) throws SQLException {
        String query1 = "select * from titles where isbn = '"+ISBN+"'";
        PreparedStatement pstmt;
        Connection conn = DBConnection.initDB();
        pstmt = conn.prepareStatement(query1);
        ResultSet set = pstmt.executeQuery();
        return set.next();

    }

    private static void GetBooksAuthors(Connection conn, Books book, String query2) throws SQLException {
        PreparedStatement preppedStmt;
        preppedStmt = conn.prepareStatement(query2);
        ResultSet set2 = preppedStmt.executeQuery();
        List<Author> authList=new ArrayList<>();
        while (set2.next()) {
            Author author = new Author();
            author.setAuthorID(set2.getInt("authorID"));
            author.setFirstName(set2.getString("firstName"));
            author.setLastName(set2.getString("lastName"));
            authList.add(author);
        }
        book.setAuthorList(authList);
    }

    public Books AddABook(Books book) throws SQLException {
        if(!BookDuplication(book.getIsbn())) {
            String query = "insert into titles values ('"+book.getIsbn()+"' , '"+book.getTitle()+"'" +
                    " , '"+book.getEdition()+"' , '"+book.getCopyright()+"')";
            addAndDeleteQueries(query);
            return book;
        } else {
            return null;
        }
    }

    public Books updateABook(Books book) throws SQLException {
        if (!BookDuplication(book.getIsbn())) {
            return null;
        } else {
            String query = "update titles set title = '"+book.getTitle()+"', editionNumber = '"+book.getEdition()+"'" +
                    ", copyright = '"+book.getCopyright()+"' WHERE isbn = '"+book.getIsbn()+"'";
            addAndDeleteQueries(query);
            return book;
        }
    }

    public String deleteABookFromTitlesTable(String ISBN) throws SQLException {
        if(BookDuplication(ISBN)) {
            deleteAnAuthorIsbnRecordByISBN(ISBN);
            String query = "delete from titles where isbn = '"+ISBN+"'";
            addAndDeleteQueries(query);
            return "Book ISBN "+ISBN + " has been deleted.";
        } else return "Book ISBN " + ISBN + " does not exist.";
    }




    public static List<Author> getAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        try (Connection conn = DBConnection.initDB()) {
            Statement stmt = conn.createStatement();
            String query1 = "select * from authors";
            ResultSet set = stmt.executeQuery(query1);

            while (set.next()) {
                Author author = new Author();
                author.setAuthorID(set.getInt("authorID"));
                author.setFirstName(set.getString("firstName"));
                author.setLastName(set.getString("lastName"));
                String query2 = "select t.isbn, t.title, t.editionNumber, t.copyright from titles t " +
                        "join authorisbn ai on t.isbn = ai.isbn join authors a on ai.authorID = a.authorID where a.authorID = '"+author.getAuthorID()+"' ";
                GetAuthorBooks(conn, author, query2);

                authors.add(author);
            }
        }
        return authors;
    }

    public Author getAuthor(int id) throws SQLException {
        String query1 = "Select * from authors where authorID = '"+id+"'";
        Connection conn = DBConnection.initDB();
        Statement stmt = conn.createStatement();
        ResultSet set = stmt.executeQuery(query1);

        if (set.next()) {
            Author author = new Author();
            author.setAuthorID(set.getInt("authorID"));
            author.setFirstName(set.getString("firstName"));
            author.setLastName(set.getString("lastName"));
            String query2 = "select t.isbn, t.title, t.editionNumber, t.copyright from titles t" +
                    " join authorisbn ai on t.isbn = ai.isbn join authors a on ai.authorID = a.authorID " +
                    "where a.authorID = '"+id+"' ";
            GetAuthorBooks(conn,author,query2);

            return author;
        } else {
            return null;
        }
    }

    private static void GetAuthorBooks(Connection conn, Author author, String query2) throws SQLException {
        PreparedStatement preppedStmt;
        preppedStmt = conn.prepareStatement(query2);
        ResultSet set2 = preppedStmt.executeQuery();
        List<Books> books=new ArrayList<>();
        while (set2.next()) {
            Books book = new Books();
            book.setIsbn(set2.getString("isbn"));
            book.setTitle(set2.getString("title"));
            book.setEdition(set2.getInt("editionNumber"));
            book.setCopyright(set2.getString("copyright"));
            books.add(book);
        }
        author.setBooksList(books);
    }

    public boolean AuthorDuplication(int id) throws SQLException {
        String query1 = "select * from authors where authorID = '"+id+"'";
        PreparedStatement pstmt;
        Connection conn = DBConnection.initDB();
        pstmt = conn.prepareStatement(query1);
        ResultSet set = pstmt.executeQuery();
        return set.next();
    }

    public Author addAuthor(Author author) throws SQLException {
        if (AuthorDuplication(author.getAuthorID())) {
            return null;
        } else {
            String query = "insert into authors Values ('"+author.getAuthorID()+"','"+author.getFirstName()+"','"+author.getLastName()+"')";
            addAndDeleteQueries(query);
            return author;
        }
    }

    public AuthorISBN addAuthorISBN(AuthorISBN authorISBN) throws SQLException {
        String query = "insert into authorISBN values ('"+authorISBN.getAuthorID()+"', '"+authorISBN.getIsbn()+"')";

        if (!AuthorDuplication(authorISBN.getAuthorID()) || !BookDuplication(authorISBN.getIsbn())) {
               return null;
        } else {
            addAndDeleteQueries(query);
            return authorISBN;
        }
    }

    public Author updateAnAuthor(Author author) throws SQLException {
        if (!AuthorDuplication(author.getAuthorID())) return null;
        else {
            String query = "update authors set firstName = '"+author.getFirstName()+"', lastName = '"+author.getLastName()+"' " +
                    "where authorID = '"+author.getAuthorID()+"'";
            addAndDeleteQueries(query);
            return author;
        }
    }

    public void deleteAnAuthorIsbnRecordByISBN(String ISBN) {
        String query = "delete from authorisbn where isbn = '"+ISBN+"'";
        addAndDeleteQueries(query);
    }

    public void deleteAnAuthorIsbnRecordByID(int id) {
        String query = "DELETE from authorisbn " +
                "where authorID = '"+id+"'";
        addAndDeleteQueries(query);
    }

    public String deleteAnAuthorFromAuthorsTable(int id) throws SQLException {
        if(AuthorDuplication(id)){
            deleteAnAuthorIsbnRecordByID(id);
            String query = "delete from authors where authorID = '"+id+"'";
            addAndDeleteQueries(query);
            return "AuthorID " +id+ " has been deleted.";
        } else return "AuthorID " + id + " does not exist.";
    }

    private void addAndDeleteQueries(String query){
        try (Connection conn = DBConnection.initDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
