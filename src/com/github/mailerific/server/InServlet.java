package com.github.mailerific.server;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.mailerific.client.Incoming;
import com.github.mailerific.client.UserAccount;

public class InServlet extends SecureServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(InServlet.class
            .getName());

    @Override
    protected void doSecureAction(final HttpServletRequest request,
            final HttpServletResponse response, final UserAccount user)
            throws IOException, ServletException {
        try {
            String path = request.getPathInfo();
            if (path != null && !path.trim().isEmpty() && path.length() > 1) {
                String message = UrlUtils.appendPathWithQueryString(path
                        .substring(1), request);
                Incoming mail = saveMessage(user, message);
                try {
                    Mails.send(mail);
                } catch (AddressException e) {
                    handleMailError(request, response);
                    return;
                } catch (MessagingException e) {
                    handleMailError(request, response);
                    return;
                }
                request.getRequestDispatcher("/WEB-INF/success-in.jsp")
                        .forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Could not handle this request: \n"
                    + request.toString(), e);
            throw new ServletException(e);
        }
    }

    private void handleMailError(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        request.getRequestDispatcher("/WEB-INF/mail-error.jsp").forward(
                request, response);
    }

    private Incoming saveMessage(final UserAccount user, final String message) {
        Incoming incoming = new Incoming();
        incoming.setMessage(message);
        incoming.setReceivedDate(new Date());
        incoming.setOwner(user.getEmail());
        incoming.setSubject(user.getInSubject());
        return Mails.save(incoming);
    }

}
