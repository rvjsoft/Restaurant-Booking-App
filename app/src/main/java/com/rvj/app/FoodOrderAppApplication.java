package com.rvj.app;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rvj.app.dataaccess.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.rvj.app.dataaccess"})
@EntityScan(basePackages = {"com.rvj.app.foodorder.entity"} )
public class FoodOrderAppApplication implements CommandLineRunner{
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	UserRepository repo;
	
	public static void main(String[] args) {
		SpringApplication.run(FoodOrderAppApplication.class, args);
	}

	@RequestMapping("/app")
	public String foo() {
		return "I am RVJ";
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		System.out.println(repo);
//		System.out.println(em);
//		User user = new User();
//		user.setUserName("admin");
//		user.setPassword("NoPassword");
//		user.setUserLevel(UserLevel.CUSTOMER);
//		em.persist(user);
	}
}
