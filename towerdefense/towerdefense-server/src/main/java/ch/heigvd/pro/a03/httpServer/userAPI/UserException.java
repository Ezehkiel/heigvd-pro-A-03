package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.users.User;

class UserException extends Exception {

    private String message;


    private User user;

    public UserException(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return this.message;
    }

    public User getUser() {
        return user;
    }


}

