package com.rvj.app.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.entity.enums.UserLevel;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByUserName(String userName);
	
	public Optional<User> findByIdAndUserLevel(Long id, UserLevel level);
	
	public Optional<User> findByUserNameAndPassword(String userName, String password);
	
	@Query("select usr.userLevel from User usr where usr.userName = ?1")
	public UserLevel getUserLevel(String userName);
	
}
