package com.example.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Game;

import jakarta.transaction.Transactional;

@Repository
public interface GameRepository extends JpaRepository<Game, String>{
	//SELECT

	//スレッド名存在チェック

	//INSERT

	//ゲーム登録
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `game`(`genre`, `title`, `date`) VALUES (?1,?2,cast(now() as datetime))", nativeQuery = true)
	public void insertByGame(String genre, String title);

	//UPDATE

	//DELETE
}
