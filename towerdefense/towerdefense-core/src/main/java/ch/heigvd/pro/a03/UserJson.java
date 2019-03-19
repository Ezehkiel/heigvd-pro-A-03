package ch.heigvd.pro.a03;

import java.util.Objects;

public class UserJson {
    private String userName=null;
    private String mdp=null;
    UserJson(String userName,String motDePasse){
        this.userName=userName;
        mdp=motDePasse;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserJson user = (UserJson) o;
        return Objects.equals(getUserName(), user.getUserName()) &&
                Objects.equals(mdp, user.mdp);
    }
}
