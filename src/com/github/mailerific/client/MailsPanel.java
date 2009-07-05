package com.github.mailerific.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MailsPanel extends Composite implements MailList.Listener {

    private final VerticalPanel list = new VerticalPanel();
    private MailList mailList; // the current mail list
    private final Label title;
    private final MailList incoming;
    private final MailList outgoing;
    private final HorizontalPanel bottomPanel;
    private final DecoratorPanel prev, next;

    public MailsPanel(final UserAccount user) {
        incoming = new MailList(user, MailList.INCOMING, this);
        outgoing = new MailList(user, MailList.OUTGOING, this);
        mailList = incoming; // show incoming by default
        VerticalPanel main = new VerticalPanel();
        main.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        title = new Label("In");
        title.setWidth(ContentPanel.WIDTH);
        title.addStyleName("content-title");
        main.add(title);

        VerticalPanel mainContent = new VerticalPanel();

        list.setWidth(ContentPanel.WIDTH);
        list.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        list.setSpacing(10);

        mainContent.add(list);

        bottomPanel = new HorizontalPanel();
        bottomPanel.setWidth(ContentPanel.WIDTH);
        bottomPanel.setVisible(false);

        Label refreshLabel = new Label("Refresh");
        refreshLabel.setStyleName("button-style");
        refreshLabel.setWidth("60px");
        refreshLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                mailList.reset();
                mailList.nextPage();
            }
        });
        DecoratorPanel refresh = new DecoratorPanel();
        refresh.add(refreshLabel);
        refresh.addStyleName("content-button");
        bottomPanel.add(refresh);
        bottomPanel.setCellHorizontalAlignment(refresh,
                HorizontalPanel.ALIGN_LEFT);

        HorizontalPanel directionPanel = new HorizontalPanel();
        directionPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

        Label prevLabel = new Label("<");
        prevLabel.setStyleName("button-style");
        prevLabel.setWidth("15px");
        prevLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                mailList.previousPage();
            }
        });
        prev = new DecoratorPanel();
        prev.add(prevLabel);
        prev.addStyleName("content-button");
        directionPanel.add(prev);

        Label nextLabel = new Label(">");
        nextLabel.setStyleName("button-style");
        nextLabel.setWidth("15px");
        nextLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                mailList.nextPage();
            }
        });
        next = new DecoratorPanel();
        next.add(nextLabel);
        next.addStyleName("content-button");
        directionPanel.add(next);

        bottomPanel.add(directionPanel);
        bottomPanel.setCellHorizontalAlignment(directionPanel,
                HorizontalPanel.ALIGN_RIGHT);

        mainContent.add(bottomPanel);

        main.add(mainContent);

        DecoratorPanel decorator = new DecoratorPanel();
        decorator.add(main);
        decorator.setWidth("100%");
        decorator.addStyleName("content-form");
        initWidget(decorator);
        DeferredCommand.addCommand(new Command() {
            @Override
            public void execute() {
                // load the lists
                mailList.nextPage();
            }
        });
    }

    public void showIncoming() {
        mailList = incoming;
        mailList.currentPage();
        title.setText("In");
    }

    public void showOutgoing() {
        mailList = outgoing;
        mailList.currentPage();
        title.setText("Out");
    }

    @Override
    public void afterRetrieve(final List<Mail> mails) {
        list.clear();
        if (!mails.isEmpty()) {
            bottomPanel.setVisible(true);
            prev.setVisible(mailList.hasPreviousPage());
            next.setVisible(mailList.hasNextPage());
            for (Mail mail : mails) {
                list.add(createItem(mail));
            }
        } else {
            bottomPanel.setVisible(false);
            HorizontalPanel panel = new HorizontalPanel();
            panel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
            panel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
            Label message = new Label("No messages found.");
            panel.add(message);
            list.add(message);
        }
    }

    private Widget createItem(final Mail mail) {
        DecoratorPanel item = new DecoratorPanel();
        VerticalPanel panel = new VerticalPanel();
        Label text = new Label(mail.getMessage());
        panel.add(text);
        Label past = new Label(mail.getDatePast());
        past.addStyleName("past");
        panel.add(past);
        panel.setCellVerticalAlignment(past, VerticalPanel.ALIGN_BOTTOM);
        panel.setWidth("850px");
        panel.setHeight("65px");
        item.add(panel);
        return item;
    }

}
