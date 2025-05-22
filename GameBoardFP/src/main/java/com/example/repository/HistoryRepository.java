package com.example.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.History;

import jakarta.transaction.Transactional;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {
	//SELECT

	//いいね履歴検索
	@Query(value = "SELECT * FROM good_history where user = ?1", nativeQuery = true)
	public List<History> findByHistory(String user);
	
	//いいね履歴ID検索
	@Query(value = "SELECT b_id FROM good_history where user = ?1 AND t_id=?2", nativeQuery = true)
	public List<Integer> findByLike(String user, int t_id);

	//INSERT

	//いいね履歴登録
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `good_history`(`b_id`,`t_id`, `t_title`, `t_game`, `t_genre`, `user`, `date`)"
			+ " VALUES (?1,?2,?3,?4,?5,?6,cast(now() as datetime))", nativeQuery = true)
	public void insertByHistory(int b_id, int t_id, String t_title, String t_game, String t_genre, String user);

	//UPDATE

	//DELETE
	//いいね履歴削除
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `good_history` WHERE b_id=?1 AND user=?2", nativeQuery = true)
	public void deleteByHistory(int b_id, String user);
	
	//スレッド削除によるいいね履歴削除
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `good_history` WHERE t_id = ?1", nativeQuery = true)
	public void DeleteHistoryByThreadLoss(int t_id);
}