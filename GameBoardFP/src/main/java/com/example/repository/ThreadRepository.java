package com.example.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Thread;

import jakarta.transaction.Transactional;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, String> {
	
	//SELECT
	
	//タイトル・ゲーム検索
	@Query(value = "SELECT * FROM thread where title like %?1% OR game like %?2%", nativeQuery = true)
	public List<Thread> findByThread(String title,String game);
	
	//ジャンルから検索
	@Query(value = "SELECT * FROM thread where genre = ?1", nativeQuery = true)
	public List<Thread> findByThread_genre(String genre);
	
	//スレッド名存在チェック
	@Query(value = "SELECT * FROM thread where title = ?1", nativeQuery = true)
	public List<Thread> findByTitle(String title);
	
	//スレッド情報検索用
	@Query(value = "SELECT * FROM thread where id = ?1", nativeQuery = true)
	public List<Thread> findByThread_info(int id);
	
	//INSERT

	//スレッド登録
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `thread`(`title`, `game`, `genre`, `user`, `date`) "
			+ "VALUES (?1,?2,?3,?4,cast(now() as datetime))", nativeQuery = true)
	public void insertByThread(String title, String game, String genre, String user);

	//UPDATE

	//DELETE
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `thread` WHERE id = ?1", nativeQuery = true)
	public void DeleteByThread(int id);
	
	
}