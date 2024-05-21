package com.heeroes.setset.safe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DisasterAlarm implements Comparable<DisasterAlarm> {
	String createdAt;  //CRT_DT
	String message;   //MSG_CN
	String region;   //RCPTN_RGN_NM
	String type;     //EMRG_STEP_NM
	
	@Override
	public int compareTo(DisasterAlarm o) {
		return -this.createdAt.compareTo(o.createdAt);
	}
}
