package com.user.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entity.User;
import com.user.service.models.Car;
import com.user.service.models.Motorcycle;
import com.user.service.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.getAllUser();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		User user = userService.getUserById(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		User newUser = userService.save(user);
		return ResponseEntity.ok(newUser);

	}

	// RestTemplate
		@CircuitBreaker(name="carsCB", fallbackMethod = "fallBackGetCars")
		@GetMapping("/cars/{userId}")
		public ResponseEntity<List<Car>> listCars(@PathVariable("userId") int id) {
			User user = userService.getUserById(id);
			if (user == null) {
				return ResponseEntity.notFound().build();
			}
			List<Car> cars = userService.getCars(id);
			return ResponseEntity.ok(cars);
		}

		// RestTemplate
		@CircuitBreaker(name="motorcyclesCB", fallbackMethod = "fallBackGetMotorcycles")
		@GetMapping("/motorcycles/{userId}")
		public ResponseEntity<List<Motorcycle>> listMotorcycles(@PathVariable("userId") int id) {
			User user = userService.getUserById(id);
			if (user == null) {
				return ResponseEntity.notFound().build();
			}
			List<Motorcycle> motorcycles = userService.getMotorcycle(id);
			return ResponseEntity.ok(motorcycles);
		}
	    
		// FeignClient
		@CircuitBreaker(name="carsCB", fallbackMethod = "fallBackSaveCar")
		@PostMapping("/car/{userId}")
		public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car) {
			Car newCar = userService.saveCar(userId, car);
			return ResponseEntity.ok(newCar);
		}
		
		// FeignClient
		@CircuitBreaker(name="motorcyclesCB", fallbackMethod = "fallBackSaveMotorcycle")
		@PostMapping("/motorcycle/{userId}")
		public ResponseEntity<Motorcycle>saveMotorcycle(@PathVariable("userId") int userId,@RequestBody Motorcycle motorcycle){
			Motorcycle newMotorcycle=userService.saveMotorcycle(userId, motorcycle);
			return ResponseEntity.ok(newMotorcycle);
		}
		
		// FeignClient
		@CircuitBreaker(name="allCB", fallbackMethod = "fallBackGetAll")
		@GetMapping("/all/{userId}")
		public ResponseEntity<Map<String, Object>>listAllVehicles(@PathVariable("userId") int userId){
			Map<String, Object>result=userService.getUserAndCars(userId);
			return ResponseEntity.ok(result);
		}
		
		public ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("userId") int id,RuntimeException ex) {
			return new ResponseEntity("The user : " + id + " has the cars in the workshop",HttpStatus.OK);
		} 

		public ResponseEntity<Car> fallBackSaveCar(@PathVariable("userId") int id,@RequestBody Car car,RuntimeException ex) {
			return new ResponseEntity("The user : " + id + " has no money for cars",HttpStatus.OK);
		} 
		
		public ResponseEntity<List<Motorcycle>> fallBackGetMotorcycles(@PathVariable("userId") int id,RuntimeException ex) {
			return new ResponseEntity("The user : " + id + " has the motorcycles in the workshop",HttpStatus.OK);
		} 

		public ResponseEntity<Motorcycle> fallBackSaveMotorcycle(@PathVariable("userId") int id,@RequestBody Motorcycle motorcycle,RuntimeException ex) {
			return new ResponseEntity("The user : " + id + " has no money for motorcycles",HttpStatus.OK);
		} 
		
		public ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") int id,RuntimeException ex) {
			return new ResponseEntity("The user : " + id + " has the vehicles in the workshop",HttpStatus.OK);
		} 

}
