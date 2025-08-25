package com.KayraAtalay.service.impl;

import com.KayraAtalay.dto.AuthRequest;
import com.KayraAtalay.dto.AuthResponse;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.dto.RefreshTokenRequest;
import com.KayraAtalay.dto.RegisterRequest;
import com.KayraAtalay.dto.RegisterResponse;
import com.KayraAtalay.enums.UserRole;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.jwt.JwtService;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.RefreshToken;
import com.KayraAtalay.model.User;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.repository.RefreshTokenRepository;
import com.KayraAtalay.repository.UserRepository;
import com.KayraAtalay.service.IAuthenticationService;
import com.KayraAtalay.utils.DtoConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Customer createCustomer(RegisterRequest request) {
        DtoCustomerIU requestCustomerIU = request.getCustomer();
        Customer customer = new Customer();
        BeanUtils.copyProperties(requestCustomerIU, customer);
        return customer;
    }

    private User createUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        return user;
    }

    private RegisterResponse createRegisterResponse(User user, Customer customer) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUser(DtoConverter.toDto(user));
        registerResponse.setCustomer(DtoConverter.toDto(customer));
        return registerResponse;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    private boolean isCustomerValid(Customer customer) {
        Optional<Customer> optMail = customerRepository.findByEmail(customer.getEmail());
        if (optMail.isPresent()) {
            return false;
        }

        Optional<Customer> optPhoneNumber = customerRepository.findByPhoneNumber(customer.getPhoneNumber());
        if (optPhoneNumber.isPresent()) {
            return false;
        }

        return true;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Optional<User> optional = userRepository.findByUsername(request.getUsername());

        if (optional.isPresent()) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_ALREADY_EXISTS, request.getUsername()));
        }

        Customer customer = createCustomer(request);

        if (isCustomerValid(customer) == false) {
            throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_ALREADY_EXISTS, null));
        }

        Customer savedCustomer = customerRepository.save(customer);
        User user = createUser(request);
        user.setCustomer(savedCustomer);
        savedCustomer.setUser(user);

        User savedUser = userRepository.save(user);

        return createRegisterResponse(savedUser, savedCustomer);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword());

            authenticationProvider.authenticate(authenticationToken);

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, "User not found.")));
                    
            String accessToken = jwtService.generateToken(user); // **DÜZELTME: Customer yerine User nesnesi gönderildi**
            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

            return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
        }
    }

    public boolean isValidRefreshToken(RefreshToken refreshToken) {
        return new Date().before(refreshToken.getExpireDate());
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());

        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, request.getRefreshToken()));
        }

        if (!isValidRefreshToken(optional.get())) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_EXPIRED, request.getRefreshToken()));
        }

        User user = optional.get().getUser();
        
        // Bu kısımda zaten User nesnesi elimizde var, tekrar Customer'dan çekmeye gerek yok
        
        String accessToken = jwtService.generateToken(user); // **DÜZELTME: Customer yerine User nesnesi gönderildi**
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }
}