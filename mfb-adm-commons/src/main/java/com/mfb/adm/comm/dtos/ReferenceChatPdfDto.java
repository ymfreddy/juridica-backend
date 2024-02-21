package com.mfb.adm.comm.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReferenceChatPdfDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pageNumber;
}
