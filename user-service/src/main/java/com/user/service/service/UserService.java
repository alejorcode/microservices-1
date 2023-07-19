package com.user.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entity.User;
import com.user.service.feignclients.CarFeignClient;
import com.user.service.feignclients.MotorcycleFeignClient;
import com.user.service.models.Car;
import com.user.service.models.Motorcycle;
import com.user.service.repository.UserRepository;

@Service
public class UserService {
	
	// RestTemplate
		@Autowired
		private RestTemplate restTemplate;

		// FeignClient
		@Autowired
		private CarFeignClient carFeignClient;
		// FeignClient
		@Autowired
		private MotorcycleFeignClient motorcycleFaingClient;

		@Autowired
		private UserRepository userRepository;

		public List<User> getAllUser() {
			return userRepository.findAll();
		}

		public User getUserById(int id) {
			return userRepository.findById(id).orElse(null);
		}

		public User save(User user) {
			User newUser = userRepository.save(user);
			return newUser;
		}

		// RestTemplate
		public List<Car> getCars(int userId) {
			List<Car> cars = restTemplate.getForObject("http://car-service/car/user/" + userId, List.class);
			return cars;
		}

		// RestTemplate
		public List<Motorcycle> getMotorcycle(int userId) {
			List<Motorcycle> motorcycles = restTemplate.getForObject("http://motorcycle-service/motorcycle/user/" + userId,
					List.class);
			return motorcycles;
		}

		// FeignClient
		public Car saveCar(int userId, Car car) {
			car.setUserId(userId);
			Car newCar = carFeignClient.save(car);
			return newCar;
		}

		// FeignClient
		public Motorcycle saveMotorcycle(int userId, Motorcycle motorcycle) {
			motorcycle.setUserId(userId);
			Motorcycle newMotorcycle = motorcycleFaingClient.save(motorcycle);
			return newMotorcycle;
		}

		// FeingClient
		public Map<String, Object> getUserAndCars(int userId) {
			Map<String, Object> result = new HashMap<>();
			User user = userRepository.findById(userId).orElse(null);
			if (user == null) {
				result.put("Message", "Username does not exist");
				return result;
			}
			result.put("User", user);
			List<Car> cars = carFeignClient.getCars(userId);
			if (cars.isEmpty()) {
				result.put("Cars", "User has no cars");
			} else {
				result.put("Cars", cars);
			}

			List<Motorcycle> motorcycles = motorcycleFaingClient.getMotorcycles(userId);
			if (motorcycles.isEmpty()) {
				result.put("Motorcycles", "User has no motrocycles");
			} else {
				result.put("Motorcycles", motorcycles);
			}
			return result;
		}

}
