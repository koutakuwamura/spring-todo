package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope

public class Account {
	//フィールド
	private String email;
	private String password;

	//コンストラクタ
	public Account() {

	}

	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}

	//ゲッター
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	//セッター
	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
