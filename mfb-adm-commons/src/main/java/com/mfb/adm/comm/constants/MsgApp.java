package com.mfb.adm.comm.constants;

public class MsgApp {
	// responses
	public static final String RESPONSE_TEST_SUCCESSFULLY = "Test conexion realizado correctamente";
	public static final String RESPONSE_PROCCESS_SUCCESSFULLY = "Proceso realizado correctamente";
	public static final String RESPONSE_FIND_SUCCESSFULLY = "Registro encontrado";
	public static final String RESPONSE_PERSIST_SUCCESSFULLY = "Registrado correctamente";
	public static final String RESPONSE_UPDATE_SUCCESSFULLY = "Actualizado correctamente";
	public static final String RESPONSE_DELETE_SUCCESSFULLY = "Eliminado correctamente";
	public static final String RESPONSE_VALIDATE_SUCCESSFULLY = "Validado correctamente";
	public static final String RESPONSE_FINALIZE_SUCCESSFULLY = "Finalizado correctamente";
	public static final String RESPONSE_NOT_FOUND_RECORD = "Registro no encontrado";
	public static final String RESPONSE_SERVER_ERROR = "Error Servidor: ";
	public static final String RESPONSE_SERVER_ERROR_VALIDATION = "Existen los siguientes errores de validaci�n";
	public static final String RESPONSE_FIND_LIST = "%s Registros encontrados";
	public static final String RESPONSE_NULL_POINTER = "Dato encontrado con valor nulo";
	public static final String RESPONSE_JWT_ERROR = "Error en el token";
	public static final String RESPONSE_JWT_REFRESH = "Nuevo token generado";
	public static final String RESPONSE_REPORT_EMPTY = "El reporte est� vacio";
	// user
	public static final String RESPONSE_UPDATE_PASSWORD_ERROR = "Usted no puede actualizar este password";
	// login
	public static final String RESPONSE_LOGIN_SUCCESSFULLY = "Usuario autenticado correctamente";
	public static final String RESPONSE_LOGIN_ERROR = "Datos inv�lidos";
	public static final String RESPONSE_LOGIN_USER_NOT_EXIST = "No existe el usuario";
	public static final String RESPONSE_LOGIN_PASS_INCORRECT = "La contrase�a es incorrecta";
	public static final String RESPONSE_LOGIN_USER_DISABLED = "EL usuario est� desh�bilitado";

	// dates
	public static final String DATE_PATTERN = "dd/MM/yyyy";
	public static final String RESPONSE_QR_WHATSAPP_SUCCESSFULLY = "Qr obtenido";
	public static final String RESPONSE_QR_WHATSAPP_ERROR = "Error al obtener el qr";
	public static final String RESPONSE_PHONE_SEND_SUCCESSFULLY = "Mensaje enviado correctamente a ";
	public static final String RESPONSE_PHONE_SEND_ERROR = "Ocurrio un error al enviar el mensaje";
	public static final String RESPONSE_EMAIL_SEND_SUCCESSFULLY = "Correo electr�nico enviado correctamente a ";
	public static final String RESPONSE_EMAILS_SEND_SUCCESSFULLY = "%s correo(s) electr�nico(s) enviado(s) correctamente";
	public static final String RESPONSE_FILE_TYPE_UNSUPORTED = "Tipo de archivo no soportado";
	public static final String RESPONSE_EMAIL_DATA_ERROR = "Error al enviar el correo electr�nico";
	public static final String RESPONSE_EMAIL_NOT_EXIST = "No existe correo electronico para el envio del correo electr�nico";
	public static final String RESPONSE_TELEFONO_NOT_EXIST = "No existe un numero de telefono para el envio del mensaje";
	public static final String RESPONSE_TELEFONO_INVALID = "El numero de telefono es inv�lido";
	public static final String RESPONSE_EMAIL_DISABLE_SEND = "El envio de email esta deshabilitado";
	public static final String RESPONSE_CALL_SERVICE_ERROR = "Ocurio un error al consumir el servicio ";
	public static final String RESPONSE_CALL_SERVICE_SOAP_ERROR = "Ocurio un error al consumir los servicios soap del SIN ";

	public static final String RESPONSE_QUESTION_SUCCESS = "Pregunta atendida";
	
	public static final String RESPONSE_PAGE_REQUEST_ERROR = "La pagina y la cantidad de registros debe ser mayor a 0";
	public static final String RESPONSE_USERNAME_ERROR = "username no enviado";

	public static final String RESPONSE_PRINTER_NOT_SET = "Impresora no asignada";
	public static final String RESPONSE_PRINTER_NOT_FOUND = "Impresora no encontrada";
	public static final String RESPONSE_PRINT_SUCCESSFULLY = "Impresi�n realizada con exito";

	// push
	public static final String RESPONSE_NOTIFICATION_SEND = "%s notificacion(es) enviado(s) correctamente";
	public static final String RESPONSE_TOKENS_SAVE = "%s token(s) registrado(s) correctamente";
	public static final String RESPONSE_TOKENS_EXISTS = "El token ya fue registrado";
	
	public static final String RESPONSE_REQUEST_FORBIDEN = "Prohibido!! la solicitud no corresponde a los datos de su empresa";

}
