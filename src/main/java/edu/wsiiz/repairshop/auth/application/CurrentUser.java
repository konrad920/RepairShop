package edu.wsiiz.repairshop.auth.application;

import edu.wsiiz.repairshop.auth.domain.user.User;

public class CurrentUser {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void set(User user) {
        currentUser.set(user);
    }

    public static User get() {
        return currentUser.get();
    }

    public static void logout() {
        currentUser.remove();
    }
}
