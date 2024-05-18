package com.heeroes.setset.safe.dto;

import lombok.Data;

@Data
public class DisasterAlarm {
	String createdAt;  //CRT_DT
	String message;   //MSG_CN
	String region;   //RCPTN_RGN_NM
	String type;     //DST_SE_NM
}
