package com.KayraAtalay.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.repository.AddressRepository;
import com.KayraAtalay.service.IAddressService;
import com.KayraAtalay.utils.DtoConverter;

@Service
public class AddressServiceImpl implements IAddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	private Address createAddress(DtoAddressIU dtoAddressIU) {
		Address address = new Address();
		address.setCreateTime(new Date());
		BeanUtils.copyProperties(dtoAddressIU, address);
		return address;
	}
	
	@Override
	public DtoAddress saveAddress(DtoAddressIU dtoAddressIU) {
		Address savedAddress = addressRepository.save(createAddress(dtoAddressIU));
		return DtoConverter.toDto(savedAddress);
	}

	@Override
	public Page<Address> findAllPageable(Pageable pageable) {
		return addressRepository.findAll(pageable);
	}

	@Override
	public Page<DtoAddress> findAllPageableDto(Pageable pageable) {
		Page<Address> addressPage = addressRepository.findAll(pageable);
		if (addressPage.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}
		
		return addressPage.map(DtoConverter::toDto);
	}

	@Override
	public DtoAddress findAddressById(Long id) {
		Optional<Address> optAddress = addressRepository.findById(id);
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, id.toString()));
		}
		return DtoConverter.toDto(optAddress.get());
	}

	@Override
	public DtoAddress deleteAddressById(Long id) {
		Optional<Address> optAddress = addressRepository.findById(id);
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, id.toString()));
		}
		addressRepository.deleteById(id);
		return DtoConverter.toDto(optAddress.get());
	}
}