package com.dndtool.server.security;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping(value = "/challenge"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public void beginLogin(HttpSession session) {
        System.out.println(session.getAttribute("srpSession"));
    }
}
