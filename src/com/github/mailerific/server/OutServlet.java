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

import com.github.mailerific.client.Outgoing;
import com.github.mailerific.client.UserAccount;

public class OutServlet extends SecureServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(OutServlet.class
            .getName());

    @Override
    protected void doSecureAction(final HttpServletRequest request,
            final HttpServletResponse response, final UserAccount user)
            throws ServletException, IOException {
        try {
            String path = request.getPathInfo();
            if (path != null && !path.trim().isEmpty() && path.length() > 1) {
                String[] paths = UrlUtils.splitPath(path);
                String recipient = paths[0];
                String message = UrlUtils.appendPathWithQueryString(paths[1],
                        request);
                Outgoing out = saveMessage(user, recipient, message);
                try {
                    Mails.send(out);
                } catch (AddressException e) {
                    handleMailError(request, response);
                    return;
                } catch (MessagingException e) {
                    handleMailError(request, response);
                    return;
                }
                request.getRequestDispatcher("/WEB-INF/success-out.jsp")
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

    private Outgoing saveMessage(final UserAccount user,
            final String recipient, final String message) {
        Outgoing outgoing = new Outgoing();
        outgoing.setMessage(message);
        outgoing.setReceivedDate(new Date());
        outgoing.setSender(user.getEmail());
        outgoing.setSubject(user.getOutSubject());
        outgoing.setRecipient(recipient);
        if (user.isOutIncludeSig()) {
            outgoing.setSignature(user.getOutSignature());
        }
        return Mails.save(outgoing);
    }

}
