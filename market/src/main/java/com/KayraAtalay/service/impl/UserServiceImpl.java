package com.KayraAtalay.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

   
    @Transactional(readOnly = true)
    public Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is not authenticated or is an anonymous user
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal() instanceof String) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_ACCESS, "User is not authenticated."));
        }

        // as the JwtAuthenticationFilter now places the Customer object there.
        if (authentication.getPrincipal() instanceof Customer) {
            return (Customer) authentication.getPrincipal();
        }

        // If for some reason the principal is not a Customer, this catches the error
        throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "Principal is not a Customer object."));
    }
}