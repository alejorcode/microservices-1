package com.motorcycle.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorcycle.service.entity.Motorcycle;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Integer> {
	List<Motorcycle> findByUserId(int userId);

}
