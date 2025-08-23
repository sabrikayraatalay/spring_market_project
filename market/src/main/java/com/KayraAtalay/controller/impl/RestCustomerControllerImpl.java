package com.KayraAtalay.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestCustomerController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.service.ICustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/customer")
public class RestCustomerControllerImpl extends RestBaseController implements IRestCustomerController {

	@Autowired
	private ICustomerService customerService;

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoCustomer> getCustomerProfile(@PathVariable Long id) {
		return ok(customerService.getCustomerProfile(id));
	}

	@PutMapping("/{id}/update")
	@Override
	public RootEntity<DtoCustomer> updateCustomerProfile(@PathVariable Long id,
			@Valid @RequestBody DtoCustomerIU updateRequest) {
		return ok(customerService.updateCustomerProfile(id, updateRequest));
	}

	@PostMapping("/{id}/address/create")
	@Override
	public RootEntity<DtoAddress> addAddressToCustomerAccount(@PathVariable Long id,
			@RequestBody DtoAddressIU addressRequest) {
		return ok(customerService.addAddressToCustomerAccount(id, addressRequest));
	}

	@GetMapping("/{id}/addresses")
	@Override
	public RootEntity<List<DtoAddress>> getAllCustomerAddresses(@PathVariable Long id) {
		return ok(customerService.getAllCustomerAddresses(id));
	}

	@DeleteMapping("/{customerId}/address/remove/{addressId}")
	@Override
	public void deleteCustomerAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
		customerService.deleteCustomerAddress(customerId, addressId);
	}

}
