package com.car.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.service.entity.Car;
import com.car.service.repository.CarRepository;

@Service
public class CarService {
	
	@Autowired
	private CarRepository carRepository;

	public List<Car> getAllCar() {
		return carRepository.findAll();
	}

	public Car getCarById(int id) {
		return carRepository.findById(id).orElse(null);
	}

	public Car save(Car car) {
		Car newCar = carRepository.save(car);
		return newCar;
	}

	public List<Car> byUserId(int userId) {
		return carRepository.findByUserId(userId);
	}

}
