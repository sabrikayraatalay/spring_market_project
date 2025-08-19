package com.KayraAtalay.service;

import com.KayraAtalay.dto.AuthRequest;
import com.KayraAtalay.dto.AuthResponse;
import com.KayraAtalay.dto.RefreshTokenRequest;
import com.KayraAtalay.dto.RegisterRequest;
import com.KayraAtalay.dto.RegisterResponse;

public interface IAuthenticationService {

	public AuthResponse authenticate(AuthRequest request);

	public RegisterResponse register(RegisterRequest request);

	AuthResponse refreshToken(RefreshTokenRequest request);

}
