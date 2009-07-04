package com.github.mailerific.client;

import com.google.gwt.user.client.Window;

public abstract class CallbackWithAutomaticRedirect<T> extends
        DefaultCallback<T> {

    private final UserAccount user;

    public CallbackWithAutomaticRedirect(final UserAccount user) {
        this.user = user;
    }

    @Override
    protected void onNotLoggedInException(final Throwable caught) {
        super.onNotLoggedInException(caught);
        Window.Location.replace(user.getLogoutUrl());
    }

}
