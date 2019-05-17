package ch.heigvd.pro.a03.httpServer.userAPI;


import ch.heigvd.pro.a03.httpServer.SqlRequest;
import ch.heigvd.pro.a03.users.Score;
import ch.heigvd.pro.a03.users.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
    private Gson gson = new Gson();


    /**
     * List all user that are registered in the DB
     *
     * @return list with all user in it
     */
    public List<User> getAllUsers() {
        LOG.log(Level.INFO, "Request: list all users");
        return SqlRequest.getAllUserDB();
    }

    /**
     * List all score that are registered in the DB
     *
     * @return list with all score in it
     */
    public List<Score> getAllScores() {
        LOG.log(Level.INFO, "Request: list all scores");
        return SqlRequest.getAllScoreDB();
    }


    /**
     * This function return a User that we found with his username
     *
     * @param req this is the request that we received from the client
     * @return the user that we found or null if the user doesn't exist
     */

    public User getUser(Request req) {

        LOG.log(Level.INFO, "Request: get one user");
        String username = gson.fromJson(req.body(), User.class).getUsername();

        User user = SqlRequest.getUserDBWithUsername(username);
        return user;
    }

    /**
     * We create a user with information that we received
     *
     * @param req this is the request that we received from the client
     * @return return a JSONObject with a JWT token and the user as data
     * field
     */
    public JSONObject createUser(Request req) {

        LOG.log(Level.INFO, "Request: creation of an user");
        String username = gson.fromJson(req.body(), User.class).getUsername();
        String password = gson.fromJson(req.body(), User.class).getPassword();

        try {

            if (username == null || password == null) {
                throw new UserException("Password or username not present in" +
                        " the request", null);
            }

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

                        return createResponse(createdUser);
                    } catch (JWTCreationException exception) {

                        LOG.log(Level.SEVERE, "ERROR with token's creations");
                        throw new UserException("Error with token's creations", null);

                    } // Fin try/catch
                } else {
                    LOG.log(Level.SEVERE, "ERROR with creation of the new user");

                    throw new UserException("Problem with the creation of the new user", null);
                } // Fin if createdUser
            } else {
                LOG.log(Level.SEVERE, "ERROR user already exist");

                throw new UserException("User already exist", null);
            }
        } catch (UserException ex) {
            JSONObject jo = new JSONObject();
            jo.put("error", true);
            jo.put("message", ex.getMessage());
            jo.put("data", ex.getUser());
            return jo;

        }
    }

    /**
     * @param req Request This is the request that we received from the client
     * @return
     */
    public User updateUser(Request req) throws UserException {
        LOG.log(Level.INFO, "Request: update an user");

        String password = this.gson.fromJson(req.body(), User.class).
                getPassword();
        String username = this.gson.fromJson(req.body(), User.class).
                getUsername();

        return SqlRequest.updateUserDB(username, Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString());
    }

    /**
     * This method is used when a client ask for a login authentication
     *
     * @param req this is the request that we received from the client
     * @return a JSONObect with the token and the user
     */
    public JSONObject loginUser(Request req) throws UserException {

        LOG.log(Level.INFO, "Request: check that an user can login");
        /* Get the username and password from the JSON */
        String password = this.gson.fromJson(req.body(), User.class).
                getPassword();
        String username = this.gson.fromJson(req.body(), User.class).
                getUsername();

        try {

            if (username == null || password == null) {
                throw new UserException("Password or username not set", null);
            }

            User userInDataBase = SqlRequest.getUserDBWithUsername(username);
            User userLoginHttp = new User(0, username, Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString());

            /* We check that the user in the database is the same that the one
             * we received
             */
            if (userInDataBase != null && userInDataBase.equals(userLoginHttp)) {

                try {

                    SqlRequest.setLastLoginDB(userInDataBase.getId());
                    return createResponse(userInDataBase);

                } catch (JWTCreationException exception) {
                    throw new UserException("Error with the creations of the" +
                            " token", userInDataBase);
                }
            } else {
                LOG.log(Level.SEVERE, "The user can't login");
                throw new UserException("The user can't login, invalid" +
                        " password", userInDataBase);
            }
        } catch (UserException ex) {
            JSONObject jo = new JSONObject();
            jo.put("error", true);
            jo.put("message", ex.getMessage());
            jo.put("data", ex.getUser());
            return jo;
        }
    }

    /**
     * This method is user to create a response with a token and the user's
     * information in it
     *
     * @param user to put in the data field of the JSON
     * @return JSONObject with all the field needed (token, data, error)
     */
    private JSONObject createResponse(User user) {
        JSONObject jo = new JSONObject();

        Algorithm algorithm = Algorithm.HMAC256("pro2019heig");
        String token = JWT.create().withClaim("username", user.getUsername())
                .withClaim("id", user.getId()).sign(algorithm);

        jo.put("error", false);
        jo.put("data", user);
        jo.put("token", token);
        return jo;
    }

    /**
     * This method is used by the server. When a game is finised the server
     * send both IDs of players and we update the database with "gamePlayed"
     * and "gameWon" field
     * @param req this is the request that we received from the server
     * @return JSONObject with all the field needed (token, error)
     */
    public JSONObject setScore(Request req) {

        org.json.JSONObject jo = new org.json.JSONObject(req.body());
        String serverToken = jo.getString("token");
        JSONObject tokenResponse = new JSONObject();
        try {
            Algorithm algorithm = Algorithm.HMAC256("P2z6cA9CGt5Oq");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            verifier.verify(serverToken);

            long idWinner = jo.getLong("idWinner");
            long idLoser = jo.getLong("idLoser");

            /* If one of the update doesn't work we inform the server */
            if (SqlRequest.incrementPlayedGameUserDB(idWinner) &&
                    SqlRequest.incrementPlayedGameUserDB(idLoser) &&
                    SqlRequest.incrementWinGameUserDB(idWinner)) {
                tokenResponse.put("error", false);
                tokenResponse.put("message", "Score updated");


            }else{
                tokenResponse.put("error", true);
                tokenResponse.put("message", "Problem with incrementation of " +
                        "scores in the database");
            }
            return tokenResponse;

        } catch (JWTVerificationException exception) {

            tokenResponse.put("error", true);
            tokenResponse.put("message", "Invalide token");
            return tokenResponse;
        }

    }

    /**
     * This method is used to get the score of one player in particular.
     * We check that the is correct by verifying the token signature.
     * @param req this is the request that we received from the client
     * @return JSONObject with all the field needed (token, error)
     */
    public JSONObject getUserScore(Request req) {

        org.json.JSONObject jo = new org.json.JSONObject(req.body());
        org.json.JSONObject data = (org.json.JSONObject) jo.get("data");
        String userToken = jo.getString("token");
        JSONObject tokenResponse = new JSONObject();
        try {
            Algorithm algorithm = Algorithm.HMAC256("pro2019heig");
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", (String) data.get("username"))
                    .withClaim("id", (int) data.get("id"))
                    .build(); //Reusable verifier instance
            verifier.verify(userToken);

            long id = Long.valueOf(req.params(":id"));
            Score score = SqlRequest.getUserScoreDB(id);


            tokenResponse.put("error", false);
            tokenResponse.put("data", score);
            tokenResponse.put("token", userToken);
            return tokenResponse;

        } catch (JWTVerificationException exception) {

            tokenResponse.put("error", true);
            tokenResponse.put("message", "Invalide token");
            return tokenResponse;
        }

    }
}