package com.user.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.service.models.Motorcycle;

@FeignClient(name="motorcycle-service",path="/motorcycle", url="http://localhost:8082")
public interface MotorcycleFeignClient {
	@PostMapping
	public Motorcycle save(@RequestBody Motorcycle motorcycle);
	
	@GetMapping("/user/{userId}")
	public List<Motorcycle>getMotorcycles(@PathVariable("userId") int userId);

}
