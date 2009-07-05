package com.github.mailerific.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

public class Main extends Composite implements TopPanel.Handler {

    private final ContentPanel contentPanel;
    private final UserAccount user;

    public Main(final UserAccount user) {
        this.user = user;
        DockPanel mainPanel = new DockPanel();
        mainPanel.setWidth("100%");
        TopPanel top = new TopPanel(this);
        top.setWidth("100%");
        mainPanel.add(top, DockPanel.NORTH);
        mainPanel.add(new HTML("<hr class=\"line-border\"/>"), DockPanel.NORTH);
        contentPanel = new ContentPanel(user);
        contentPanel.setWidth("100%");
        mainPanel.add(contentPanel, DockPanel.CENTER);
        initWidget(mainPanel);
    }

    @Override
    public void onHomeSelection() {
        contentPanel.showMails();
    }

    @Override
    public void onOfflineSelection() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPromoteSelection() {
        DialogBox dialog = DialogFactory.createPromoteDialog();
        dialog.center();
        dialog.show();
    }

    @Override
    public void onSettingsSelection() {
        contentPanel.showSettings();
    }

    @Override
    public void onSignOutSelection() {
        DialogBox dialog = DialogFactory.createSignOutDialog(user.getId(), user
                .getLogoutUrl());
        dialog.center();
        dialog.show();
    }

}
