package com.github.mailerific.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class WelcomePanel extends Composite {

    private final DockPanel main;
    private final Label title = new Label("Mailerific.");
    private final HTML titleText = new HTML(
            "Mailerific is a service that lets you "
                    + "send e-mails through your browser's address bar. It's simple, "
                    + "free and you don't even have to sign up. All you need is a Google account.");
    private final Anchor anchor = new Anchor("Try it out.");

    public WelcomePanel() {
        main = new DockPanel();
        title.addStyleName("title-header");
        titleText.addStyleName("title-text");
        anchor.addStyleName("title-text");
        anchor.addStyleName("signin-link");
        main.add(anchor, DockPanel.SOUTH);
        main.add(titleText, DockPanel.SOUTH);
        main.add(title, DockPanel.CENTER);
        initWidget(main);
    }

    public HandlerRegistration addSignInHandler(final ClickHandler handler) {
        return anchor.addClickHandler(handler);
    }

}
