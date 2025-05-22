package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 10)
	private int id;

	@Size(min=2,max=15,message = "2～15桁で入力してください")
	@Column(length = 255)
	private String name;

	@Size(min=4,max=10,message = "4～10桁で入力してください")
	@Column(length = 255)
	private String pass;

	@Column(length = 10)
	private String role;
	
	@Column(length = 50)
	private String date;

}
