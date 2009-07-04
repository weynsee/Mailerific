package com.github.mailerific.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

public abstract class DefaultCallback<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(final Throwable caught) {
        // should do something about the error, but what?
        GWT.log("Error during remote call", caught);
        if (caught instanceof NotLoggedInException) {
            onNotLoggedInException(caught);
        } else {
            DialogBox error = DialogFactory.createErrorDialog();
            error.center();
            error.show();
        }
    }

    protected void onNotLoggedInException(final Throwable caught) {
        DialogBox error = DialogFactory.createNotLoggedInErrorDialog();
        error.center();
        error.show();
    }

}
