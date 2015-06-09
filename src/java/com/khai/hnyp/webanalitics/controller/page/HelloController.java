package com.khai.hnyp.webanalitics.controller.page;

import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.service.AccountService;

@Controller
public class HelloController {
	
	public static final String HELLO_PAGE_URL = "homePage";
	
	public static final String SESSION_USER = "sessionUser";
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/")
	public String handle(Model model, HttpSession session) {
		
		if (session.getAttribute(SESSION_USER) != null) {
			return "redirect:" + "/apps";
		}
		
		model.addAttribute("currentTime", new Date());
		model.addAttribute("sessionId", session.getId());
			
//		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
//		Object dataSource = wac.getBean("dataSource");
			
		return HELLO_PAGE_URL;
	}
}
