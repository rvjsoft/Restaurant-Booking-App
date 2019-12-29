package com.rvj.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findByUserName(String userName);
	
}
