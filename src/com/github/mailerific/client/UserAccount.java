package com.github.mailerific.client;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserAccount implements Serializable {

    public static final String DEFAULT_SUBJECT = "This e-mail was sent to you by Mailerific";

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String email;
    @Persistent
    private boolean firstTimeUser;
    @Persistent
    private String inSubject = DEFAULT_SUBJECT;
    @Persistent
    private String outSubject = DEFAULT_SUBJECT;
    @Persistent
    private String publicSubject = DEFAULT_SUBJECT;
    @Persistent
    private boolean outIncludeSig;
    @Persistent
    private String outSignature;
    @Persistent
    private boolean enablePublic;
    @Persistent
    private String username;
    @NotPersistent
    private String nickname;
    @NotPersistent
    private String loginUrl;
    @NotPersistent
    private String logoutUrl;
    @NotPersistent
    private boolean loggedIn;
    @NotPersistent
    private boolean mailerificUser;

    public Long getId() {
        return this.id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isFirstTimeUser() {
        return firstTimeUser;
    }

    public void setFirstTimeUser(final boolean firstTimeUser) {
        this.firstTimeUser = firstTimeUser;
    }

    public String getInSubject() {
        return inSubject;
    }

    public void setInSubject(final String inSubject) {
        this.inSubject = inSubject;
    }

    public String getOutSubject() {
        return outSubject;
    }

    public void setOutSubject(final String outSubject) {
        this.outSubject = outSubject;
    }

    public String getPublicSubject() {
        return publicSubject;
    }

    public void setPublicSubject(final String publicSubject) {
        this.publicSubject = publicSubject;
    }

    public boolean isOutIncludeSig() {
        return outIncludeSig;
    }

    public void setOutIncludeSig(final boolean outIncludeSig) {
        this.outIncludeSig = outIncludeSig;
    }

    public String getOutSignature() {
        return outSignature;
    }

    public void setOutSignature(final String outSignature) {
        this.outSignature = outSignature;
    }

    public boolean isEnablePublic() {
        return enablePublic;
    }

    public void setEnablePublic(final boolean enablePublic) {
        this.enablePublic = enablePublic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(final String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(final String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(final boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isMailerificUser() {
        return mailerificUser;
    }

    public void setMailerificUser(final boolean mailerificUser) {
        this.mailerificUser = mailerificUser;
    }

}
