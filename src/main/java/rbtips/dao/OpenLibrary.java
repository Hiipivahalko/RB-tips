package rbtips.dao;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import rbtips.domain.Book;


public class OpenLibrary {
    
    public Book getByIsbn(String isbn) throws IOException {
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:"+ isbn +"&jscmd=data&format=json";
        String jsonText = Request.Get(url).execute().returnContent().asString();
        
        jsonText = jsonText.substring(20, (jsonText.length()-1));
        
        Gson mapper = new Gson();
        
        JsonBook jsonBook = mapper.fromJson(jsonText, JsonBook.class);
        
        Book book = new Book(jsonBook.title, jsonBook.getAuthor(), jsonBook.publish_date, jsonBook.isbn);
        book.setIsbn(isbn);
        
        return book;
    }
    
    private class JsonBook {
        private String isbn;
        private String publish_date;
        private String title;
        private String author;
        private Author[] authors;
        
        public String getTitle() {
        return title;
        }
    
        public String getPublishDate() {
            return publish_date;
        }
    
        public String getAuthor() {
            this.author = "";
            for(Author a : authors) {
                    author += a.getName() + ", ";
                }
            return author.substring(0, author.length() - 2);
        }
        
        private class Author {
            private String name;

            public String getName() {
                return name;
            }
        }
    }
}
