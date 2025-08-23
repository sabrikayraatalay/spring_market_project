package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;

import java.util.List;

public interface ICustomerService {

    public DtoCustomer getCustomerProfile(Long id);

    public DtoCustomer updateCustomerProfile(Long id, DtoCustomerIU updateRequest);

    public DtoAddress addAddressToCustomerAccount(Long id, DtoAddressIU addressRequest );

    public List<DtoAddress> getAllCustomerAddresses(Long id);

    public void deleteCustomerAddress(Long customerId ,Long addressId);
}