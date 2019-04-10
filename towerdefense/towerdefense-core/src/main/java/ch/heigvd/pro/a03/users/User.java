package ch.heigvd.pro.a03.users;

import java.util.Objects;

public class User {

    private String username;

    private String password;
    private int id;
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    public User(int id, String userName,String motDePasse){
        this.id = id;
        this.username = userName;
        this.password = motDePasse;
    }


    public String toString(){
        return this.id + " " + this.username + " " + this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.username, user.getUsername()) &&
                Objects.equals(this.password, user.password);
    }
}
