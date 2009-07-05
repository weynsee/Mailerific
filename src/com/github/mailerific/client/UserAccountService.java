package com.github.mailerific.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("services/user")
public interface UserAccountService extends RemoteService {

    UserAccount authenticate(String uri);

    Boolean isUsernameUnique(Long id, String username);

    /*
     * the following methods deal with properties only and not the User object.
     * Ideally we should use the object directly, but exception happens when
     * object is marked as detached. Anyway, since there are only a few save
     * methods possible this should suffice
     */

    void unmarkFirstTime(Long id) throws NotLoggedInException;

    void saveInSettings(Long id, String subject) throws NotLoggedInException;

    void saveOutSettings(Long id, String subject, boolean includeSignature,
            String signature) throws NotLoggedInException;

    void savePublicSettings(Long id, boolean enable, String subject,
            String username) throws NotLoggedInException,
            UsernameNotUniqueException;

    /*
     * below are the listing functions
     */

    List<Mail> listIncoming(final String email, final Long upperBound)
            throws NotLoggedInException;

    List<Mail> listIncoming(final String email) throws NotLoggedInException;

    List<Mail> listOutgoing(final String email, final Long upperBound)
            throws NotLoggedInException;

    List<Mail> listOutgoing(final String email) throws NotLoggedInException;

    void removeUser(Long id) throws NotLoggedInException;

}