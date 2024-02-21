package com.mfb.adm.comm.dtos;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MessageChatPdfDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String role;
	private String content;
}
