package com.nearbyshops;

import com.nearbyshops.models.Role;
import com.nearbyshops.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NearbyshopsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NearbyshopsApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(RoleRepository roleRepository) {
//		return args -> {
//			Role adminRole = roleRepository.findByRole("ADMIN");
//			if (adminRole == null) {
//				roleRepository.save(new Role("ADMIN"));
//			}
//
//			Role userRole = roleRepository.findByRole("USER");
//			if (userRole == null) {
//				roleRepository.save(new Role("USER"));
//			}
//		};
//
//	}
}

