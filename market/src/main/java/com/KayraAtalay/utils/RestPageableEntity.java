package com.KayraAtalay.utils;

import java.util.List;

import lombok.Data;

@Data
public class RestPageableEntity<T> {
	
	private List<T> content;
	
	private int pageNumber;
	
	private int pageSize;
	
	private Long totalElements;

}