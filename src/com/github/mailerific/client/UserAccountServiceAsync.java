package com.github.mailerific.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserAccountServiceAsync {

    public static UserAccountServiceAsync RPC = GWT
            .create(UserAccountService.class);

    void authenticate(String baseUrl, AsyncCallback<UserAccount> callback);

    void save(UserAccount user, AsyncCallback<UserAccount> callback);

    void isUsernameUnique(Long id, String username,
            AsyncCallback<Boolean> callback);

    void unmarkFirstTime(Long id, AsyncCallback<UserAccount> callback);

    void saveInSettings(Long id, String subject,
            AsyncCallback<UserAccount> callback);

    void saveOutSettings(Long id, String subject, boolean includeSignature,
            String signature, AsyncCallback<UserAccount> callback);

    void savePublicSettings(Long id, boolean enable, String subject,
            String username, AsyncCallback<UserAccount> callback);

    void listIncoming(final String email, final Long upperBound,
            AsyncCallback<List<Mail>> callback);

    void listIncoming(final String email, AsyncCallback<List<Mail>> callback);

    void listOutgoing(final String email, AsyncCallback<List<Mail>> callback);

    void listOutgoing(final String email, Long upperBound,
            AsyncCallback<List<Mail>> callback);

}
