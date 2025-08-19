package com.KayraAtalay.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	
	@Column(name = "email")
	private String email;
	
	 @ManyToMany
	    @JoinTable(
	        name = "customer_address",
	        joinColumns = @JoinColumn(name = "customer_id"),
	        inverseJoinColumns = @JoinColumn(name = "address_id")
	    )
	private List<Address> addresses;
	
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
	private User user;
	

}
