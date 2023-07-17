package com.user.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.service.models.Car;

@FeignClient(name = "car-service",path = "/car", url = "http://localhost:8081")
public interface CarFeignClient {
	@PostMapping
	public Car save(@RequestBody Car car);
	
	@GetMapping("/user/{userId}")
	public List<Car>getCars(@PathVariable("userId") int userId);

}
