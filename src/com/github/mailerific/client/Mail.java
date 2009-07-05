package com.github.mailerific.client;

import java.util.Date;

public interface Mail {

    String getMessage();

    Long getId();

    Date getReceivedDate();

    void setDatePast(String date);

    String getDatePast();

}
