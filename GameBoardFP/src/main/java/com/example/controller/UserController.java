package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.component.SecuritySession;
import com.example.entity.User;
import com.example.service.SignupService;

@Controller
public class UserController {

	//セッション情報
	@Autowired
	private SecuritySession securitySession;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	//Signupサービス
	@Autowired
	private SignupService signup;

	//ログインページ
	@GetMapping("/login")
	public ModelAndView Login(ModelAndView mav) {

		mav.setViewName("login/login");

		return mav;
	}
	
	@GetMapping("/answer")
	public ModelAndView Login_ans(ModelAndView mav) {
		
		String user = securitySession.getUsername();

		mav.addObject("UserName", user);

		mav.setViewName("login/answer");

		return mav;
	}

	//ログイン結果
	@PostMapping("/answer")
	public ModelAndView LoginAns(ModelAndView mav) {

		String u_name = securitySession.getUsername();

		mav.addObject("UserName", u_name);

		mav.setViewName("login/answer");
		return mav;
	}

	//新規登録ページ
	@GetMapping("/signup")
	public ModelAndView Signup(@ModelAttribute @Validated User users, ModelAndView mav) {

		mav.setViewName("login/signup");
		return mav;
	}

	//登録処理
	@PostMapping("/signup")
	public ModelAndView UserInsert(@ModelAttribute @Validated User users, BindingResult result,
			@RequestParam("name") String name,
			@RequestParam("pass") String pass,
			ModelAndView mav) {

		if (result.hasErrors()) {
			mav.addObject("error", "入力内容に誤りがあります");
		} else {

			//既存ユーザー確認
			if (signup.AddCheck(name)) {
				//成功
				//追加
				signup.Siginup(name, pass);
				mav.addObject("ans", "ユーザーを登録しました");
			} else {
				//失敗
				mav.addObject("ans", "すでに同名のユーザーが存在します");
			}
		}

		mav.setViewName("login/signup");
		return mav;
	}
}
