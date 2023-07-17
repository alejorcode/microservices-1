package com.car.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.car.service.entity.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
	List<Car>findByUserId(int userId);
}
