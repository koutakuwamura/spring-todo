package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;

@Controller
public class UserController {
	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	// ログイン画面を表示
	@GetMapping( "/login" )
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();
		//エラーパラメータのチェック
		if(error.equals("notLoggedIn")) {
			model.addAttribute("message","ログインしてください");
		}
		return "login";
	}
	
	//ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam("email")String email,
			@RequestParam("password")String password,
			Model model
			) {
		//emailとpasswordが空の時にエラーとする
		if(email == null || email.length()==0 || password == null || password.length()==0) {
			model.addAttribute("message","メールアドレスとパスワードを入力してください");
			return "login";
		}
		
	//セッション管理されたアカウント情報に名前をセット
		account.setEmail(email);
		
		//「/tasks」へのリダイレクト
		return "redirect:/tasks";
	}
	
	
}
