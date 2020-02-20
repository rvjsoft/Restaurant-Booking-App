package com.rvj.app.foodorder.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.rvj.app.foodorder.entity.converters.UserLevelConverter;
import com.rvj.app.foodorder.entity.enums.UserLevel;

import lombok.Data;

@Data
@Entity
@Table(name = "user_login")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NaturalId
	@Column(name = "usr")
	private String userName;
	
	@Column(name = "pwd")
	private String password;
	
	@Column(name = "usr_level")
	@Convert(converter = UserLevelConverter.class)
	private UserLevel userLevel;
}
