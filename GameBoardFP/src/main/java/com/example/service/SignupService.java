package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class SignupService {
	
	@Autowired
	private UserRepository re_user;//Userのリポジトリ

	@Autowired
	protected PasswordEncoder passwordEncoder;

	//既存ユーザー確認
	public boolean AddCheck(String name) {

		List<User> user = re_user.findByUserName(name);

		if (user.size() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void Siginup(String name, String password) {
		//ハッシュ化
		String pass = passwordEncoder.encode(password);

		//ユーザー登録
		re_user.insertByUser(name, pass);
	}
}
