package com.heeroes.setset.attraction.dto;

import lombok.Data;

@Data
public class Attraction {
	int id;
	String title;
	int contentTypeId;
	String addr;
	String image;
	double latitude;
	double longitude;
	String overview;
	int popularity;
}