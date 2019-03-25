package ch.heigvd.pro.a03;


public class Server {

    public static void main(String[] args) {
        
        new Server();



        // Run HTTP on other thread;

        MultiThreadedServer multi = new MultiThreadedServer(4567);
        multi.serveClients();
    }
}