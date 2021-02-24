package me.calibri.webprojectspring.services;

import me.calibri.webprojectspring.entities.User;
import me.calibri.webprojectspring.models.UserDto;
import me.calibri.webprojectspring.repositories.UserRepository;
import me.calibri.webprojectspring.utils.LoginUtility;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public User login(String usernameOrEmail, String password) {
        Optional<User> optionalUser = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(optionalUser.isPresent()){ //ako nqma optional shte se poluchi NullPointerException nay veroqtno Jesus 10:10
            if(BCrypt.checkpw(password,optionalUser.get().getPassword())){
                return optionalUser.get();
            }
            else {
                throw new RuntimeException("Password is incorrect");
            }
        }
        else {
            throw new RuntimeException("No such user");
        }
    }


    public User register(String username, String password, String email) {
        Optional<User> optionalUser = repository.findByUsernameOrEmail(username, email);
        if(optionalUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt(username.length() + 1));
        User user = new User();
        user.setPassword(hashpw);
        user.setUsername(username);
        user.setEmail(email);
        user.setSharedNotes(new ArrayList<>());
        return repository.save(user);
    }
    public User getUserById(long id){
        Optional<User> optionalUser = repository.findByUserId(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        else{
            throw new RuntimeException("No such user with this id");
        }
    }

}
