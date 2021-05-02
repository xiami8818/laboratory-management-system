package com.laboratory.demo.controller;

import com.laboratory.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.login(request, response);
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/login");
    }

    @RequestMapping("/visitor")
    public void visitor(HttpServletResponse response) throws IOException {
        userService.visitor(response);
    }

    @RequestMapping("/showUsers")
    public Map<String, Object> showUsers(int page, int limit) {
        return userService.getUsers(page, limit);
    }

    @RequestMapping("/createUser")
    public Map<String, Object> createUser(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        String no = request.getParameter("no");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        if(userService.isUserExist(no)) {
            res.put("msg", "用户已存在");
            return res;
        }
        userService.createUser(no, password, name);
        res.put("msg", "success");
        return res;
    }

    @RequestMapping("/deleteUser")
    public Map<String, Object> deleteUser(int id) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        userService.deleteUserById(id);
        res.put("msg", "删除成功！");
        return res;
    }

    @RequestMapping("/updateUser")
    public Map<String, Object> updateUser(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        if (no == null || name == null || id==null) {
            res.put("msg", "修改失败！");
            return res;
        }
        userService.updateUser(Integer.parseInt(id), no, name);
        res.put("msg", "success");
        return res;
    }

    @PostMapping("/changePassword")
    public Map<String, String> changePassword(HttpServletRequest request, HttpServletResponse response) {
        return userService.updatePassword(request, response);
    }
}
