package com.github.mailerific.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserAccountServiceAsync {

    public static UserAccountServiceAsync RPC = GWT
            .create(UserAccountService.class);

    void authenticate(String baseUrl, AsyncCallback<UserAccount> callback);

    void isUsernameUnique(Long id, String username,
            AsyncCallback<Boolean> callback);

    void unmarkFirstTime(Long id, AsyncCallback<Void> callback);

    void saveInSettings(Long id, String subject, AsyncCallback<Void> callback);

    void saveOutSettings(Long id, String subject, boolean includeSignature,
            String signature, AsyncCallback<Void> callback);

    void savePublicSettings(Long id, boolean enable, String subject,
            String username, AsyncCallback<Void> callback);

    void listIncoming(final String email, final Long upperBound,
            AsyncCallback<List<Mail>> callback);

    void listIncoming(final String email, AsyncCallback<List<Mail>> callback);

    void listOutgoing(final String email, AsyncCallback<List<Mail>> callback);

    void listOutgoing(final String email, Long upperBound,
            AsyncCallback<List<Mail>> callback);

    void removeUser(final Long id, AsyncCallback<Void> callback);

}
