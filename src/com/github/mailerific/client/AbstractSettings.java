package com.github.mailerific.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public abstract class AbstractSettings implements Settings {

    protected final Label subjectLabel;
    protected final TextBox subjectTextBox;

    public AbstractSettings() {
        subjectLabel = new Label("E-mail subject");
        subjectLabel.setTitle("Subject to be included when the e-mail is sent");
        subjectTextBox = new TextBox();
        subjectTextBox
                .setTitle("Subject to be included when the e-mail is sent");
        subjectTextBox.setMaxLength(100);
        subjectTextBox.setVisibleLength(50);
    }

    protected void doInternalSave() {
    }

}
