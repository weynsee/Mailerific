package com.github.mailerific.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class InSettings extends AbstractSettings implements Settings {

    private final Label instruction = new HTML(
            "You can send messages to your inbox"
                    + " by using the URL <br/><span class=\"sample-url\">"
                    + GWT.getHostPageBaseURL()
                    + "i/&lt;Your message here&gt;</span><br/>Please note that you can only send"
                    + " messages when you are signed in to Mailerific.");
    private final Label title = new Label("/i");
    private final Grid form;
    private UserAccount user;
    private SaveUserCallback callback;

    public InSettings() {
        form = new Grid(1, 2);
        form.setCellSpacing(6);
        form.setWidget(0, 0, subjectLabel);
        form.setWidget(0, 1, subjectTextBox);
    }

    @Override
    public void init(final UserAccount user) {
        this.user = user;
        callback = new SaveUserCallback(user);
        subjectTextBox.setValue(user.getInSubject());
    }

    @Override
    public Grid getForm() {
        return form;
    }

    @Override
    public Label getInstruction() {
        return instruction;
    }

    @Override
    public Label getTitle() {
        return title;
    }

    @Override
    public void save() {
        String subject = subjectTextBox.getValue();
        user.setInSubject(subject);
        UserAccountServiceAsync.RPC.saveInSettings(user.getId(), subject,
                callback);
    }

}
