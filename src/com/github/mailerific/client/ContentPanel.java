package com.github.mailerific.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class ContentPanel extends Composite implements ContentSidePanel.Handler {

    public static final String WIDTH = "890px";

    private final SettingsPanel settings;
    private final MailsPanel mails;
    private final ContentSidePanel sidePanel;

    public ContentPanel(final UserAccount user) {
        DockPanel panel = new DockPanel();
        sidePanel = new ContentSidePanel(this);
        panel.add(sidePanel, DockPanel.WEST);
        settings = new SettingsPanel(user);
        mails = new MailsPanel(user);
        FlowPanel content = new FlowPanel();
        settings.setVisible(false);
        mails.setVisible(false);
        content.add(settings);
        content.add(mails);
        content.setWidth(WIDTH);
        panel.add(content, DockPanel.CENTER);
        initWidget(panel);
        showSettings();
    }

    public void showSettings() {
        mails.setVisible(false);
        settings.setVisible(true);
        sidePanel.showPublicMenuItem();
    }

    public void showMails() {
        settings.setVisible(false);
        mails.setVisible(true);
        sidePanel.hidePublicMenuItem();
    }

    @Override
    public void onClickIn() {
        if (settings.isVisible()) {
            settings.showInSettings();
        } else if (mails.isVisible()) {
            mails.showIncoming();
        }
    }

    @Override
    public void onClickOut() {
        if (settings.isVisible()) {
            settings.showOutSettings();
        } else if (mails.isVisible()) {
            mails.showOutgoing();
        }
    }

    @Override
    public void onClickPublic() {
        if (settings.isVisible()) {
            settings.showPublicSettings();
        }
    }
}
