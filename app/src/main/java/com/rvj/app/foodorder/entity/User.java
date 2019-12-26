package com.rvj.app.foodorder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.rvj.app.foodorder.entity.enums.UserLevel;

import lombok.Data;

@Data
@Entity
@Table(name = "user_login")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@Column(name = "")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "usr")
	private String userName;
	
	@Column(name = "pwd")
	private String password;
	
	@Column(name = "usr_level")
	private UserLevel userLevel;
}
