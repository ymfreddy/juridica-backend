package com.mfb.adm.comm.constants;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ConstApp {

	// hhtp codes
	public static final int HTTP_200_OK = 200;
	public static final int HTTP_201_CREATED = 201;
	public static final int HTTP_202_ACCEPTED = 202;
	public static final int HTTP_400_BAD_REQUEST = 400;
	public static final int HTTP_401_UNAUTHORIZED = 401;
	public static final int HTTP_403_FORBIDDEN = 403;
	public static final int HTTP_404_NOT_FOUND = 404;
	public static final int HTTP_405_METHOD_NOT_ALLOWED = 405;
	public static final int HTTP_500_INTERNAL_SERVER_ERROR = 500;
	public static final int HTTP_502_BAD_GATEWAY = 502;

	public static final DateFormat FORMATO_FECHA_HORA = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	public static final DateFormat FORMATO_FECHA_HORA_COMPLETA = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final DateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat FORMATO_FECHA_NOMBRE = new SimpleDateFormat("ddMMyyyy-HHmmss");
	public static final DateFormat FORMATO_GESTION = new SimpleDateFormat("yy");
	public static final SimpleDateFormat FORMATO_HORA = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat FORMATO_DIA_SEMANA = new SimpleDateFormat("EEEE");
	public static final DateFormat FORMATO_FECHA_ISO = new SimpleDateFormat("yyyy-MM-dd");


	public static final String OPERACION_CITA = "CT";
	

	public static final BigDecimal VALOR_0 = new BigDecimal("0");
	public static final BigDecimal TASA_EFECTIVA_MINERAS = new BigDecimal("0.1494253");

	// email
	public static final String EMAIL_USUARIO_CREACION_PASSWORD = "ENVIO DE CREDENCIALES";
	public static final String EMAIL_USUARIO_RESETEO_PASSWORD = "REINICIO DE CREDENCIALES";
	public static final String EMAIL_USUARIO_DETALLE = "Los datos para ingresar al sistema son username: %s y el password : %s ";
	public static final String EMAIL_USUARIO_LUGAR = "POTOSI - BOLIVIA";
	public static final String EMAIL_FIRMA = "Mi factura Bolivia";

}
