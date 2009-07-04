package com.github.mailerific.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class PersistenceFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest arg0, final ServletResponse arg1,
            final FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(arg0, arg1);
        } finally {
            Persistence.close();
        }
    }

    @Override
    public void init(final FilterConfig arg0) throws ServletException {
    }

}
