package com.laboratory.demo.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {
    public static Cookie create(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(24*60*60);
        return cookie;
    }
}
