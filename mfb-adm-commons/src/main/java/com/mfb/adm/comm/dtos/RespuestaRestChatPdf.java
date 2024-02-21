package com.mfb.adm.comm.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RespuestaRestChatPdf implements Serializable {
	private static final long serialVersionUID = 1L;
	private String content;
	private String error;
	private List<ReferenceChatPdfDto> references;
}

