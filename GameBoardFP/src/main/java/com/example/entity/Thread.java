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
@Table(name = "thread")
public class Thread {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 10)
	private int id;
	
	@Column(length = 255)
	private String title;
	
	@Column(length = 255)
	private String game;
	
	@Column(length = 255)
	private String genre;
	
	@Column(length = 255)
	private String user;
	
	@Column(length = 50)
	private String date;
	
}
