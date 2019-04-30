package ch.heigvd.pro.a03.httpServer.userAPI;


import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import spark.Request;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    final static Logger LOG = Logger.getLogger(UserService.class.getName());
    Gson gson = new Gson();


    /**
     * List all user that are registered in the DB
     * @return List<User> list with all user in it
     */
    public List<User> getAllUsers() throws UserException {
        LOG.log(Level.INFO, "Request: list all users");
        return SqlRequest.getAllUserDB();
    }

    /**
     * This function return a User that we found with his username
     * @param req Request This is the request that we received from the client
     * @return the user that we found or null if the user doesn't exist
     */
    public User getUser(Request req) {
        try {
            String username = gson.fromJson(req.body(), User.class).getUsername();
            if (username == null) {
                throw new UserException("ERROR empty username", null);
            }
            LOG.log(Level.INFO, "Request: get one user");
            User user = SqlRequest.getUserDBWithUsername(username);
            if (user == null){
                throw new UserException("ERROR this user doesn't exist", null);
            }else{
                return user;
            }
        }catch (UserException ex){
            return null;
        }
    }

    /**
     * We create a user with information that we received
     * @param req Request This is the request that we received from the client
     * @return we return a JSONObject with a JWT token and the user as data
     */
    public JSONObject createUser(Request req) {
        String password = gson.fromJson(req.body(), User.class).getPassword();
        String username = gson.fromJson(req.body(), User.class).getUsername();


        try {
            if(username == null || password == null){
                throw new UserException("ERROR password or username empty", null);
            }
            LOG.log(Level.INFO, "Request: creation of an user");
            /* We check that the user isn't already in the DB */
            User user = getUser(req);

            if (user == null) {
                String hash = Hashing.sha256()
                        .hashString(password, StandardCharsets.UTF_8)
                        .toString();
                /* We create the new user */
                User createdUser = SqlRequest.createUserDB(username, hash);

                if (createdUser != null) {
                    try {
                        JSONObject jo = new JSONObject();

                        Algorithm algorithm = Algorithm.HMAC256("secret");
                        String token = JWT.create().withClaim("username", createdUser.getUsername())
                                .withClaim("id", createdUser.getId()).sign(algorithm);

                        jo.put("error", false);
                        jo.put("data", createdUser);
                        jo.put("token", token);

                        return jo;
                    } catch (JWTCreationException exception) {
                        LOG.log(Level.SEVERE, "ERROR with token's creations");
                        throw new UserException("ERROR with token's creations", null);
                    }
                } else {
                    LOG.log(Level.SEVERE, "ERROR with creation of the new user");

                    throw new UserException("ERROR with creation of the new user", null);
                }
            } else {
                LOG.log(Level.SEVERE, "ERROR user already exist");

                throw new UserException("ERROR user already exist", null);
            }
        }catch(UserException ex){
            JSONObject jo = new JSONObject();
            jo.put("error", true);
            jo.put("message",ex.getMessage());
            jo.put("data", ex.getUser());
            LOG.log(Level.SEVERE, "ERROR user already exist");
            return jo;

        }
    }

    /**
     *
     * @param req Request This is the request that we received from the client
     * @return
     */
    public User updateUser(Request req) throws UserException {
        LOG.log(Level.INFO, "Request: update an user");

        String password = gson.fromJson(req.body(), User.class).getPassword();
        String username = gson.fromJson(req.body(), User.class).getUsername();

        return SqlRequest.updateUserDB(username,Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
    }

    /**
     * This
     * @param req Request This is the request that we received from the client
     * @return a JSONObect with the token and the user
     */
    public static JSONObject loginUser(Request req) throws UserException {
        Gson gson = new Gson();

        try {
            LOG.log(Level.INFO, "Request: check that an user can login");
            String password = gson.fromJson(req.body(), User.class).getPassword();
            String username = gson.fromJson(req.body(), User.class).getUsername();

            if(username == null || password == null){
                throw new UserException("ERROR password or username empty", null);
            }
            User userInDataBase = SqlRequest.getUserDBWithUsername(username);
            User userLoginHttp = new User(0, username, Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString());

            if (userInDataBase != null && userInDataBase.equals(userLoginHttp)) {

                try {
                    JSONObject jo = new JSONObject();

                    Algorithm algorithm = Algorithm.HMAC256("secret");
                    String token = JWT.create().withClaim("username", userInDataBase.getUsername())
                            .withClaim("id", userInDataBase.getId()).sign(algorithm);

                    jo.put("data", userInDataBase);
                    jo.put("token", token);

                    return jo;
                } catch (JWTCreationException exception) {
                    throw new UserException("ERROR can't create token", userInDataBase);
                }
            } else {
                LOG.log(Level.SEVERE, "The user can't login");
                throw new UserException("ERROR the user can't login, invalide password", userInDataBase);
            }
        }catch(UserException ex){
            JSONObject jo = new JSONObject();
            jo.put("error", true);
            jo.put("message",ex.getMessage());
            jo.put("data", ex.getUser());
            LOG.log(Level.SEVERE, "ERROR user already exist");
            return jo;
        }
    }
}