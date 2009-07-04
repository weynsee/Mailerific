package com.github.mailerific.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

public class OutSettings extends AbstractSettings implements Settings {

    private final Label instruction = new HTML(
            "You can send messages to other people"
                    + " by using the URL <br/><span class=\"sample-url\">"
                    + GWT.getHostPageBaseURL()
                    + "o/&lt;Recipient's e-mail address&gt;/&lt;Your message here&gt;</i>.<br/>Please note that you can only send"
                    + " messages when you are signed in to Mailerific");
    private final Label title = new Label("/o");
    private final FlexTable form;
    private UserAccount user;
    private final CheckBox includeCheckBox = new CheckBox();
    private final TextArea includeTextArea = new TextArea();
    private final DisclosurePanel signaturePanel = new DisclosurePanel();
    private SaveUserCallback callback;

    public OutSettings() {
        form = new FlexTable();
        form.setCellSpacing(6);
        form.setWidget(0, 0, subjectLabel);
        form.setWidget(0, 1, subjectTextBox);

        Label includeLabel = new Label("Include signature");
        includeLabel.setTitle("Include this text in every mail you send");
        form.setWidget(1, 0, includeLabel);
        form.setWidget(1, 1, includeCheckBox);

        includeTextArea.setCharacterWidth(50);
        includeTextArea.setHeight("80px");

        FlexTable signatureTable = new FlexTable();
        signatureTable.setCellSpacing(6);
        signatureTable.setWidget(0, 0, new Label("Signature"));
        signatureTable.setWidget(0, 1, includeTextArea);
        signatureTable.getCellFormatter().setVerticalAlignment(0, 0,
                HasVerticalAlignment.ALIGN_TOP);

        signaturePanel.setAnimationEnabled(true);
        signaturePanel.setContent(signatureTable);
        form.setWidget(2, 0, signaturePanel);
        form.getFlexCellFormatter().setColSpan(2, 0, 2);

        includeCheckBox.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                signaturePanel.setOpen(includeCheckBox.getValue());
            }

        });
    }

    @Override
    public void init(final UserAccount account) {
        this.user = account;
        subjectTextBox.setValue(user.getOutSubject());
        includeCheckBox.setValue(user.isOutIncludeSig());
        if (user.isOutIncludeSig()) {
            signaturePanel.setOpen(true);
        }
        includeTextArea.setValue(user.getOutSignature());
        callback = new SaveUserCallback(user);
    }

    @Override
    public FlexTable getForm() {
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
        boolean includeSig = includeCheckBox.getValue();
        String signature = includeTextArea.getValue();

        user.setOutSubject(subject);
        user.setOutIncludeSig(includeSig);
        user.setOutSignature(signature);

        UserAccountServiceAsync.RPC.saveOutSettings(user.getId(), subject,
                includeSig, signature, callback);
    }

}
