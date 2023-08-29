package semantic.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private String author;
    private String timeCreated;
    private String text;

    public Comment(String author, String timestamp, String text) {
        this.author = author;
        this.timeCreated = timestamp;
        this.text = text;
    }
    
    

    public String getAuthor() {
        return author;
    }

    public String getFormattedTimestamp() {
        
        return timeCreated;
    }

    public String getText() {
        return text;
    }
    
}