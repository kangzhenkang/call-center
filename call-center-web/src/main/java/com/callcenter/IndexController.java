
package com.callcenter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页
 * @author wangyue-ds6
 * @since 2014-02-28
 */
@Controller
@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
public class IndexController{
	
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String index(HttpServletRequest request,Model view){
	    view.addAttribute("contextPath", request.getContextPath());
		return "mainFrame/frame";
	}
}
