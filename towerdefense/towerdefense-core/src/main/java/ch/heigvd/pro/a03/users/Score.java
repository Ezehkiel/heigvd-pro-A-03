package ch.heigvd.pro.a03.users;

public class Score {
    private int userId;
    private String username;
    private int nbPartieJoue;
    private int nbPartieGagne;

    public Score(int userId, String username, int nbPartieJoue, int nbPartieGagne){
        this.userId = userId;
        this.username = username;
        this.nbPartieJoue = nbPartieJoue;
        this.nbPartieGagne = nbPartieGagne;
    }

    public String getUsername() {
        return username;
    }

    public int getNbPartieJoue() {
        return nbPartieJoue;
    }

    public int getNbPartieGagne() {
        return nbPartieGagne;
    }
}
