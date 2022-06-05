package com.rvj.app;

import com.rvj.app.dataaccess.UserRepository;
import com.rvj.app.foodorder.services.UserRegistrationService;
import com.rvj.app.security.CustomUserDetails;
import com.rvj.app.security.CustomUserDetailsManager;
import org.apache.catalina.Context;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = {"com.rvj.app.dataaccess"})
@EntityScan(basePackages = {"com.rvj.app.foodorder.entity"} )
@EnableConfigurationProperties
public class FoodOrderAppApplication implements CommandLineRunner{

	@Autowired
	UserRegistrationService userRegistrationService;

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderAppApplication.class, args);
	}
	
	@Bean
	public ServletWebServerFactory servletContainer() {
	    return new TomcatServletWebServerFactory() {
	        @Override
	        protected void postProcessContext(Context context) {
	            Rfc6265CookieProcessor rfc6265CookieProcessor = new Rfc6265CookieProcessor();
	            rfc6265CookieProcessor.setSameSiteCookies("None");
	            context.setCookieProcessor(rfc6265CookieProcessor);
	        }
	    };
	}

//	@Bean
	UserDetailsManager users(CustomUserDetailsManager customUserDetailsManager) {
		return customUserDetailsManager;

//		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//		return users;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("localhost")
						.allowCredentials(true)
						.allowedHeaders("Content-Type", "Accept", "X-Requested-With", "remember-me", "Authorization", "x-usr-level")
						.allowedMethods("POST", "GET", "OPTIONS", "DELETE")
						.allowedOriginPatterns("*")
						.maxAge(3600)
						.exposedHeaders("x-usr-level");
			}
		};
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
