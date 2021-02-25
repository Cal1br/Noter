package me.calibri.webprojectspring.models;

public class NoteEditModel {
    private final String title;
    private final String content;
    private final long id;


    public NoteEditModel(String title, String content, long id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

}
