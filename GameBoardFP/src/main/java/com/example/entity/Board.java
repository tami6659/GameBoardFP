package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 10)
	private int id;
	
	@Column(length = 10)
	private int t_id;
	
	@Column(length = 255)
	private String t_title;
	
	@Column(length = 255)
	private String t_game;
	
	@Column(length = 255)
	private String t_genre;
	
	@Column(length = 255)
	private String user;
	
	@Column(length = 10)
	private int res_id;
	
	@Column(length = 255)
	private String res;
	
	@Column(length = 255)
	private String image;
	
	@Column(length = 100)
	private int good;
	
	@Column(length = 50)
	private String date;
	
}
