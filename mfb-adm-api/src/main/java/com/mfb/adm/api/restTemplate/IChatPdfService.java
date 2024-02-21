package com.mfb.adm.api.restTemplate;

import com.mfb.adm.comm.dtos.RespuestaRestTemplate;

public interface IChatPdfService {
	RespuestaRestTemplate preguntar(String pregunta, String idRecurso);
}
