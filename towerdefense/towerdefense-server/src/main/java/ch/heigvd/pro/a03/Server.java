package ch.heigvd.pro.a03;


public class Server {


    public Server() {
        Gson gson = new Gson();

        get("/hello/:name", (request, response) -> HelloWorld.greet(request.params(":name")), gson::toJson);
    }

    public static void main(String[] args) {
        
        new Server();



        // Run HTTP on other thread;

        MultiThreadedServer multi = new MultiThreadedServer(4567);
        multi.serveClients();

        try{
            //Thread.sleep(50);
        }catch(Exception e){}
        try{
          //  FakeClient.main(new String[]{});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}