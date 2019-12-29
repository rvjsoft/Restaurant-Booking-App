package com.rvj.app.foodorder.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.rvj.app.dataaccess.CustomerRepository;
import com.rvj.app.foodorder.entity.Address;
import com.rvj.app.foodorder.entity.AddressType;
import com.rvj.app.foodorder.entity.Customer;
import com.rvj.app.foodorder.models.AddAddressRequest;
import com.rvj.app.foodorder.models.AddressModel;
import com.rvj.app.foodorder.models.DeleteAddressRequest;
import com.rvj.app.foodorder.models.UpdateAddressRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository customerRepository; 
	
	public Customer getCustomer(String userName){
		return customerRepository.findByUserName(userName);
	}
	
	@Transactional
	public boolean addAddresses(AddAddressRequest request) {
		Customer customer= getCustomer(request.getUserName());
		for(AddressModel address : request.getAddresses()) {
			Address userAddress = new Address();
			AddressType  addressType = new AddressType();
			BeanUtils.copyProperties(address, addressType);
			userAddress.setAddress(addressType);
			customer.addAddress(userAddress);
		}
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			log.info("Caught exception while adding address");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean updateAddresses(UpdateAddressRequest request) {
		Customer customer= getCustomer(request.getUserName());
		List<Address> addresses = customer.getAddresses().stream().filter(address -> address.getId().equals(request.getAddressId())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(addresses)) {
			return false;
		}
		Address address = addresses.get(0);
		BeanUtils.copyProperties(request.getAddress(), address.getAddress());
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			log.info("Caught exception while updating address");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean deleteAddresses(DeleteAddressRequest request) {
		Customer customer= getCustomer(request.getUserName());
		List<Address> addresses = customer.getAddresses().stream().filter(address -> address.getId().equals(request.getAddressId())).collect(Collectors.toList());
		if(CollectionUtils.isEmpty(addresses)) {
			return false;
		}
		Address address = addresses.get(0);
		customer.removeAddress(address);
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			log.info("Caught exception while deleting address");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
