package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.Player;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpServerUtils {

    private static final String url = "https://ezehkiel.ch:3945";

    public static Player login(String username, String password) {

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

    public static Player register(String username, String password) {

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

    private static Player playerFromResponse(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return null;
        }

        return new Gson().fromJson(readData(connection), Player.class);
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
