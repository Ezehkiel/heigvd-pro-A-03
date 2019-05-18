package ch.heigvd.pro.a03.utils;

import ch.heigvd.pro.a03.users.User;

import java.util.Random;

public class RandomPlayer {
    public static final User USER;

    static {
        Random random = new Random();
        int id = random.nextInt();

        USER = new User(id, "Player" + id, null);
    }
}
