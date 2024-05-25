package com.easysoftware.common.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 请求头拦截器.
 */
@Slf4j
public class RequestHeaderFilter implements Filter {
    /**
     * Referer pass domain.
     */
    private String allowDomains;

    /**
     * check header.
     *
     * @param servletRequest  The request.
     * @param servletResponse The response.
     * @param filterChain     The filterChain.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String referer = request.getHeader("Referer");
        String[] domains = allowDomains.split(";");
        boolean checkReferer = checkDomain(domains, referer);
        if (!checkReferer) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * check header.
     *
     * @param domains configdomain.
     * @param input   input.
     * @return boolean.
     */
    private boolean checkDomain(String[] domains, String input) {
        if (StringUtils.isBlank(input)) {
            return true;
        }
        int fromIndex;
        int endIndex;
        if (input.startsWith("http://")) {
            fromIndex = 7;
            endIndex = input.indexOf(":", fromIndex);
        } else {
            fromIndex = 8;
            endIndex = input.indexOf("/", fromIndex);
            endIndex = endIndex == -1 ? input.length() : endIndex;
        }
        String substring = input.substring(0, endIndex);
        for (String domain : domains) {
            if (substring.endsWith(domain)) {
                return true;
            }
        }
        return false;
    }

    /**
     * constructor.
     *
     * @param allowDomains configdomain.
     */
    public RequestHeaderFilter(String allowDomains) {
        this.allowDomains = allowDomains;
    }
}
