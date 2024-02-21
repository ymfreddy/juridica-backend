package com.mfb.adm.comm.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RespuestaRestTemplate {
	private boolean success;
	private String message;
	private Object content;
}
