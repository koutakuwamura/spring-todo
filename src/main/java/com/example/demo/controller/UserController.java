package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;//entityはオブジェく化しない
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	@Autowired
	UserRepository userRepository;

	// ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index(
			@RequestParam(name = "error", defaultValue = "") String error,
			Model model) {
		// セッション情報を全てクリアする
		session.invalidate();
		//エラーパラメータのチェック
		if (error.equals("notLoggedIn")) {
			model.addAttribute("message", "ログインしてください");
		}
		return "login";
	}

	//ログインを実行
	@PostMapping("/login")
	public String login(@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model,
			HttpSession session) {
		// 入力チェック
		if (email.isEmpty() || password.isEmpty()) {
			model.addAttribute("message", "メールアドレスとパスワードを入力してください");
			return "login";
		}

		// ユーザー認証
		List<User> users = userRepository.findByEmailAndPassword(email, password);
		if (users.isEmpty()) {
			model.addAttribute("message", "メールアドレスまたはパスワードが間違っています");
			return "login";
		}

		// ログイン成功処理
		session.setAttribute("userEmail", email);
		return "redirect:/tasks";
	}

	//新規登録画面の表示
	@GetMapping("/users/new")
	public String creat() {
		return "createUser";
		
	}

	//新規登録処理
	@PostMapping("/users/add")
	public String add(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "password", defaultValue = "") String password,
			 @RequestParam(name = "password_confirm", defaultValue = "") String passwordConfirm, 
			Model model) {
//		if(name.isEmpty() ||email.isEmpty() || password.isEmpty()|| password_confirm.isEmpty()) {
//			model.addAttribute("message", "空白があります。入力してください。");
//			return "createUser";
//		}
//		else if (password != password_confirm) {
//			model.addAttribute("message", "パスワードが一致しません");
//			return "createUser";
//		} else {
			//Userオブジェクトの生成
		User user = new User(email, name, password);

			//Userテーブルへの反映
			userRepository.save(user);
			account.setName(name);
		//}
		//「/tasks」にGETでリクエストしなおす
		return "redirect:/tasks";
	}

}
