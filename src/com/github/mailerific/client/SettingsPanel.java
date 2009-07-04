package com.github.mailerific.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SettingsPanel extends Composite {

    private final Settings in = new InSettings();
    private final Settings out = new OutSettings();
    private final Settings pub = new PublicSettings();

    private final Settings[] settings = { in, out, pub };

    private final DisclosurePanel form;
    private final Label title;
    private Settings lastDisplayed;

    public SettingsPanel(final UserAccount user) {
        VerticalPanel main = new VerticalPanel();
        main.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        DecoratorPanel decorator = new DecoratorPanel();
        title = new Label("Settings");
        title.setWidth(ContentPanel.WIDTH);
        title.addStyleName("content-title");
        main.add(title);

        VerticalPanel contentPanel = new VerticalPanel();
        contentPanel.setWidth(ContentPanel.WIDTH);
        contentPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        for (Settings setting : settings) {
            setting.init(user);

            Widget desc = setting.getTitle();
            desc.addStyleName("content-namespace");
            desc.setVisible(false);
            contentPanel.add(desc);

            desc = setting.getInstruction();
            desc.addStyleName("content-desc");
            desc.setVisible(false);
            contentPanel.add(desc);
        }

        HTML line = new HTML("<hr/>");
        line.setWidth("100%");

        contentPanel.add(line);
        for (Settings setting : settings) {
            HTMLTable form = setting.getForm();
            form.setVisible(false);
            contentPanel.add(form);
        }

        Label button = new Label("Save");
        button.setStyleName("button-style");
        button.setWidth("50px");
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                lastDisplayed.save();
            }

        });
        DecoratorPanel buttonPanel = new DecoratorPanel();
        buttonPanel.add(button);
        buttonPanel.addStyleName("content-button");
        contentPanel.add(buttonPanel);
        contentPanel.setSpacing(2);

        form = new DisclosurePanel();
        form.setAnimationEnabled(true);
        form.setContent(contentPanel);
        form.setStyleName("");
        main.add(form);

        decorator.add(main);
        decorator.setWidth("100%");
        decorator.addStyleName("content-form");
        initWidget(decorator);
        showInSettings();
    }

    public void showInSettings() {
        showSettings(in);
    }

    public void showOutSettings() {
        showSettings(out);
    }

    public void showPublicSettings() {
        showSettings(pub);
    }

    private void showSettings(final Settings setting) {
        if (lastDisplayed != setting) {
            form.setOpen(false);
            // allow effects time to run
            Timer t = new Timer() {

                @Override
                public void run() {
                    if (lastDisplayed != null)
                        toggleVisibility(lastDisplayed, false);
                    toggleVisibility(setting, true);
                    lastDisplayed = setting;
                    form.setOpen(true);
                }

            };
            t.schedule(350);
        }
    }

    private void toggleVisibility(final Settings setting, final boolean visible) {
        setting.getTitle().setVisible(visible);
        setting.getInstruction().setVisible(visible);
        setting.getForm().setVisible(visible);
    }
}
