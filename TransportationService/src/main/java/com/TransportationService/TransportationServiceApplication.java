package com.TransportationService;

import com.TransportationService.entity.*;
import com.TransportationService.service.CabSerive;
import com.TransportationService.service.DriverCabOwnerService;
import com.TransportationService.service.DriverService;
import com.TransportationService.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransportationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportationServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(DriverService driverService, CabSerive cabSerive, UserService userService,
											   DriverCabOwnerService driverCabOwnerService) {
		return runner -> {
			System.out.println("Hello World");
			createDriver(driverService,cabSerive,userService,driverCabOwnerService);
		};
	}

	private void createDriver(DriverService driverService, CabSerive cabSerive, UserService userService, DriverCabOwnerService driverCabOwnerService) {
		//Create User
		User user = new User();
		user.setFirstName("James");
		user.setLastName("Doe");
		user.setEmail("james@doe.com");
		user.setPassword("123");
		user.setPhoneNumber("1234567890");
		user.setAddress("abc road");
		user.setRole(Role.DRIVER);

		Driver driver = new Driver();
		driver.setUser(user);

		driver.setDriversLicense("12121212");
		driver.setStartTime("12pm");
		driver.setEndTime("6pm");

		Driver d = driverService.addDriver(driver);

		Cab cab1 = new Cab();
		Cab cab2 = new Cab();

		cab1.setColor("Red");
		cab1.setModel("Maruti");
		cab1.setStatus(true);
		cab1.setRegisterNo("87891");
		cab1.setSeatingCapacity(4);

		cab2.setColor("Blue");
		cab2.setModel("Bmw");
		cab2.setStatus(true);
		cab2.setRegisterNo("12451");
		cab2.setSeatingCapacity(5);


		Cab c1 = cabSerive.addCab(cab1);
		Cab c2 = cabSerive.addCab(cab2);


		DriverCabOwner driverCabOwner1 = new DriverCabOwner();
		driverCabOwner1.setDriver(d);
		driverCabOwner1.setCab(c1);

		DriverCabOwner driverCabOwner2 = new DriverCabOwner();
		driverCabOwner2.setDriver(d);
		driverCabOwner2.setCab(c2);

		driver.addDriverCabOwner(driverCabOwner1);
		driver.addDriverCabOwner(driverCabOwner2);


		Driver savedDriver = driverService.findDriverById(d.getId());

		System.out.println("Saves driver : "+savedDriver);
	}


}
