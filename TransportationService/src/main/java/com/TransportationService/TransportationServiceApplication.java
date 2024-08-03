package com.TransportationService;

import com.TransportationService.dto.request.AdminDto;
import com.TransportationService.dto.request.UserDetailsDto;
import com.TransportationService.entity.Admin;
import com.TransportationService.entity.Role;
import com.TransportationService.entity.User;
import com.TransportationService.repository.AdminRepository;
import com.TransportationService.repository.UserRepository;
import com.TransportationService.service.AdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
public class TransportationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportationServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AdminService adminService) {
		return runner -> {
			AdminInitializer(adminService);
		};
	}

	private void AdminInitializer(AdminService adminService) throws Exception {
		if(!adminService.adminExists()){
			UserDetailsDto adminUser = new UserDetailsDto();
			adminUser.setAddress("mumbai");
			adminUser.setEmail("admin@gmail.com");
			adminUser.setFirstName("admin");
			adminUser.setLastName("admin");
			adminUser.setPassword("admin");
			adminUser.setPhoneNumber("1234567890");


			AdminDto adminDto = new AdminDto();
			adminDto.setUser(adminUser);

			adminService.addAdmin(adminDto);
		}
	}
}
