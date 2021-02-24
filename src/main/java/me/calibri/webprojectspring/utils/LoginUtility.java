package me.calibri.webprojectspring.utils;

import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.models.UserDto;

import javax.servlet.http.HttpSession;

public final class LoginUtility {

    private LoginUtility(){

    }

    public static boolean isLoggedIn(HttpSession session){
        return session.getAttribute("userDto") instanceof UserDto;
    }
    public static UserDto getUserDto(HttpSession session){
        Object dto = session.getAttribute("userDto");
        if(dto instanceof UserDto){
            return (UserDto) dto;
        }
        else{
            return null;
        }
    }

    public static void logIn(HttpSession session, UserDto user){
        session.setAttribute("userDto", user);
    }
    public static void logOut(HttpSession session){
        session.invalidate();
    }
}
