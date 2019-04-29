package ch.heigvd.pro.a03.httpServer.userAPI;


import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    final static Logger LOG = Logger.getLogger(UserService.class.getName());


    Gson gson = new Gson();


    // returns a list of all users
    public List<User> getAllUsers(Request req, Response res) {
        LOG.log(Level.INFO, "Request: list all users");
        return SqlRequest.getAllUserDB();
    }

    // returns a single user by id
    public User getUser(Request req, Response res) {
        String username = gson.fromJson(res.body(), User.class).getUsername();
        LOG.log(Level.INFO, "Request: get one user");
        return SqlRequest.checkLogin(username);
    }

    // creates a new user
    public User createUser(Request req, Response res) {
        String password = gson.fromJson(res.body(), User.class).getPassword();
        String username = gson.fromJson(res.body(), User.class).getUsername();

        LOG.log(Level.INFO, "Request: creation of an user");
        User user = getUser(req, res);
        if(user == null){
            String hash = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();
            return SqlRequest.createUser(username, hash);
        }else{
            LOG.log(Level.INFO, "User already exist");

            return null;
        }
    }

    // updates an existing user
    public User updateUser(Request req, Response res) {
        LOG.log(Level.INFO, "Request: update an user");

        String password = gson.fromJson(res.body(), User.class).getPassword();
        String username = gson.fromJson(res.body(), User.class).getUsername();

        return SqlRequest.updateUser(username,Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
    }

    public User canLogin(Request req, Response res) throws UserException {
        String password = gson.fromJson(res.body(), User.class).getPassword();
        String username = gson.fromJson(res.body(), User.class).getUsername();
        LOG.log(Level.INFO, "Request: check that an user can login");
        User userInDataBase = SqlRequest.checkLogin(username);
        User userLoginHttp = new User(0, username, Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
        System.out.println(userInDataBase);
        System.out.println(userLoginHttp);

        if(userInDataBase != null && userInDataBase.equals(userLoginHttp)){
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                String token = JWT.create().withClaim("username", userInDataBase.getUsername())
                        .withClaim("id", userInDataBase.getId()).sign(algorithm);

                res.body("{\"token\":" + token + ", \"data\":"+ userInDataBase + "}");

            } catch (JWTCreationException exception){
                //Invalid Signing configuration / Couldn't convert Claims.
            }
            return null;
        }else{
            throw new UserException("This user can't login", userLoginHttp);
        }

    }

}