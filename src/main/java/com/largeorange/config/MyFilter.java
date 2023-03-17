package com.largeorange.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * @author 大橙子
 * @version 1.0
 * date:2023/3/15
 * @since 1.0
 */
@WebFilter(filterName = "myFilter",urlPatterns = "/*")
@Slf4j
public class MyFilter implements Filter {
    /**
     * 路径匹配
     */
    public static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：" + requestURI);
        String[] urls = {
                "/user/login",
                "/web/login.html",
                "/css/**",
                "/js/**",
                "/image/**",
        };
        if (getUrl(urls, requestURI)) {
            filterChain.doFilter(request, response);
            log.info("合法请求通过！");
            return;
        }
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            filterChain.doFilter(request, response);
            log.info("已登录请求通过！");
            return;
        }
        log.info("未登录！");
        response.sendRedirect("/web/login.html");
    }

    public static boolean getUrl(String[] urls, String url) {
        for (String s : urls) {
            boolean match = antPathMatcher.match(s, url);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
