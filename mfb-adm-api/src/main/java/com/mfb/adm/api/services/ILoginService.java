package com.mfb.adm.api.services;

import java.io.IOException;

import com.mfb.adm.comm.dtos.RespuestaRest;
import com.mfb.adm.comm.requests.SolicitudLogin;

public interface ILoginService {
	RespuestaRest login(SolicitudLogin request) throws IOException;

	RespuestaRest refrescarToken(String request) throws Exception;
}
