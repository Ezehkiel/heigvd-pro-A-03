package ch.heigvd.pro.a03.users;

import java.util.Date;
import java.util.Objects;

public class User {

    private String username;
    private String password;
    private int id;
    private String token;
    private int nbPartieJoue;
    private int getNbPartieGagne;
    private Date lastLogin;

    public User(int id, String userName,String motDePasse){
        this.id = id;
        this.username = userName;
        this.password = motDePasse;
    }

    public User (int id, String userName,String motDePasse, int nbPartieJoue, int getNbPartieGagne, Date lastLogin){
        this(id, userName, motDePasse);
        this.nbPartieJoue = nbPartieJoue;
        this.getNbPartieGagne = getNbPartieGagne;
        this.lastLogin = lastLogin;
    }


    public String toString(){
        return this.id + " " + this.username ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.username, user.getUsername()) &&
                Objects.equals(this.password, user.password);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
