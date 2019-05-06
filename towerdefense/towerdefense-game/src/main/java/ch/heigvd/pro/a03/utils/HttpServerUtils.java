package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.users.User;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpServerUtils {

    public String getErrorMessage() {
        return errorMessage;
    }
    public void resetError(){
        this.errorMessage = null;
    }

    private static String errorMessage;

    private static final String url = "http://127.0.0.1:3945";

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

    public static User register(String username, String password) {

        try {
            String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

            HttpURLConnection connection = postConnection("users/register");

            if (connection != null && writeData(connection, data)) {
                return playerFromResponse(connection);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static User playerFromResponse(HttpURLConnection connection) throws IOException {
        User player = null;
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return player;
        }

        JSONObject response = new JSONObject(readData(connection));
        boolean haveError = response.getBoolean("error");
        if(haveError){
            errorMessage = response.getString("message");
        }else{
            JSONObject data = (JSONObject) response.get("data");
            player = new User(data.getInt("id"), data.getString("username"), null);
            player.setToken(response.getString("token"));
        }

        return player;
    }

    private static HttpURLConnection postConnection(String path) {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url + "/" + path).openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            return connection;

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

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
