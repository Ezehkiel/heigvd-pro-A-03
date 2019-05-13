package ch.heigvd.pro.a03.users;

public class Score {
    private int userId;
    private int nbPartieJoue;
    private int nbPartieGagne;

    public Score(int userId, int nbPartieJoue, int nbPartieGagne){
        this.userId = userId;
        this.nbPartieJoue = nbPartieJoue;
        this.nbPartieGagne = nbPartieGagne;
    }
}
