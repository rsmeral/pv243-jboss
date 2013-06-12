package cz.muni.fi.pv243.et.security;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.picketlink.Identity;

import java.io.IOException;

@WebFilter(urlPatterns = "/secured/*")
public class SecurityFilter implements Filter {

    @Inject
    private Identity identity;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (!this.identity.isLoggedIn()) {
            HttpServletRequest request = (HttpServletRequest) req;

            if (request.getRequestURI().startsWith(request.getContextPath() + "/secured")) {
                ((HttpServletResponse) response).sendRedirect(request.getContextPath() + "/login.jsf");
                //req.getRequestDispatcher("/login.jsf").forward(req, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {

    }

}
