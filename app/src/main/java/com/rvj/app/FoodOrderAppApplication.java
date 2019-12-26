package com.rvj.app;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvj.app.foodorder.entity.User;
import com.rvj.app.foodorder.entity.enums.UserLevel;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rvj.app"})
public class FoodOrderAppApplication implements CommandLineRunner{
	
	@PersistenceContext
	EntityManager em;
	
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
//		System.out.println(em);
//		User user = new User();
//		user.setUserName("admin");
//		user.setPassword("NoPassword");
//		user.setUserLevel(UserLevel.CUSTOMER);
//		em.persist(user);
	}
}
