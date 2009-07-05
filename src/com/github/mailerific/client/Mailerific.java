package com.github.mailerific.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mailerific implements EntryPoint {

    private RootPanel root;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        root = RootPanel.get();
        UserAccountServiceAsync.RPC.authenticate(GWT.getHostPageBaseURL(),
                new DefaultCallback<UserAccount>() {
                    public void onSuccess(final UserAccount result) {
                        if (result.isLoggedIn()) {
                            loadApp(result);
                        } else {
                            loadTitlePage(result.getLoginUrl());
                        }
                    }
                });
    }

    private void loadApp(final UserAccount user) {
        Main main = new Main(user);
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(main);
        panel.addStyleName("container");
        root.add(panel);
        if (user.isFirstTimeUser()) {
            DialogBox welcome = DialogFactory.createFirstTimeDialog();
            welcome.center();
            welcome.show();
            user.setFirstTimeUser(false);
            UserAccountServiceAsync.RPC.unmarkFirstTime(user.getId(),
                    new DefaultCallback<Void>() {
                        @Override
                        public void onSuccess(final Void result) {
                            // do nothing
                        }
                    });
        }
    }

    private void loadTitlePage(final String signInLink) {
        WelcomePanel panel = new WelcomePanel();
        panel.addSignInHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                Window.Location.assign(signInLink);
            }

        });
        root.add(panel);
    }
}
