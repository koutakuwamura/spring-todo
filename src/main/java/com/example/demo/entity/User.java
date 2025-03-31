package com.example.demo.entity;

import jakarta.persistence.Entity;//@Entity アノテーションを使うためのインポート
import jakarta.persistence.GeneratedValue;
//@GeneratedValue アノテーションを使うためのインポート
//主キー（@Id で指定する項目）の値を自動生成するために使う
import jakarta.persistence.GenerationType;
//GenerationType は @GeneratedValue で使う戦略（strategy）を指定するための Enum（列挙型）
import jakarta.persistence.Id;//エンティティの主キー（ユニークなID）を設定するために使う
import jakarta.persistence.Table;//エンティティをデータベースのどのテーブルと結びつけるかを指定できる

// Users エンティティクラス
@Entity
@Table(name="users") // データベースの `users` テーブルと対応
public class User {
    
    @Id // 主キーを指定
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id; // 顧客ID
    
    private String email;   // メールアドレス
    private String name;    // 名前
    private String password; //パスワード
    
    // デフォルトコンストラクタ
    public User() {
    }
    
    
    
 // idを除外したコンストラクタ（自動採番されるため）
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    
    // ゲッターメソッド（フィールドの値を取得する）
    public Integer getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPassword() {
        return password;
    }
}
