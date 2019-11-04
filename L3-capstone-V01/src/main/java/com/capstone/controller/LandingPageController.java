package com.capstone.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.capstone.model.Customer;
import com.capstone.service.EthService;
import com.capstone.util.CommonConstants;

@Controller
public class LandingPageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LandingPageController.class);
	@Autowired
	private EthService ethService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView profile() {
		LOGGER.info("landing page view START");
		ModelAndView modelAndView = new ModelAndView();
		List<Customer> customers;
		try {
			customers = ethService.getAllCustomers();
			modelAndView.addObject("customers", customers);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		modelAndView.setViewName("landing");
		modelAndView.addObject("titleHome", CommonConstants.CAPSTONE_TITLE);
		LOGGER.info("landing page view END");
		return modelAndView;
	}

	@RequestMapping(path = { "customer", "/customer/{ssn}" }, method = RequestMethod.GET)
	public ModelAndView customer(@PathVariable(required = false) String ssn) {
		LOGGER.info("landing page view START");
		ModelAndView modelAndView = new ModelAndView();
		Customer customer = new Customer();
		if (ssn == null) {

		} else {
			try {
				customer = ethService.getCustomer(ssn);

			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		modelAndView.addObject("customer", customer);
		modelAndView.setViewName("customer");
		modelAndView.addObject("titleHome", CommonConstants.CAPSTONE_TITLE);
		LOGGER.info("landing page view END");
		return modelAndView;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public ModelAndView customerPost(Customer customer, HttpServletRequest request) {
		LOGGER.info("landing page view START");
		ModelAndView modelAndView = new ModelAndView();
		String txn = "Error";

		try {
			if (request.getParameter("customerSSN") == null || request.getParameter("customerSSN").equals("")) {
				txn = ethService.insertCustomer(customer);
			} else {
				customer.setSsn(request.getParameter("customerSSN"));
				txn = ethService.updateCustomer(customer);
			}
		} catch (Exception e) {
			txn = e.getMessage();
			LOGGER.error(e.getMessage(), e);
		}
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("txn", txn);
		modelAndView.setViewName("customer");
		modelAndView.addObject("titleHome", CommonConstants.CAPSTONE_TITLE);
		LOGGER.info("landing page view END");
		return modelAndView;
	}

}
