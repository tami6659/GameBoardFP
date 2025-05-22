package com.example.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Board;

import jakarta.transaction.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {
	//SELECT
	//スレッド検索
	@Query(value = "SELECT * FROM board where t_id = ?1 LIMIT ?2 OFFSET ?3", nativeQuery = true)
	public List<Board> findByBoard(int t_id,int limit,int offset);

	//スレッド書き込み総数
	@Query(value = "SELECT COUNT(*) as count FROM board where t_id = ?1", nativeQuery = true)
	public List<Integer> findByBoardCount(int t_id);


	//ユーザー書き込み履歴検索
	@Query(value = "SELECT * FROM `board` WHERE user=?1 GROUP BY t_id", nativeQuery = true)
	public List<Board> findByW_history(String user);

	//ユーザー書き込み件数
	@Query(value = "SELECT COUNT(user) FROM `board` WHERE user=?1 GROUP BY t_id", nativeQuery = true)
	public List<Integer> findByW_h_count(String user);

	//いいね総数
	@Query(value = "SELECT SUM(good) FROM `board` WHERE user=?1 GROUP BY t_id", nativeQuery = true)
	public List<Integer> findByAllGood(String user);

	//画像名かぶりチェック
	@Query(value = "SELECT * FROM board where image = ?1", nativeQuery = true)
	public List<Board> findByImage(String image);

	//INSERT

	//書き込み登録
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `board`"
			+ "(`t_id`, `t_title`, `t_game`, `t_genre`, `user`,`res_id`, `res`, `image`, `good`,`date`)"
			+ " VALUES (?1,?2,?3,?4,?5,?6,?7,?8,0,cast(now() as datetime))", nativeQuery = true)
	public void insertByWrite(int t_id, String t_title, String t_game, String t_genre, String user, int res_id,
			String res, String image);

	//UPDATE

	//いいね追加
	@Modifying
	@Transactional
	@Query(value = "UPDATE `board` SET `good`=good+1 WHERE id=?1", nativeQuery = true)
	public void updateByGood(int id);

	//いいね解除
	@Modifying
	@Transactional
	@Query(value = "UPDATE `board` SET `good`=good-1 WHERE id=?1", nativeQuery = true)
	public void updateByDel_Good(int id);

	//DELETE
	//スレッド削除による全書き込み削除
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `board` WHERE t_id = ?1", nativeQuery = true)
	public void DeleteWriteByThreadLoss(int t_id);

}