package com.KayraAtalay.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.repository.AddressRepository;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.service.ICustomerService;
import com.KayraAtalay.utils.DtoConverter;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	private Customer findCustomerFromDatabase(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, id.toString())));

		return customer;
	}

	@Override
	public DtoCustomer getCustomerProfile(Long id) {

		Customer customer = findCustomerFromDatabase(id);

		return DtoConverter.toDto(customer);
	}

	@Override
	public DtoCustomer updateCustomerProfile(Long id, DtoCustomerIU updateRequest) {

		Customer customer = findCustomerFromDatabase(id);

		BeanUtils.copyProperties(updateRequest, customer);

		Customer savedCustomer = customerRepository.save(customer);

		return DtoConverter.toDto(savedCustomer);

	}

	@Override
	public DtoAddress addAddressToCustomerAccount(Long id, DtoAddressIU addressRequest) {
		Customer customer = findCustomerFromDatabase(id);
		Address address = new Address();
		BeanUtils.copyProperties(addressRequest, address);

		for (Address findAddress : customer.getAddresses()) {
			if (findAddress.equals(address)) {
				throw new BaseException(
						new ErrorMessage(MessageType.GENERAL_EXCEPTION, "This address already in your list"));
			}
		}

		Address savedAddress = addressRepository.save(address);

		customer.getAddresses().add(address);

		customerRepository.save(customer);

		return DtoConverter.toDto(savedAddress);
	}

	@Override
	public List<DtoAddress> getAllCustomerAddresses(Long id) {

		Customer customer = findCustomerFromDatabase(id);
		List<Address> addresses = customer.getAddresses();

		return addresses.stream().map(DtoConverter::toDto).collect(Collectors.toList());
	}

	@Override
	public void deleteCustomerAddress(Long customerId, Long addressId) {
		Customer customer = findCustomerFromDatabase(customerId);
		Address addressToDelete = addressRepository.findById(addressId).orElseThrow(
				() -> new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, addressId.toString())));

		// Checking if the address belongs to the customer
		if (!customer.getAddresses().contains(addressToDelete)) {
			throw new BaseException(
					new ErrorMessage(MessageType.GENERAL_EXCEPTION, "This address is not on your list"));
		}

		// Removing the address from the customer's collection.
		customer.getAddresses().remove(addressToDelete);
		customerRepository.save(customer);

		addressRepository.delete(addressToDelete);

	}

}
