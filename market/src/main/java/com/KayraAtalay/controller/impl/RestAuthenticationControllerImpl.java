package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestAuthenticationController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.AuthRequest;
import com.KayraAtalay.dto.AuthResponse;
import com.KayraAtalay.dto.RefreshTokenRequest;
import com.KayraAtalay.dto.RegisterRequest;
import com.KayraAtalay.dto.RegisterResponse;
import com.KayraAtalay.service.IAuthenticationService;

import jakarta.validation.Valid;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

	@Autowired
	private IAuthenticationService authenticationService;

	@Override
	@PostMapping("/register")
	public RootEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ok(authenticationService.register(request));
	}

	@Override
	@PostMapping("/authenticate")
	public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
		return ok(authenticationService.authenticate(request));
	}

	@Override
	@PostMapping("/refreshToken")
	public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return ok(authenticationService.refreshToken(request));
	}

}
