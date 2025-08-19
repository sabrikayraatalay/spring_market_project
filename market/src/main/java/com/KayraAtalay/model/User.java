package com.KayraAtalay.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.KayraAtalay.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	 @OneToOne(mappedBy = "user")
	 private Customer customer;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 return List.of(() -> "ROLE_" + role.name());
	}

}
