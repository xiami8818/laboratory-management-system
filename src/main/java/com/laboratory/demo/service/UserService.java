package com.laboratory.demo.service;

import com.laboratory.demo.entry.User;
import com.laboratory.demo.mapper.UserMapper;
import com.laboratory.demo.utils.CookieUtil;
import com.laboratory.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String no = request.getParameter("no");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");
        Date date = new Date();
        userMapper.updateTime(date, no);
        List<User> users = null;
        if(identity == null || no == null || password == null) {
            response.sendRedirect("/login");
            return ;
        }
        if(identity.equals("admin")) {
            users = userMapper.getAdmin(no, password);
        }else {
            users = userMapper.getUser(no, password);
        }
        if(users.isEmpty()) {
            response.sendRedirect("/login");
            return ;
        }
        User user = users.get(0);
        String token = JwtUtils.createToken(Integer.toString(user.getId()), identity);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

    public String getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        for(Cookie cookie: cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        return JwtUtils.getClaimByName(token, "id").asString();
    }

    public User getUserById(String id) {
        List<User> users = userMapper.getUserById(Integer.parseInt(id));
        return users.get(0);
    }

    public User getAdminById(String id) {
        List<User> users = userMapper.getAdminById(Integer.parseInt(id));
        return users.get(0);
    }

    public String getIdentity(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        for(Cookie cookie: cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        return JwtUtils.getClaimByName(token, "identity").asString();
    }

    public void visitor(HttpServletResponse response) throws IOException {
        String token = JwtUtils.createToken("1", "visitor");
        Cookie cookie = CookieUtil.create("token", token);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

    public Map<String, Object> getUsers(int page, int limit) {
        Map<String, Object> res = new HashMap<>();
        res.put("code", "0");
        res.put("msg", "$success");
        List<Map<String, String>> userList = new ArrayList<>();
        int start = (page - 1)*limit;
        List<User> users = userMapper.getUsers(start, limit);
        for(User user: users) {
            Map<String, String> map = new HashMap<>();
            map.put("id", Integer.toString(user.getId()));
            map.put("no", user.getNo());
            map.put("name", user.getName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String lasttime = dateFormat.format(user.getLasttime());
            map.put("lasttime", lasttime);
            userList.add(map);
        }
        res.put("data", userList);
        return res;
    }

    public boolean isUserExist(String no) {
        List<User> users = userMapper.getUserByNo(no);
        return !users.isEmpty();
    }

    public void createUser(String no, String password, String name) {
        Date nowTime = new Date();
        userMapper.insertUser(no, password, name, nowTime);
    }

    public void deleteUserById(int id) {
        userMapper.deleteUserById(id);
    }

    public void updateUser(int id, String no, String name) {
        userMapper.updateUser(no, name, id);
    }

    public Map<String, String> updatePassword(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(getUserId(request));
        Map<String, String> res = new HashMap<>();
        String new_pass = request.getParameter("new_pass");
        String old_pass = request.getParameter("old_pass");
        String password;
        String identity = getIdentity(request);
        if(identity.equals("admin")) {
            password = userMapper.selectAdminPassById(id);
        } else {
            password = userMapper.selectUserPassById(id);
        }
        if(new_pass == null || old_pass == null) {
            res.put("status", "failed");
            res.put("msg", "两次密码输入不一致！");
            return res;
        }
        if(password.equals(old_pass)) {
            res.put("status", "failed");
            res.put("msg", "原密码错误！");
            return res;
        }
        if(identity.equals("admin")) {
            userMapper.updateAdminPass(id, new_pass);
        } else {
            userMapper.updateUserPass(id, new_pass);
        }
        res.put("status", "success");
        res.put("msg", "密码修改成功!");
        return res;
    }
}
