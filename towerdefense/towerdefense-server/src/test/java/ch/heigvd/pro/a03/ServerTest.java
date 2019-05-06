package ch.heigvd.pro.a03;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class ServerTest {

//    @BeforeAll
//    public static void waitForServer() {
//
//        new Server();
//
//        // Wait for the server to be ready
//        boolean isRunning = false;
//        while (!isRunning) {
//
//            try {
//                HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:4567").openConnection();
//                connection.setRequestMethod("GET");
//                connection.getResponseCode();
//
//                isRunning = true;
//
//            } catch (Exception e) {
//                isRunning = false;
//            }
//        }
//
//        System.out.println("Server is running!");
//    }
//
//    @AfterAll
//    public static void stopServer() {
//
//        System.out.println("Stopping server!");
//        Spark.stop();
//    }
//
//    @Test
//    public void itShouldGreetSomeoneInHelloPage() throws IOException {
//
//        String name = "Jack";
//
//        // Open connection
//        URL url = new URL("http://127.0.0.1:4567/hello/" + name);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//        // Check response status code
//        assertEquals(200, connection.getResponseCode());
//
//        // Read response body
//        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//        StringBuilder sb = new StringBuilder();
//        String output;
//        while ((output = br.readLine()) != null) {
//            sb.append(output);
//        }
//
//        br.close();
//
//        assertEquals("Hello, " + name + "!", sb.toString());
//    }
}