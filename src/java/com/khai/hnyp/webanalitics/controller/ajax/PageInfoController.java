package com.khai.hnyp.webanalitics.controller.ajax;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.khai.hnyp.webanalitics.controller.page.AnaliticsForm;

@Controller
public class PageInfoController {
	
	@RequestMapping("/ajax/pageInfo")
	public void pageInfo(AnaliticsForm form, HttpSession session) {
		System.out.println("###Request###");
		System.out.println("#url=" + form.getLocation());
		System.out.println("#referrer=" + form.getReferrer());
		System.out.println("#time=" + form.getTime());
		System.out.println("#sessionId=" + form.getSessionId());
		System.out.println("#############");
	}
}
