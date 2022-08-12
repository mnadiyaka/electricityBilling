package com.billing.webapp;

import com.billing.webapp.model.entity.User;
import com.billing.webapp.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

//	private static void parseJSON_Example(UserRepository collection){
//		String json = "{ 'name' : 'lokesh' , " +
//				"'website' : 'howtodoinjava.com' , " +
//				"'address' : { 'addressLine1' : 'Some address' , " +
//				"'addressLine2' : 'Karol Bagh' , " +
//				"'addressLine3' : 'New Delhi, India'}" +
//				"}";
//
//		User user = (User)JSON.parse(json);
//
//		collection.insert(user);
//	}

	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}
