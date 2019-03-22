package ch.heigvd.pro.a03;


public class Server {

    

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