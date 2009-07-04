package com.github.mailerific.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mailerific.client.UserAccount;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public abstract class SecureServlet extends HttpServlet {

    @Override
    protected void service(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
            String url = UrlUtils.fullUrl(request);
            request.setAttribute("loginUrl", userService.createLoginURL(url));
            request.setAttribute("home", UrlUtils.baseUrl(request));
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        UserAccount account = Users.getUserByEmail(user.getEmail());
        if (account == null) {
            // we don't know who this is.
            // It has a Google account, but not a registered user
            // so just redirect
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        doSecureAction(request, response, account);
    }

    protected abstract void doSecureAction(HttpServletRequest request,
            HttpServletResponse response, UserAccount user)
            throws ServletException, IOException;

}
