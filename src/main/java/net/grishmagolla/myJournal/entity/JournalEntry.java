package net.grishmagolla.myJournal.entity;

public class JournalEntry {
    private long id;
    private String title;
    private String content;

    public long getId(long id){
        return id;
    }
    public void setId(){
        this.id = id;
     }

     public String getTitle(String title){
        return title;
     }
     public void setTitle(){
        this.title = title;
     }
     public String getContent(){
        return content;
     }
     public void setContent(String content){
        this.content = content;
     }
}

