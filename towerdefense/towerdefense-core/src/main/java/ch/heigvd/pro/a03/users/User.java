package ch.heigvd.pro.a03.users;

import java.util.Objects;

public class User {

    private String username;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private String password;

    public User(String userName,String motDePasse){
        this.username = userName;
        this.password = motDePasse;
    }


    public String toString(){
        return this.username + " " + this.password;
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
