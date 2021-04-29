package com.laboratory.demo.controller;

import com.laboratory.demo.entry.Lab;
import com.laboratory.demo.entry.User;
import com.laboratory.demo.service.LabSerivce;
import com.laboratory.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    UserService userService;

    @Autowired
    LabSerivce labSerivce;

    @RequestMapping({"/", "/index"})
    public String index(HttpServletRequest request, Model model) {
        String userId = userService.getUserId(request);
        String identity = userService.getIdentity(request);
        User user = null;
        if(identity.equals("staff")) {
            user = userService.getUserById(userId);
        }else if(identity.equals("admin")){
            user = userService.getAdminById(userId);
        }
        List<Lab> labs = labSerivce.getAllLabs();
        model.addAttribute("labs", labs);
        model.addAttribute("user",user);
        model.addAttribute("identity", identity);
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/term")
    public String term(HttpServletRequest request, Model model) {
        String userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String identity = userService.getIdentity(request);
        List<Lab> labs = labSerivce.getAllLabs();
        model.addAttribute("labs", labs);
        model.addAttribute("user",user);
        model.addAttribute("identity", identity);
        return "term";
    }

    @RequestMapping("/lab")
    public String lab(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String userId = userService.getUserId(request);
        User user = userService.getUserById(userId);
        String identity = userService.getIdentity(request);
        List<Lab> labs = labSerivce.getAllLabs();
        model.addAttribute("labs", labs);
        model.addAttribute("user",user);
        model.addAttribute("identity", identity);
        return "lab";
    }

    @RequestMapping("/userManager")
    public String userManager(HttpServletRequest request, Model model) {
        String userId = userService.getUserId(request);
        String identity = userService.getIdentity(request);
        User user = null;
        if(identity.equals("admin")) {
            user = userService.getAdminById(userId);
        }else if(identity.equals("staff")) {
            user = userService.getUserById(userId);
        }
        List<Lab> labs = labSerivce.getAllLabs();
        model.addAttribute("labs", labs);
        model.addAttribute("user",user);
        model.addAttribute("identity", identity);
        return "user";
    }

}
