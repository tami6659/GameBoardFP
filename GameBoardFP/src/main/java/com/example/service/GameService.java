package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository re_game;//Gameのリポジトリ
	
	//ゲーム登録
	public void GameInsert(String genre, String game) {
		re_game.insertByGame(genre, game);
	}
}
