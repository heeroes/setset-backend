package com.heeroes.setset.safe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class News implements Comparable<News>{
	String title;  //YNA_TTL
	String content;  //YNA_CN
	String Date; //YNA_YMD
	String author; //YNA_WRTR_NM
	long id; //YNA_NO
	
	@Override
	public int compareTo(News o) {
		return -this.Date.compareTo(o.Date);
	}
}
