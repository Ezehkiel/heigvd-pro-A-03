package ch.heigvd.pro.a03.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.PrintWriter;
import java.io.BufferedReader;

public class Communication {

    public static void sendProtocol(PrintWriter out, int protocolId, String data){
        out.println(String.format("%03d",protocolId)+"-"+data+"\r\n");
        out.flush();
    }

}
