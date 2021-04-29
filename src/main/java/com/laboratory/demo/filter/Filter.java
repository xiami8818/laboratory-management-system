package com.laboratory.demo.filter;

import com.laboratory.demo.utils.JwtUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws IOException {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies == null) {
            response.sendRedirect("/login");
            return false;
        }
        for(Cookie cookie: cookies) {
            if(cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        if(token == null) {
            response.sendRedirect("/login");
            return false;
        }
        if(!JwtUtils.checkToken(token)) {
            response.sendRedirect("/login");
            return false;
        }
        String jwt = JwtUtils.updateToken(token);
        Cookie resCookie = new Cookie("token", jwt);
        resCookie.setPath("/");
        resCookie.setHttpOnly(true);
        response.addCookie(resCookie);
        return true;
    }
}
