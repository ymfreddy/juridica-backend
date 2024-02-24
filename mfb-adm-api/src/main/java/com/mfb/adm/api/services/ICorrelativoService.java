package com.mfb.adm.api.services;

import java.util.Date;

public interface ICorrelativoService {

	Long generar(Long idEmpresa , String operacion, Date fecha);
}
