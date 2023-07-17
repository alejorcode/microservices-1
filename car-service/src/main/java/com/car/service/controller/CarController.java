package com.car.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.service.entity.Car;
import com.car.service.service.CarService;

@RestController
@RequestMapping("/car")
public class CarController {
	
	@Autowired
	private CarService carService;

	@GetMapping
	public ResponseEntity<List<Car>> listAllCars() {
		List<Car> cars = carService.getAllCar();
		if (cars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cars);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getCar(@PathVariable("id") int id) {
		Car car = carService.getCarById(id);
		if (car == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(car);

	}

	@PostMapping
	public ResponseEntity<Car> saveCar(@RequestBody Car car) {
		Car newCar = carService.save(car);
		return ResponseEntity.ok(newCar);

	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Car>> listCarByUserId(@PathVariable("userId") int id) {
		List<Car> cars = carService.byUserId(id);
		if (cars.isEmpty()) {
			return ResponseEntity.noContent().build();

		}

		return ResponseEntity.ok(cars);
	}

}
