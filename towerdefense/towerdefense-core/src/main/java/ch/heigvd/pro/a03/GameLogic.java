package ch.heigvd.pro.a03;

public class GameLogic {

    private Map map;
    private Map map2;


    public GameLogic(Map map, Map map2){
        this.map=map;
        this.map2=map2;
    }

    public void playGame(){
        map.update();
        map2.update();

    }
}
