
package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestAddressController {
	
	public RootEntity<DtoAddress> saveAddress(DtoAddressIU dtoAddressIU);
	
	public RootEntity<DtoAddress> findAddressById(Long id);
	
	public RootEntity<RestPageableEntity<DtoAddress>> findAllPageable(PageableRequest pageable);
	
	public RootEntity<DtoAddress> deleteAddressById(Long id);

}
