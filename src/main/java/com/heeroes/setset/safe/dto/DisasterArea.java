package com.heeroes.setset.safe.dto;

import lombok.Data;

@Data
public class DisasterArea {
	String riskCode; //DST_RSK_DSTRCTCD
	String fcltName;  //FCLT_NM
	String fcltCodeName;  //FCLTY_GRD_NM
	String registDate;  //DSGN_YMD
	String registReason;  //DSGN_RSN
	String riskFactor;   //RSK_FACTR_CN
	String address;    //RONA_DADDR
	
}
