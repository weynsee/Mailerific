package com.github.mailerific.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentSidePanel extends Composite {

    private final MenuItem publicMenuItem;

    public ContentSidePanel(final Handler handler) {
        VerticalPanel menuPanel = new VerticalPanel();
        MenuBar menu = new MenuBar(true);
        MenuItem item = new MenuItem("In", new Command() {

            @Override
            public void execute() {
                handler.onClickIn();

            }
        });
        item.setWidth("90px");
        menu.addItem(item);
        menu.addSeparator();
        item = new MenuItem("Out", new Command() {

            @Override
            public void execute() {
                handler.onClickOut();

            }
        });
        menu.addItem(item);
        menu.addSeparator();

        publicMenuItem = new MenuItem("Public", new Command() {

            @Override
            public void execute() {
                handler.onClickPublic();

            }
        });
        publicMenuItem.setVisible(false);
        menu.addItem(publicMenuItem);

        menu.setWidth("90px");

        menuPanel.add(menu);
        menu.setStyleName("");
        initWidget(menuPanel);
    }

    public void showPublicMenuItem() {
        publicMenuItem.setVisible(true);
    }

    public void hidePublicMenuItem() {
        publicMenuItem.setVisible(false);
    }

    public static interface Handler {
        void onClickIn();

        void onClickOut();

        void onClickPublic();
    }

}
