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
        
        Book book = mapper.fromJson(jsonText, Book.class);
        
        book.setIsbn(isbn);
        book.setAuthor("");
        
        return book;
    }
}
