package ch.heigvd.pro.a03.server;

import ch.heigvd.pro.a03.users.Score;
import ch.heigvd.pro.a03.users.User;

import org.json.JSONArray;
import ch.heigvd.pro.a03.utils.Config;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class HttpServerUtils {

    public static String getErrorMessage() {
        return errorMessage;
    }

    public static void resetError() {
        errorMessage = null;
    }

    private static String errorMessage;

    /**
     * This method take the password and the username of the user and will
     * create a JSON string to send it to the server on the endpoint
     * corresponding.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return a new user with informations received from the server
     * or null if something goes bad in the authentication
     */
    public static User login(String username, String password) {

        try {
            String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            HttpURLConnection connection = postConnection("users/login");

            if (connection != null && writeData(connection, data)) {
                return playerFromResponse(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method ask the server for all scores
     *
     * @return all scores in the array
     */
    public static ArrayList<Score> allScore() {

        try {

            HttpURLConnection connection = getConnection("users/scores");

            if (connection != null) {
                return scoresFromResponse(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method take the username and the password and send them to the
     * server to register the user
     *
     * @param username username of the user
     * @param password password of the user
     * @return a new user with informations corressponding or null
     * if somethings goes bad
     */
    public static User register(String username, String password) {

        try {
            String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            HttpURLConnection connection = postConnection("users/register");

            if (connection != null && writeData(connection, data)) {
                return playerFromResponse(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<Score> scoresFromResponse(HttpURLConnection connection) {
        ArrayList<Score> allScores = new ArrayList<>();
        JSONArray response = new JSONArray(readData(connection));

        for (Object obj : response) {
            JSONObject score = (JSONObject) obj;
            allScores.add(new Score(score.getInt("userId"), score.getString("username"),
                    score.getInt("nbPartieJoue"), score.getInt("nbPartieGagne")));
        }
        return allScores;
    }

    /**
     * This method format the response of the server to create all scores
     * received in the JSON
     *
     * @param connection the connection to the server, used to read data
     * @return a new user if informations where right, null otherwise
     * @throws IOException
     */
    private static User playerFromResponse(HttpURLConnection connection) throws IOException {
        User player = null;
        /*
            If the response code is not a 200 code it means that something
            failed on the server side, we return a player null
         */
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return player;
        }

        /* We create a JSONObject to parse field that we received from the
        server
         */
        JSONObject response = new JSONObject(readData(connection));
        boolean haveError = response.getBoolean("error");
        if (haveError) {
            errorMessage = response.getString("message");
        } else {
            JSONObject data = (JSONObject) response.get("data");

            player = new User(data.getInt("id"), data.getString("username"), null,
                    data.getInt("nbPartieJoue"), data.getInt("nbPartieGagne"),
                    null);
            player.setToken(response.getString("token"));
        }

        return player;
    }

    /**
     * This method create the POST connection with the server
     *
     * @param path it's the endpoint that we want to reach
     * @return the connection created on the good endpoint and ready
     * to send/receive data
     */
    private static HttpURLConnection postConnection(String path) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    Config.getHttpUrl() + "/" + path
            ).openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            return connection;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method create the GET connection with the server
     *
     * @param path it's the endpoint that we want to reach
     * @return the connection created on the good endpoint and ready
     * to send/receive data
     */
    private static HttpURLConnection getConnection(String path) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    Config.getHttpUrl() + "/" + path
            ).openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            return connection;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * This method is used to write data on a connection
     *
     * @param connection the connection where data have to be written
     * @param data       tha data to write
     * @return trus if it goes well, false otherwise
     */
    private static boolean writeData(HttpURLConnection connection, String data) {

        try {
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.flush();
            os.close();

            return true;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * This method is used to read data from a connection
     *
     * @param connection the connection where data have to be reading
     * @return trus if it goes well, false otherwise
     */
    private static String readData(HttpURLConnection connection) {

        try {

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
            );

            StringBuilder responseBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            return responseBuilder.toString();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
