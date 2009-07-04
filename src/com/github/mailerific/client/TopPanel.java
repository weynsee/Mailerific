package com.github.mailerific.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;

public class TopPanel extends Composite {

    public TopPanel(final Handler handler) {
        HorizontalPanel panel = new HorizontalPanel();

        panel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

        HTML title = new HTML("<span class=\"em-mail\">Mail</span>erific!");
        title.addStyleName("logo");
        DecoratorPanel decoratorPanel = new DecoratorPanel();
        decoratorPanel.add(title);
        panel.add(decoratorPanel);
        panel.setCellHorizontalAlignment(decoratorPanel,
                HorizontalPanel.ALIGN_LEFT);

        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Settings", new Command() {

            @Override
            public void execute() {
                handler.onSettingsSelection();
            }

        });
        menuBar.addSeparator();
        menuBar.addItem("Mails", new Command() {

            @Override
            public void execute() {
                handler.onHomeSelection();
            }

        });
        // disable this for now since it's not yet implemented
        // menuBar.addSeparator();
        // menuBar.addItem("Offline", new Command() {
        //
        // @Override
        // public void execute() {
        // handler.onOfflineSelection();
        // }
        //
        // });
        menuBar.addSeparator();
        menuBar.addItem("Promote", new Command() {

            @Override
            public void execute() {
                handler.onPromoteSelection();
            }

        });
        menuBar.addSeparator();
        menuBar.addItem("Sign Out", new Command() {

            @Override
            public void execute() {
                handler.onSignOutSelection();
            }

        });
        menuBar.setStyleName("");

        panel.add(menuBar);
        panel.setCellVerticalAlignment(menuBar, HorizontalPanel.ALIGN_BOTTOM);

        initWidget(panel);
        panel.setStyleName("top-panel");
    }

    public static interface Handler {
        public void onHomeSelection();

        public void onPromoteSelection();

        public void onSettingsSelection();

        public void onOfflineSelection();

        public void onSignOutSelection();
    }

}
