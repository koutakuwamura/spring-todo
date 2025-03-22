package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Users;

public class UserController {
	@Autowired
	HttpSession session;

	@Autowired
	Users users;


	// ログイン画面を表示
		@GetMapping({ "/users/new"})
		public String new(
				@RequestParam(name = "error", defaultValue = "") String error,
				Model model) {
			// セッション情報を全てクリアする
			session.invalidate();
			// エラーパラメータのチェック
			if (error.equals("notLoggedIn")) {
				model.addAttribute("message", "ログインしてください");
			}

			return "users.new";
		}

}
