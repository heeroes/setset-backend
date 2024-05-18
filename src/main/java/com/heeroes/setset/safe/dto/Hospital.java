package com.heeroes.setset.safe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hospital {
	String id;
	String name;
	String address;
	double latitude;
	double longitude;
	String tel;
}
