package com.github.mailerific.client;

import com.google.gwt.user.client.ui.DialogBox;

public class SaveUserCallback extends CallbackWithAutomaticRedirect<Void> {

    public SaveUserCallback(final UserAccount user) {
        super(user);
    }

    @Override
    public void onSuccess(final Void result) {
        DialogBox dialog = DialogFactory.createSaveSuccessDialog();
        dialog.center();
        dialog.show();
    }

}
