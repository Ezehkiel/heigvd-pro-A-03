package ch.heigvd.pro.a03.httpServer.userAPI;


import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.User;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    final static Logger LOG = Logger.getLogger(UserService.class.getName());

    // returns a list of all users
    public List<User> getAllUsers() {
        LOG.log(Level.INFO, "Request: list all users");
        return SqlRequest.getAllUserDB();
    }

    // returns a single user by id
    public User getUser(String username) {
        LOG.log(Level.INFO, "Request: get one user");
        return SqlRequest.checkLogin(username);
    }

    // creates a new user
    public User createUser(String name, String password) {
        LOG.log(Level.INFO, "Request: creation of an user");
        User user = getUser(name);
        if(user !=null) {
            String hash = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();
            return SqlRequest.createUser(name, hash);
        }else{
            LOG.log(Level.INFO, "User already exist");

            return null;
        }
    }

    // updates an existing user
    public User updateUser(String username, String password) {
        LOG.log(Level.INFO, "Request: update an user");

        return SqlRequest.updateUser(username,Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
    }

    public User canLogin(String username, String password) throws UserException {
        LOG.log(Level.INFO, "Request: check that an user can login");
        User userInDataBase = SqlRequest.checkLogin(username);
        User userLoginHttp = new User(0, username, Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
        System.out.println(userInDataBase);
        System.out.println(userLoginHttp);

        if(userInDataBase != null && userInDataBase.equals(userLoginHttp)){
            return userInDataBase;
        }else{
            throw new UserException("This user can't login", userLoginHttp);
        }

    }

}