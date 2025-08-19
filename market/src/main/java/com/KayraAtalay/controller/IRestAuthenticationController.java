package com.KayraAtalay.controller;

import com.KayraAtalay.dto.AuthRequest;
import com.KayraAtalay.dto.AuthResponse;
import com.KayraAtalay.dto.RefreshTokenRequest;
import com.KayraAtalay.dto.RegisterRequest;
import com.KayraAtalay.dto.RegisterResponse;

public interface IRestAuthenticationController {
	
	public RootEntity<RegisterResponse> register(RegisterRequest request);
	
	public RootEntity<AuthResponse> authenticate(AuthRequest request);
	
	public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest request);

}
