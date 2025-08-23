package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestAddressController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.service.IAddressService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/address")
public class RestAddressControllerImpl extends RestBaseController implements IRestAddressController {

	@Autowired
	private IAddressService addressService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoAddress> saveAddress(@Valid @RequestBody DtoAddressIU dtoAddressIU) {
		return ok(addressService.saveAddress(dtoAddressIU));
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoAddress> findAddressById(@PathVariable Long id) {
		return ok(addressService.findAddressById(id));
	}

	@GetMapping("/list/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoAddress>> findAllPageable(PageableRequest pageable) {
	    Page<DtoAddress> page = addressService.findAllPageableDto(toPageable(pageable));
	    return ok(toPageableResponse(page, page.getContent()));
	}

	@DeleteMapping("/delete/{id}")
	@Override
	public RootEntity<DtoAddress> deleteAddressById(@PathVariable Long id) {
		return ok(addressService.deleteAddressById(id));
	}

}