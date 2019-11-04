package com.capstone.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.model.Customer;
import com.capstone.service.EthService;

@RestController
@RequestMapping("/v1")
public class ApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
	@Autowired
	private EthService ethService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/customer/{ssn}")
	ResponseEntity<Customer> customer(@PathVariable String ssn) throws Exception {
		return  ResponseEntity.ok().body(ethService.getCustomer(ssn));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/customers")
	ResponseEntity<List<Customer>> customers() throws Exception {
		return  ResponseEntity.ok().body(ethService.getAllCustomers());
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/customer")
	ResponseEntity<String> addCustomer(@RequestBody Customer customer) throws Exception {
		return  ResponseEntity.ok().body(ethService.insertCustomer(customer));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/customer")
	ResponseEntity<String> updateCustomer(@RequestBody Customer customer) throws Exception {
		return  ResponseEntity.ok().body(ethService.updateCustomer(customer));
	}
}
