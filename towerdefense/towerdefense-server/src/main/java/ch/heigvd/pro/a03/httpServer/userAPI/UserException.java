package ch.heigvd.pro.a03.httpServer.userAPI;

import ch.heigvd.pro.a03.users.User;

/**
 * This Exception class is used to catch error in link with user check.
 * It's user to create JSONObject response with error easily.
 */
class UserException extends Exception {

    private String message;
    private User user;

    /**
     * Constructor
     *
     * @param message the message that need to be forwarded
     * @param user the user that generated the error
     */
    public UserException(String message, User user) {
        this.message = message;
        this.user = user;
    }

    /**
     * Get the message of the exception
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Get the user of the exception
     * @return the user
     */
    public User getUser() {
        return user;
    }


}

