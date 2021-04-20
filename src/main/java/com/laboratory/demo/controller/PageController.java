package com.laboratory.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PageController {
    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("name","虾米");
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
