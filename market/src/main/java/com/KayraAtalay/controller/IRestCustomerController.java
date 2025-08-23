package com.KayraAtalay.controller;

import java.util.List;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;

public interface IRestCustomerController {
	
	public RootEntity<DtoCustomer> getCustomerProfile(Long id);

    public RootEntity<DtoCustomer> updateCustomerProfile(Long id,DtoCustomerIU updateRequest);

    public RootEntity<DtoAddress> addAddressToCustomerAccount(Long id, DtoAddressIU addressRequest);

    public RootEntity<List<DtoAddress>> getAllCustomerAddresses(Long id);

    public void deleteCustomerAddress(Long customerId, Long addressId);

}
