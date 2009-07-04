package com.github.mailerific.client;

import com.google.gwt.user.client.ui.DialogBox;

public class SaveUserCallback extends
        CallbackWithAutomaticRedirect<UserAccount> {

    public SaveUserCallback(final UserAccount user) {
        super(user);
    }

    @Override
    public void onSuccess(final UserAccount result) {
        DialogBox dialog = DialogFactory.createSaveSuccessDialog();
        dialog.center();
        dialog.show();
    }

}
