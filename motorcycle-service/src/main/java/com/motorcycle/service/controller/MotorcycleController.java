package com.motorcycle.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motorcycle.service.entity.Motorcycle;
import com.motorcycle.service.service.MotorcycleService;

@RestController
@RequestMapping("/motorcycle")
public class MotorcycleController {
	
	@Autowired
	private MotorcycleService motorcycleService;
	
	@GetMapping
	public ResponseEntity<List<Motorcycle>>listAllMotorcycles(){
		List<Motorcycle>motorcycles=motorcycleService.getAllMotorcycle();
		if(motorcycles.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(motorcycles);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Motorcycle>getMotorcycle(@PathVariable("id") int id){
		Motorcycle motorcycle=motorcycleService.getMotorcycleById(id);
		if(motorcycle==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(motorcycle);	
	}
	
	@PostMapping
	public ResponseEntity<Motorcycle>saveMotorcycle(@RequestBody Motorcycle motorcycle){
		Motorcycle newMotorcycle=motorcycleService.saveMotorcycle(motorcycle);
		return ResponseEntity.ok(newMotorcycle);
		
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Motorcycle>>listMotorcyclesByUserId(@PathVariable("userId") int id){
		List<Motorcycle>motorcycles=motorcycleService.byUserId(id);
		if(motorcycles.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(motorcycles);
	}

}
