package com.khai.hnyp.webanalitics.controller.page;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	public static final String HELLO_PAGE_URL = "homePage";
	
	@RequestMapping("/")
	public String handle(Model model, HttpSession session) {
		model.addAttribute("currentTime", new Date());
		model.addAttribute("sessionId", session.getId());
		return HELLO_PAGE_URL;
	}
}
