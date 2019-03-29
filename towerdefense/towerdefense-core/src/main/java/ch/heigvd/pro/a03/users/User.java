package ch.heigvd.pro.a03.users;

import java.util.Objects;

public class User {

    private String username;
    private String password;

    User(String userName,String motDePasse){
        this.username = userName;
        this.password = motDePasse;
    }

    public String getUserName() {
        return this.username;
    }

    public String toString(){
        return this.username + " " + this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(this.username, user.getUserName()) &&
                Objects.equals(this.password, user.password);
    }
}
