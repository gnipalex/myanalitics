package com.khai.hnyp.webanalitics.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @author Roman Rastiehaiev
 *         Created on 06.06.2015.
 */
@Controller
public class FakeController {

    public static final String APPLICATIONS_PAGE = "apps";

    @RequestMapping("/apps")
    public String main() {
        return APPLICATIONS_PAGE;
    }

//    @RequestMapping(value = "/account/login", method = RequestMethod.POST)
//    public String login(HttpSession session) {
//        session.setAttribute("sessionUser", "Rastiehaiev");
//        return "redirect:/" + APPLICATIONS_PAGE;
//    }
//
//    @RequestMapping("/account/logout")
//    public String logout(HttpSession session) {
//        session.removeAttribute("sessionUser");
//        return "redirect:/";
//    }

}
