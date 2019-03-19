package ch.heigvd.pro.a03;

import java.util.ArrayList;
import java.util.List;

public class ListeUtilisateurs {
    private final List<UserJson> users = new ArrayList<UserJson>();

    public List<UserJson> getUsers() {
        return users;
    }
    public boolean exist(UserJson user){
        for(UserJson u:users){
            if(u.equals(user)){
                return true;
            }
        }
        return false;
    }
    public void addUser(UserJson newUser){
        users.add(newUser);
    }
}
