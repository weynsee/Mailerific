package com.github.mailerific.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Label;

public class LoadingPopup extends DecoratedPopupPanel {

    private boolean showIt;

    public LoadingPopup() {
        super(false, true);
        Label label = new Label("Loading...");
        setAnimationEnabled(true);
        center();
        setWidget(label);
    }

    public void showDelayed() {
        showIt = true;
        new Timer() {
            @Override
            public void run() {
                if (!LoadingPopup.this.isShowing() && showIt)
                    LoadingPopup.this.show();
            }

        }.schedule(50);
    }

    @Override
    public void hide() {
        showIt = false;
        super.hide();
    }

}
