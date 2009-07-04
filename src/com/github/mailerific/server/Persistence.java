package com.github.mailerific.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class Persistence {

    private static final PersistenceManagerFactory PMF = JDOHelper
            .getPersistenceManagerFactory("transactions-optional");

    private Persistence() {
    }

    private static final ThreadLocal<PersistenceManager> MANAGER = new ThreadLocal<PersistenceManager>() {
        @Override
        protected PersistenceManager initialValue() {
            return PMF.getPersistenceManager();
        }
    };

    public static PersistenceManager manager() {
        return MANAGER.get();
    }

    public static void close() {
        try {
            PersistenceManager manager = manager();
            manager.close();
        } finally {
            MANAGER.remove();
        }
    }

}
