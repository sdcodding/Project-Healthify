package com.hms.api.controller;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.api.entity.Role;
import com.hms.api.entity.User;
import com.hms.api.exception.ResourceAlreadyExistsException;
import com.hms.api.exception.ResourceNotFoundException;
import com.hms.api.service.UserService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	private static Logger LOG = LogManager.getLogger(AdminController.class);

	@Autowired
	UserService userService;

	@PostMapping("/add-user")
	
	public ResponseEntity<Boolean> registerUser(@RequestBody User user) {

		boolean isAdded = userService.addUser(user);
		if (isAdded) {
			LOG.info("Added User :" + user);
			return new ResponseEntity<Boolean>(isAdded, HttpStatus.CREATED);
		}

		else {
			LOG.info("User Already Exixts With >ID:" + user.getUsername());
			throw new ResourceAlreadyExistsException("User Already Exixts With >ID:" + user.getUsername());
		}

	}

	@DeleteMapping(value = "/delete-user/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable String id) {
		boolean isDeleted = this.userService.deleteUserById(id);
		if (isDeleted) {
			LOG.info("User Deleted ID: " + id);
			return new ResponseEntity<Boolean>(isDeleted, HttpStatus.OK);
		} else {
			LOG.info("User Not Found Deleted >ID: " + id);
			throw new ResourceNotFoundException("User Not Found For Delete >ID: " + id);
		}
	}

	@PutMapping("/update-user")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User admn = userService.updateUser(user);
		if (admn != null) {
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}

		else {
			LOG.info("User Not Found For Update >ID: " + user.getUsername());
			throw new ResourceNotFoundException("User Not Found For Update >ID:" + user.getUsername());
		}

	}

	@GetMapping(value = "get-all-user", produces = "application/json")
	public ResponseEntity<List<User>> getAllAdmin() {
		List<User> list = this.userService.getAllUsers();
		if (!list.isEmpty()) {
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		} else {
			LOG.info("User Not Found");
			throw new ResourceNotFoundException("User Not Found");
		}
	}

	@PostMapping(value = "/add-role")
	public ResponseEntity<Object> addRole(@RequestBody Role role) {
		Role userRole = userService.addRole(role);
		if (userRole != null) {
			return new ResponseEntity<Object>(role, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Object>("Role Not Added", HttpStatus.OK);

		}
	}

	@GetMapping(value = "/get-role-by-id/{roleId}")
	public ResponseEntity<Role> getRoleById(@PathVariable int roleId) {
		Role role = userService.getRoleById(roleId);
		if (role != null) {
			return new ResponseEntity<Role>(role, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Role Not Found For ID : " + roleId);
		}
	}

	@GetMapping(value = "/get-total-count-of user")
	public ResponseEntity<Long> getUsersTotalCounts() {
		Long count = userService.getUsersTotalCounts();
		if (count > 0) {
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User Not Exists");
		}
	}

	@GetMapping(value = "/get-total-count-of-user-by-type/{type}")
	public ResponseEntity<Long> getUsersTotalCountsByType(@PathVariable String type) {
		Long count = userService.getUsersTotalCounts(type);
		if (count > 0) {
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User Not Exists For " + type + " Type");
		}
	}

	@GetMapping(value = "/get-total-count-of-user-by-date-and-type//{date}/{type}")
	public ResponseEntity<Long> getUserCountByDateAndType(@PathVariable Date date, @PathVariable String type) {
		Long count = userService.getUserCountByDateAndType(date, type);
		if (count > 0) {
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User Not Exists Please Check Date & Type");
		}
	}

	@GetMapping(value = "/get-user-by-firtname/{firstName}", produces = "application/json")
	public ResponseEntity<List<User>> getUserByFirstName(@PathVariable String firstName) {
		List<User> list = userService.getUserByFirstName(firstName);
		if (!list.isEmpty()) {
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("User Not Exists For Name : " + firstName);
		}
	}

	@GetMapping(value = "/user/report")
	public String generateReport() {

		return userService.generateReport();

	}

}
