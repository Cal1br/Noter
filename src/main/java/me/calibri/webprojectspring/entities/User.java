package me.calibri.webprojectspring.entities;

import me.calibri.webprojectspring.models.UserDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(unique = true, nullable = false, updatable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<Note> sharedNotes;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Note> getSharedNotes() {
        return sharedNotes;
    }

    public void setSharedNotes(List<Note> sharedNotes) {
        this.sharedNotes = sharedNotes;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public UserDto toDto(){
        return new UserDto(this);
    }
}
