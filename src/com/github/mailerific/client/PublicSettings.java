package com.github.mailerific.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class PublicSettings extends AbstractSettings implements Settings {

    private final Label instruction = new HTML(
            "You can send messages to your inbox without logging in by"
                    + " using the URL <br/><span class=\"sample-url\">"
                    + GWT.getHostPageBaseURL()
                    + "p/&lt;Your username&gt;/&lt;Your message here&gt;</span>"
                    + "<br/>Enable this setting if you want to send messages to your inbox"
                    + " from other computers.");
    private final Label title = new Label("/p");
    private final FlexTable form;
    private UserAccount user;
    private final CheckBox enableCheckBox = new CheckBox();
    private final TextBox usernameTextBox = new TextBox();
    private final DisclosurePanel disclosure = new DisclosurePanel();
    private final Label errorLabel = new Label(
            "Only letters, numbers, underscore and periods are allowed.");
    private final Label uniqueLabel = new Label(
            "This username is no longer available");
    private final Label validLabel = new Label("This username is available");
    private final DisclosurePanel notification = new DisclosurePanel();
    private SaveUserCallback callback;

    public PublicSettings() {
        form = new FlexTable();
        form.setCellSpacing(6);
        form.setWidth("450px");
        Label enableLabel = new Label("Allow public access");
        enableLabel.setTitle("Allow mail sending without logging in");
        form.setWidget(0, 0, enableLabel);
        form.setWidget(0, 1, enableCheckBox);
        form.getColumnFormatter().setWidth(1, "70%");

        FlexTable publicSettingsTable = new FlexTable();
        publicSettingsTable.setCellSpacing(6);

        HorizontalPanel usernamePanel = new HorizontalPanel();
        usernamePanel.setSpacing(6);
        usernamePanel.add(usernameTextBox);
        Button checkButton = new Button("Check availability");
        checkButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                String username = usernameTextBox.getValue();
                if (!validUsername(username)) {
                    showNotification(errorLabel);
                    return;
                }
                UserAccountServiceAsync.RPC.isUsernameUnique(user.getId(),
                        username, new DefaultCallback<Boolean>() {

                            @Override
                            public void onSuccess(final Boolean result) {
                                if (result) {
                                    showNotification(validLabel);
                                } else {
                                    showNotification(uniqueLabel);
                                }
                            }

                        });
            }

        });
        usernamePanel.add(checkButton);

        publicSettingsTable.setWidget(0, 0, new Label("Username"));
        publicSettingsTable.setWidget(0, 1, usernamePanel);

        HorizontalPanel notificationPanel = new HorizontalPanel();
        errorLabel.addStyleName("error-text");
        errorLabel.setVisible(false);
        notificationPanel.add(errorLabel);
        uniqueLabel.addStyleName("error-text");
        uniqueLabel.setVisible(false);
        notificationPanel.add(uniqueLabel);
        validLabel.addStyleName("success-text");
        validLabel.setVisible(false);
        notificationPanel.add(validLabel);
        notificationPanel.setWidth("400px");

        notification.setAnimationEnabled(true);
        notification.setContent(notificationPanel);
        notification.setStyleName("");
        notification.setOpen(false);

        publicSettingsTable.setWidget(1, 0, notification);
        publicSettingsTable.getFlexCellFormatter().setColSpan(1, 0, 2);

        publicSettingsTable.setWidget(2, 0, subjectLabel);
        publicSettingsTable.setWidget(2, 1, subjectTextBox);

        disclosure.setAnimationEnabled(true);
        disclosure.setContent(publicSettingsTable);
        form.setWidget(1, 0, disclosure);
        form.getFlexCellFormatter().setColSpan(1, 0, 2);
        enableCheckBox.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                disclosure.setOpen(enableCheckBox.getValue());
            }

        });
    }

    private void showNotification(final Label label) {
        if (!label.isVisible()) {
            hideNotification();
            label.setVisible(true);
        }
        notification.setOpen(true);
    }

    private void hideNotification() {
        errorLabel.setVisible(false);
        uniqueLabel.setVisible(false);
        validLabel.setVisible(false);
        notification.setOpen(false);
    }

    @Override
    public void init(final UserAccount account) {
        this.user = account;
        subjectTextBox.setValue(user.getPublicSubject());
        enableCheckBox.setValue(user.isEnablePublic());
        usernameTextBox.setValue(user.getUsername());
        if (user.isEnablePublic()) {
            disclosure.setOpen(true);
        }
        callback = new SaveUserCallback(user) {

            @Override
            public void onFailure(final Throwable caught) {
                if (caught instanceof UsernameNotUniqueException) {
                    showNotification(uniqueLabel);
                    return;
                }
                super.onFailure(caught);
            }

            @Override
            public void onSuccess(final Void result) {
                super.onSuccess(result);
                hideNotification();
            }

        };
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

    private static boolean validUsername(final String username) {
        return username.matches("^[\\w\\.]+$");
    }

    @Override
    public void save() {
        if (!validUsername(usernameTextBox.getValue())) {
            showNotification(errorLabel);
            return;
        }

        String subject = subjectTextBox.getValue();
        boolean enabled = enableCheckBox.getValue();
        String username = usernameTextBox.getValue();

        UserAccountServiceAsync.RPC.savePublicSettings(user.getId(), enabled,
                subject, username, callback);

    }

}
