package ch.heigvd.pro.a03;

import ch.heigvd.pro.a03.socketServer.MultiThreadedServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    Socket socket;
    InputStreamReader in = null;
    OutputStreamWriter out = null;

    Boolean isReady;

    public Client(Socket socket) {

        this.socket = socket;
        isReady = false;
        try {
            this.in =  new InputStreamReader(socket.getInputStream());


            this.out =  new OutputStreamWriter(socket.getOutputStream());
            out.flush();

        } catch (IOException ex) {
            Logger.getLogger(MultiThreadedServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InputStreamReader getIn() {
        return in;
    }

    public void setIn(InputStreamReader in) {
        this.in = in;
    }

    public OutputStreamWriter getOut() {
        return out;
    }

    public boolean isReady(){
        return isReady;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }
}
