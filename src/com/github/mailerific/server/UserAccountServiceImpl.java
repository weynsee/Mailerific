package com.github.mailerific.server;

import java.util.List;

import com.github.mailerific.client.Mail;
import com.github.mailerific.client.NotLoggedInException;
import com.github.mailerific.client.UserAccount;
import com.github.mailerific.client.UserAccountService;
import com.github.mailerific.client.UsernameNotUniqueException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserAccountServiceImpl extends RemoteServiceServlet implements
        UserAccountService {

    @Override
    public void save(final UserAccount user) throws NotLoggedInException {
        checkLoggedIn();
        Users.save(user);
    }

    private void checkLoggedIn() throws NotLoggedInException {
        User user = UserServiceFactory.getUserService().getCurrentUser();
        if (user == null) {
            throw new NotLoggedInException("Not logged in.");
        }
    }

    @Override
    public UserAccount authenticate(final String uri) {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        UserAccount userAccount;
        if (user != null) {
            // has google account, now check for Mailerific account
            userAccount = Users.getUserByEmail(user.getEmail());
            if (userAccount == null) {
                // if no Mailerific account, sign him up
                userAccount = signUp(user);
            }
            userAccount.setNickname(user.getNickname());
            userAccount.setLoggedIn(true);
            userAccount.setLogoutUrl(userService.createLogoutURL(uri));
        } else {
            userAccount = new UserAccount();
            // not logged in to google, redirect to login page
            userAccount.setLoggedIn(false);
            userAccount.setLoginUrl(userService.createLoginURL(uri));
        }
        return userAccount;
    }

    private UserAccount signUp(final User user) {
        UserAccount account = new UserAccount();
        account.setFirstTimeUser(true);
        account.setEmail(user.getEmail());
        return Users.save(account);
    }

    @Override
    public void saveInSettings(final Long id, final String subject)
            throws NotLoggedInException {
        checkLoggedIn();
        UserAccount account = Users.getUserById(id);
        account.setInSubject(subject);
    }

    @Override
    public void saveOutSettings(final Long id, final String subject,
            final boolean includeSig, final String signature)
            throws NotLoggedInException {
        checkLoggedIn();
        UserAccount account = Users.getUserById(id);
        account.setOutSubject(subject);
        account.setOutIncludeSig(includeSig);
        account.setOutSignature(signature);
    }

    @Override
    public void savePublicSettings(final Long id, final boolean enable,
            final String subject, final String username)
            throws NotLoggedInException, UsernameNotUniqueException {
        checkLoggedIn();
        if (!isUsernameUnique(id, username)) {
            throw new UsernameNotUniqueException();
        }
        UserAccount account = Users.getUserById(id);
        account.setPublicSubject(subject);
        account.setEnablePublic(enable);
        account.setUsername(username);
    }

    @Override
    public void unmarkFirstTime(final Long id) throws NotLoggedInException {
        checkLoggedIn();
        UserAccount account = Users.getUserById(id);
        account.setFirstTimeUser(false);
    }

    @Override
    public Boolean isUsernameUnique(final Long id, final String username) {
        UserAccount account = Users.getUserById(id);
        if (username.equals(account.getUsername())) {
            // not unique, but user owns the previous one
            return true;
        }
        return null == Users.getUserByUsername(username);
    }

    public List<Mail> listIncoming(final String email, final Long upperBound)
            throws NotLoggedInException {
        checkLoggedIn();
        return Mails.getByOwner(email, upperBound);
    }

    public List<Mail> listIncoming(final String email)
            throws NotLoggedInException {
        checkLoggedIn();
        return Mails.getByOwner(email);
    }

    public List<Mail> listOutgoing(final String email, final Long upperBound)
            throws NotLoggedInException {
        checkLoggedIn();
        return Mails.getBySender(email, upperBound);
    }

    public List<Mail> listOutgoing(final String email)
            throws NotLoggedInException {
        checkLoggedIn();
        return Mails.getBySender(email);
    }
}
