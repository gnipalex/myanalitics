package com.khai.hnyp.webanalitics.controller.page;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.khai.hnyp.webanalitics.controller.form.RegisterForm;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	public static final Logger LOG = Logger.getLogger(AccountController.class);
	
	public static final String ACCOUNT_PAGES_PREFIX = "/page/account";
	
	public static final String REGISTER_PAGE = ACCOUNT_PAGES_PREFIX + "/register";
	
	public static final String HOME_URL = "/";
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model) {
		return REGISTER_PAGE;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(RegisterForm form, Model model) {
		LOG.info("register attempt : " + form.getLogin() + ", " + form.getPassword());
		return "redirect:" + HOME_URL;
	}
}
