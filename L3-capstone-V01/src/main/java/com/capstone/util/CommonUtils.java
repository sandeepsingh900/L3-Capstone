package com.capstone.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capstone.model.Customer;

public class CommonUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	public static Customer mapCustomer(String ssn, String fn, String ln, String city, String state,
			String streetAddress, long dob) {
		Customer customer = new Customer();
		customer.setFirstName(fn);
		customer.setLastName(ln);
		customer.setSsn(ssn);
		customer.setState(state);
		customer.setCity(city);
		customer.setDob(new Date(dob));
		customer.setStreetAddress(streetAddress);
		return customer;
	}

	
}
