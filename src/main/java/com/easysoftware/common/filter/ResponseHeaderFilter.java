package com.easysoftware.common.filter;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;

/**
 * 响应请求头设置过滤器.
 */
@Slf4j
public class ResponseHeaderFilter implements Filter {
    /**
     * Set header.
     *
     * @param servletRequest The request.
     * @param servletResponse The response.
     * @param filterChain The filterChain.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.getSession(false);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
