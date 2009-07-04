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
public class Outgoing implements Serializable, Mail {

    public static final int MAX_RETRY = 5;

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
    private String sender;
    @Persistent
    private String subject;
    @Persistent
    private int retryCount;
    @Persistent
    private String recipient;
    @Persistent
    private boolean mailSent;
    @Persistent
    private String signature;

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

    public String getSender() {
        return sender;
    }

    public void setSender(final String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(final int mailRetryCount) {
        this.retryCount = mailRetryCount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(final String recipient) {
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(final boolean mailSent) {
        this.mailSent = mailSent;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

}
