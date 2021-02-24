package me.calibri.webprojectspring.models;

import me.calibri.webprojectspring.entities.User;

public class UserDto {
    private long id;
    public UserDto(User user){
        this.id = user.getUserId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
