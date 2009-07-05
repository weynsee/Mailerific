package com.github.mailerific.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.github.mailerific.client.UserAccount;

public class Users {

    private static final Users MANAGER = new Users();

    private Users() {
    }

    @SuppressWarnings("unchecked")
    private UserAccount findByEmail(final String email) {
        PersistenceManager pm = Persistence.manager();
        Query query = pm.newQuery(UserAccount.class, "email == mail");
        query.declareParameters("String mail");
        List<UserAccount> list = (List<UserAccount>) query.execute(email);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    private UserAccount findByUsername(final String username) {
        PersistenceManager pm = Persistence.manager();
        Query query = pm.newQuery(UserAccount.class, "usernameUpper == name");
        query.declareParameters("String name");
        List<UserAccount> list = (List<UserAccount>) query.execute(username
                .toUpperCase());
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    private UserAccount findById(final Long id) {
        PersistenceManager pm = Persistence.manager();
        return pm.getObjectById(UserAccount.class, id);
    }

    private UserAccount saveUser(final UserAccount user) {
        PersistenceManager pm = Persistence.manager();
        return pm.makePersistent(user);
    }

    public static UserAccount getUserByEmail(final String email) {
        return MANAGER.findByEmail(email);
    }

    public static UserAccount getUserByUsername(final String username) {
        return MANAGER.findByUsername(username);
    }

    public static UserAccount getUserById(final Long id) {
        return MANAGER.findById(id);
    }

    public static UserAccount save(final UserAccount user) {
        return MANAGER.saveUser(user);
    }

}
