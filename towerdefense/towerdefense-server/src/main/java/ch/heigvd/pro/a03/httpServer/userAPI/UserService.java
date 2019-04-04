package ch.heigvd.pro.a03.httpServer.userAPI;


import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.User;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {

    // returns a list of all users
    public List<User> getAllUsers() {
        return SqlRequest.getAllUserDB();
    }

    // returns a single user by id
    public User getUser(String id) {
        return SqlRequest.getUser(Long.parseLong(id));
    }

    // creates a new user
    public User createUser(String name, String password) {
        String hash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
        System.out.println(hash);
        return SqlRequest.createUser(name, hash);
    }

    // updates an existing user
    public User updateUser(String username, String password) {
        return SqlRequest.updateUser(username,Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
    }

    public User canLogin(String username, String password) throws UserException {
        User userInDataBase = SqlRequest.checkLogin(username);
        User userLoginHttp = new User(0, username, Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
        System.out.println(userInDataBase);
        System.out.println(userLoginHttp);
        if(userInDataBase.equals(userLoginHttp)){
            return userInDataBase;
        }else{
            throw new UserException("This user can't login", userLoginHttp);
        }

    }

}