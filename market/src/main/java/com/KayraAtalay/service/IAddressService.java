package com.KayraAtalay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.model.Address;

public interface IAddressService {

	public DtoAddress saveAddress(DtoAddressIU dtoAddressIU);
	
	public Page<Address> findAllPageable(Pageable pageable);
	
	public Page<DtoAddress> findAllPageableDto(Pageable pageable);
	
	public DtoAddress findAddressById(Long id);
	
	public DtoAddress deleteAddressById(Long id);

}