package com.mfb.adm.comm.requests;

import java.io.Serializable;
import java.util.List;

import com.mfb.adm.comm.dtos.MessageChatPdfDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class SolicitudPreguntaChatPdf implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sourceId;
	private Boolean referenceSources;
	private List<MessageChatPdfDto> messages;
}
