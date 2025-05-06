package com.example.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Genre;

import jakarta.transaction.Transactional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
	
	//SELECT
	//ユーザー名前検索
		@Query(value = "SELECT * FROM genre where genre = ?1", nativeQuery = true)
		public List<Genre> findByGenre(String genre);

	//INSERT

	//ジャンル追加
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO`genre`(`genre`,`date`) VALUES (?1,cast(now() as datetime))", nativeQuery = true)
	public void insertByGenre(String genre);


	//UPDATE

	//DELETE
}
