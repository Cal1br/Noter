package me.calibri.webprojectspring.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;
    @Column(nullable = false, length = 55)
    private String title;
    @Column(nullable = false, length = 2000)
    private String content;
    @Column(updatable = false, nullable = false)
    private Date createTimestamp;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @ManyToMany()
    private List<User> users;
    @OneToMany(mappedBy = "note")
    private List<Pictures> pictures;


    public Note() {

    }
    @PrePersist
    public void prePersist(){
        this.createTimestamp = new Date();
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
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

    public String getShortContent() {
        return content.substring(0, Math.min(97, content.length())) + "...";
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

