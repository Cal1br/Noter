package me.calibri.webprojectspring.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;


    @ManyToMany(mappedBy = "sharedNotes")
    private List<User> users;

    @OneToMany(mappedBy = "note")
    private List<Pictures> pictures;

    public Note() {

    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
            this.users = users;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long note_id) {
        this.noteId = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Pictures> getPictures() {
        return pictures;
    }

    public void setPictures(List<Pictures> pictures) {
        if (pictures != null) {
            this.pictures = pictures;
        } else {
            this.pictures = new ArrayList<Pictures>();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

