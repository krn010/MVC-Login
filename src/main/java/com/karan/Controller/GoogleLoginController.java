package com.karan.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.karan.Model.Register;
import com.karan.SocialLogin.CallBack;

@Controller
public class GoogleLoginController {
	
	@Autowired
	CallBack googleCallBack;

	@RequestMapping("googleLogin")
	public RedirectView googleLogin(HttpServletResponse response) {
		
		System.out.println("Google Login Controller");
		
		return new RedirectView("https://accounts.google.com/o/oauth2/auth?scope=profile&redirect_uri=http://localhost:8080/MVCLogin2/callBack&response_type=code&client_id=766044093056-v3f0cums0r9rhjgmqeblgjnbupnskkm2.apps.googleusercontent.com&approval_prompt=force");
	}
	
	@RequestMapping(value = "callBack")
	public String getGoogleUser(HttpServletRequest request) {
	
		System.out.println("getGoogleUser()");
		String code  = request.getParameter("code");
		Register user = googleCallBack.getGoogleCallBack(code);
		HttpSession session = request.getSession(true);
		session.setAttribute("userObject", user);
		session.setAttribute("loginuser", user);
		return "welcome";
	}
}