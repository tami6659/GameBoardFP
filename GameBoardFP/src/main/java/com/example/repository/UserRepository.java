package com.example.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	//SELECT

	//ユーザー名前検索
	@Query(value = "SELECT * FROM user where name = ?1", nativeQuery = true)
	public List<User> findByUserName(String name);

	//INSERT
	
	//ユーザー登録
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `user`(`name`, `pass`, `role`, `date`) "
			+ "VALUES (?1,?2,'USER',cast(now() as datetime))", nativeQuery = true)
	public void insertByUser(String name, String pass);
	
	//ログイン用
	User findByName(String name);
}