package com.capstone.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple6;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.StaticGasProvider;

import com.capstone.customer.contract.CapstoneCustomer;
import com.capstone.model.Customer;
import com.capstone.util.CommonConstants;
import com.capstone.util.CommonUtils;

@Service
public class EthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EthService.class);
	private Web3j web3 = null;

	public boolean initConnections() {
		if (web3 == null) {
			try {
				web3 = Web3j.build(new HttpService(CommonConstants.RPC_NODE));
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				return false;
			}
		}
		return true;
	}

	public List<Customer> getAllCustomers() throws Exception {
		initConnections();
		Credentials userCred = Credentials.create(CommonConstants.PRIVATE_KEY);
		List<Customer> customers = new ArrayList<>();
		EthGasPrice price = web3.ethGasPrice().send();
		EthBlock block = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
		CapstoneCustomer customerContract = CapstoneCustomer.load(CommonConstants.CAPSTONE_CONTRACT_ADDRESS, web3,
				userCred, new StaticGasProvider(price.getGasPrice(), block.getBlock().getGasLimit()));
		
		long count = customerContract.getCustomerCount().send().getValue().longValue();
		for (int i = 0; i < count; i++) {
			Uint256 index = new Uint256(i);
			Tuple7<Utf8String, Utf8String, Utf8String, Utf8String, Utf8String, Utf8String, Uint256> t = customerContract
					.getCustomerById(index).send();
			String ssn = t.getValue1().toString();
			String fn = t.getValue2().toString();
			String ln = t.getValue3().toString();
			String city = t.getValue4().toString();
			String state = t.getValue5().toString();
			String streetAddress = t.getValue6().toString();
			long dob = t.getValue7().getValue().longValue();
			Customer customer = CommonUtils.mapCustomer(ssn, fn, ln, city, state, streetAddress, dob);
			customers.add(customer);
		}
		return customers;
	}

	public String insertCustomer(Customer customer) throws Exception {
		initConnections();
		Credentials userCred = Credentials.create(CommonConstants.PRIVATE_KEY);
		EthGasPrice price = web3.ethGasPrice().send();
		EthBlock block = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
		CapstoneCustomer customerContract = CapstoneCustomer.load(CommonConstants.CAPSTONE_CONTRACT_ADDRESS, web3,
				userCred, new StaticGasProvider(price.getGasPrice(), block.getBlock().getGasLimit()));
		return customerContract.addCustomer(new Utf8String(customer.getSsn()), new Utf8String(customer.getFirstName()),
				new Utf8String(customer.getLastName()), new Utf8String(customer.getCity()),
				new Utf8String(customer.getState()), new Utf8String(customer.getStreetAddress()),
				new Uint256(customer.getDob().getTime())).send().getTransactionHash();
	}

	public Customer getCustomer(String ssn) throws Exception {
		initConnections();
		Credentials userCred = Credentials.create(CommonConstants.PRIVATE_KEY);

		EthGasPrice price = web3.ethGasPrice().send();
		EthBlock block = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
		CapstoneCustomer customerContract = CapstoneCustomer.load(CommonConstants.CAPSTONE_CONTRACT_ADDRESS, web3,
				userCred, new StaticGasProvider(price.getGasPrice(), block.getBlock().getGasLimit()));

		Tuple6<Utf8String, Utf8String, Utf8String, Utf8String, Utf8String, Uint256> t = customerContract
				.getCustomerBySSN(new Utf8String(ssn)).send();

		String fn = t.getValue1().toString();
		String ln = t.getValue2().toString();
		String city = t.getValue3().toString();
		String state = t.getValue4().toString();
		String streetAddress = t.getValue5().toString();
		long dob = t.getValue6().getValue().longValue();
		Customer customer = CommonUtils.mapCustomer(ssn, fn, ln, city, state, streetAddress, dob);

		return customer;
	}
	
	public String updateCustomer(Customer customer) throws Exception {
		initConnections();
		Credentials userCred = Credentials.create(CommonConstants.PRIVATE_KEY);
		EthGasPrice price = web3.ethGasPrice().send();
		EthBlock block = web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
		CapstoneCustomer customerContract = CapstoneCustomer.load(CommonConstants.CAPSTONE_CONTRACT_ADDRESS, web3,
				userCred, new StaticGasProvider(price.getGasPrice(), block.getBlock().getGasLimit()));
		return customerContract.updateCustomer(new Utf8String(customer.getSsn()), new Utf8String(customer.getFirstName()),
				new Utf8String(customer.getLastName()), new Utf8String(customer.getCity()),
				new Utf8String(customer.getState()), new Utf8String(customer.getStreetAddress()),
				new Uint256(customer.getDob().getTime())).send().getTransactionHash();
	}
	

}
