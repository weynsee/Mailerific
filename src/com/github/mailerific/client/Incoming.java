package com.github.mailerific.client;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Incoming implements Serializable, Mail {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String message;
    @Persistent
    private Date receivedDate;
    @Persistent
    private Date mailedDate;
    @Persistent
    private String owner;
    @Persistent
    private String subject;
    @Persistent
    private boolean mailSent;

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(final Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getMailedDate() {
        return mailedDate;
    }

    public void setMailedDate(final Date mailedDate) {
        this.mailedDate = mailedDate;
    }

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(final boolean mailSent) {
        this.mailSent = mailSent;
    }

}
