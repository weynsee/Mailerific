package com.github.mailerific.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NotLoggedInException extends Exception implements Serializable {

    public NotLoggedInException() {
        super();
    }

    public NotLoggedInException(final String message) {
        super(message);
    }

}
