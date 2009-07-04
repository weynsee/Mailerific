package com.github.mailerific.client;

import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;

public interface Settings {

    Widget getTitle();

    Widget getInstruction();

    HTMLTable getForm();

    void init(UserAccount account);

    void save();

}
