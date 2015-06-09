package com.khai.hnyp.webanalitics.controller.page;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.khai.hnyp.webanalitics.controller.form.LoginForm;
import com.khai.hnyp.webanalitics.controller.form.RegisterForm;
import com.khai.hnyp.webanalitics.model.AccountModel;
import com.khai.hnyp.webanalitics.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	public static final Logger LOG = Logger.getLogger(AccountController.class);
	
	public static final String ACCOUNT_PAGES_PREFIX = "/page/account";
	
	public static final String REGISTER_PAGE = ACCOUNT_PAGES_PREFIX + "/register";
	
	public static final String HOME_URL = "/";
	
	public static final String SESSION_USER_KEY = "sessionUser";
	
	public static final String REGISTER_ERRORS_ATTRIBUTE = "registerErrors";
	
	public static final String REGISTER_FORM_ATTRIBUTE = "registerForm";
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model, HttpSession session) {
		return REGISTER_PAGE;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(RegisterForm form, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		LOG.info("register attempt : " + form.getLogin());
		
		Map<String, String> errorMap = validateRegisterForm(form);
		if (!errorMap.isEmpty()) {
			redirectAttributes.addFlashAttribute(REGISTER_ERRORS_ATTRIBUTE, errorMap);
			redirectAttributes.addFlashAttribute(REGISTER_FORM_ATTRIBUTE , form);
			return "redirect:" + "/account/register";
		}
		
		AccountModel account = populateModel(form);
		accountService.create(account);
		
		//all ok, auto login
		LoginForm loginForm = populateAutoLoginForm(account);
		return loginPost(loginForm, null, model, session);
	}
	
	private LoginForm populateAutoLoginForm(AccountModel model) {
		LoginForm form = new LoginForm();
		form.setLogin(model.getLogin());
		form.setPassword(model.getPassword());
		return form;
	}
	
	private AccountModel populateModel(RegisterForm form) {
		AccountModel model = new AccountModel();
		model.setEmail(form.getEmail());
		model.setLogin(form.getLogin());
		model.setPassword(form.getPassword());
		return model;
	}
	
	private Map<String, String> validateRegisterForm(RegisterForm form) {
		Map<String, String> errorMap = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(form.getLogin())) {
			if (userAlreadyExists(form.getLogin())) {
				errorMap.put("login", "already exist");
			}
		} else {
			errorMap.put("login", "empty");
		}
		if (StringUtils.isNotEmpty(form.getPassword())) {
			if (!form.getPassword().equals(form.getRePassword())) {
				errorMap.put("password", "password mismatch");
			}
		} else {
			errorMap.put("password", "empty");
		}
		return errorMap;
	}
	
	private boolean userAlreadyExists(String login) {
		return accountService.getByLogin(login) != null;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(LoginForm form, String referrer, Model model, HttpSession session) {
		AccountModel account = accountService.getByLogin(form.getLogin());
		if (account != null && accountService.checkPasswordMatches(account, form.getPassword())) {
			session.setAttribute(SESSION_USER_KEY, account);
		}
		if (StringUtils.isNotEmpty(referrer)) {
			return "redirect:" + referrer;
		} 
		
		return "redirect:" + HOME_URL;	
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:" + HOME_URL;
	}
}
