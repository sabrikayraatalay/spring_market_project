package com.KayraAtalay.utils;

import org.springframework.beans.BeanUtils;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCategory;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoUser;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Category;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.User;

public class DtoConverter {

	public static DtoCustomer toDto(Customer customer) {
		DtoCustomer dtoCustomer = new DtoCustomer();

		BeanUtils.copyProperties(customer, dtoCustomer);

		return dtoCustomer;

	}

	public static DtoUser toDto(User user) {
		DtoUser dtoUser = new DtoUser();

		BeanUtils.copyProperties(user, dtoUser);

		return dtoUser;

	}

	public static DtoAddress toDto(Address address) {
		DtoAddress dtoAddress = new DtoAddress();
		BeanUtils.copyProperties(address, dtoAddress);
		return dtoAddress;
	}

	public static DtoCategory toDto(Category category) {
		DtoCategory dtoCategory = new DtoCategory();

		BeanUtils.copyProperties(category, dtoCategory);

		return dtoCategory;
	}

}
