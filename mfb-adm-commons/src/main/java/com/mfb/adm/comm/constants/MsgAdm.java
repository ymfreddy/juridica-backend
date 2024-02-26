package com.mfb.adm.comm.constants;

public class MsgAdm {
	// usuarios
	public static final String RESPONSE_USARIOS_EXISTE_USERNAME = "Ya existe un usuario con el username enviado ";
	public static final String RESPONSE_USARIOS_EXISTE_EMAIL = "Ya existe un usuario con el email enviado ";
	public static final String RESPONSE_USUARIO_ID_INEXISTENTE = "No existe el id de usuario enviado ";
	public static final String RESPONSE_USUARIO_IDS_INEXISTENTE = "No existe ningún id de usuario enviado ";
	public static final String RESPONSE_USUARIO_OPCION_VACIO = "Debe registrar al menos una opción para el usuario ";
	public static final String RESPONSE_USUARIO_OPCION_ERROR = "El usuario no tiene asignado ningúna opcíón";
	public static final String RESPONSE_USUARIO_CLIENTE_SECTOR_INEXISTENTE = "No existe un sector de Compra y Venta para el tipo de usuario";
	// empresas
	public static final String RESPONSE_EMPRESA_ID_NO_ENVIADO = "Debe enviar el identificador de la empresa";
	public static final String RESPONSE_EMPRESAS_EXISTE_NIT = "Ya existe una empresa con el nit enviado ";
	public static final String RESPONSE_EMPRESAS_EXISTE_SIGLA = "Ya existe una empresa con la sigla enviada ";
	public static final String RESPONSE_EMPRESAS_NO_EXISTE = "No existe una empresa con el id enviado ";
	public static final String RESPONSE_EMPRESAS_SIGLA_CON_CORRELATIVO = "Ya existe un correlativo generado con la sigla enviada ";
	// recursos
	public static final String RESPONSE_RECURSOS_NO_EXISTE = "No existe el recurso enviado ";
	public static final String RESPONSE_RECURSOS_NO_EXISTE_CHATPDF = "El recurso enviado no esta disponible ";
	public static final String RESPONSE_LIMITE_EXCEDIDO = "Usted supero el límite de %s preguntas diarias";
	// clientes
	public static final String RESPONSE_CLIENTES_EXISTE_CODIGO = "Ya existe un cliente con el código enviado ";
	// categorias
	public static final String RESPONSE_CATEGORIAS_EXISTE_NOMBRE = "Ya existe una categoria con el nombre enviado.";
	// productos
	public static final String RESPONSE_PRODUCTOS_EXISTE_CODIGO = "Ya existe un producto con el codigo enviado ";
	public static final String RESPONSE_PRODUCTOS_SELECCIONAR_PARAMETRO_SUCURSAL = "La sucursal no existe";

}
