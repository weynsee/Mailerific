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

public class LoginServlet extends HttpServlet {

    @Override
    protected void service(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (Users.getUserByEmail(user.getEmail()) == null) {
            signUp(user);
        }
        response.sendRedirect("/");
    }

    private void signUp(final User user) {
        UserAccount account = new UserAccount();
        account.setFirstTimeUser(true);
        account.setEmail(user.getEmail());
        Users.save(account);
    }

}
