package com.TransportationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransportationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportationServiceApplication.class, args);
	}
/*
	@Bean
	public CommandLineRunner commandLineRunner(DriverService driverService, CabSerive cabSerive,
											   UserService userService) {
		return runner -> {
			System.out.println("Hello World");
			//createDriver(driverService,cabSerive,userService);
			addCab(driverService,cabSerive,userService);
			//addDriverCabOwners(driverService,cabSerive,userService);
			//findDriversCab(driverService,cabSerive,userService);
		};
	}

	private void findDriversCab(DriverService driverService, CabSerive cabSerive, UserService userService) throws Exception {
		int driverId = 1;
		Driver driver = driverService.findDriverById(driverId);
		System.out.println("I am here");
//		System.out.println("Driver Id: " + driverId+" :"+driver);
	}

	private void addCab(DriverService driverService, CabSerive cabSerive, UserService userService) throws Exception {
		Cab cab1 = new Cab();
		Cab cab2 = new Cab();
		int driverId = 1;
		User user = userService.findUserById(driverId);
		userService.addUser(user);
		System.out.println("User : "+user);
		cab1.setColor("Red");
		cab1.setModel("Maruti");
		cab1.setStatus(true);
		cab1.setRegisterNo("87891");
		cab1.setSeatingCapacity(4);
		cab1.setUser(user);

		cab2.setColor("Blue");
		cab2.setModel("Bmw");
		cab2.setStatus(true);
		cab2.setRegisterNo("12451");
		cab2.setSeatingCapacity(5);
		cab2.setUser(user);
		Cab c1 = cabSerive.addCab(cab1);
		Cab c2 = cabSerive.addCab(cab2);

		System.out.println("Cab1 :"+c1);
		System.out.println("Cab2 :"+c2);

		System.out.println("user : "+user);
	}

	private void createDriver(DriverService driverService, CabSerive cabSerive, UserService userService) throws Exception {
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

		System.out.println("Saves driver : "+d);
	}
 */
}
