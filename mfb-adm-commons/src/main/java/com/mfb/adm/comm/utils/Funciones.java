package com.mfb.adm.comm.utils;

import java.util.regex.Pattern;

public class Funciones {

	private static String[] UNIDADES = { "", "un ", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ",
			"nueve " };
	private static String[] DECENAS = { "diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
			"diecisiete ", "dieciocho ", "diecinueve ", "veinte ", "veintiuno ", "veintidos ", "veintitres ",
			"veinticuatro ", "veinticinco ", "veintiseis ", "veintisiete ", "veintiocho ", "veintinueve ", "treinta ",
			"cuarenta ", "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa " };
	private static String[] CENTENAS = { "", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ",
			"seiscientos ", "setecientos ", "ochocientos ", "novecientos " };

	public static String ConvertirNumeroALiteral(String numero, boolean mayusculas, String moneda) {

		String literal = "";
		String parte_decimal;
		// si el numero utiliza (.) en lugar de (,) -> se reemplaza
		numero = numero.replace(".", ",");

		// si el numero no tiene parte decimal, se le agrega ,00
		if (numero.indexOf(",") == -1) {
			numero = numero + ",00";
		}
		// se valida formato de entrada -> 0,00 y 999 999 999,00
		if (Pattern.matches("\\d{1,9},\\d{1,2}", numero)) {
			// se divide el numero 0000000,00 -> entero y decimal
			String[] Num = numero.split("[,]", -1);

			// de da formato al numero decimal
			parte_decimal = Num[1] + "/100 (" + moneda.toUpperCase() +")";
			// se convierte el numero a literal
			if (Integer.parseInt(Num[0]) == 0) { // si el valor es cero
				literal = "cero ";
			} else if (Integer.parseInt(Num[0]) > 999999) { // si es millon
				literal = getMillones(Num[0]);
			} else if (Integer.parseInt(Num[0]) > 999) { // si es miles
				literal = getMiles(Num[0]);
			} else if (Integer.parseInt(Num[0]) > 99) { // si es centena
				literal = getCentenas(Num[0]);
			} else if (Integer.parseInt(Num[0]) > 9) { // si es decena
				literal = getDecenas(Num[0]);
			} else { // sino unidades -> 9
				literal = getUnidades(Num[0]);
			}
			// devuelve el resultado en mayusculas o minusculas
			if (mayusculas) {
				return (literal + parte_decimal).toUpperCase();
			} else {
				return (literal + parte_decimal);
			}
		} else { // error, no se puede convertir
			return literal = "NUMERO MAYOR A 999 999 999,00";
		}
	}

	/* funciones para convertir los numeros a literales */

	private static String getUnidades(String numero) { // 1 - 9
														// si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
		String num = numero.substring(numero.length() - 1);
		return UNIDADES[Integer.parseInt(num)];
	}

	private static String getDecenas(String num) {
		// 99
		int n = Integer.parseInt(num);
		if (n < 10) {
			// para casos como -> 01 - 09
			return getUnidades(num);
		} else if (n > 29) {
			// para 30...99
			String u = getUnidades(num);
			if (u.equals("")) {
				// para 20,30,40,50,60,70,80,90
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 17];
			} else {
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 17] + "y " + u;
			}
		} else {
			// numeros entre 11 y 19
			return DECENAS[n - 10];
		}
	}

	private static String getCentenas(String num) {
		// 999 o 099
		if (Integer.parseInt(num) > 99) {
			// es centena
			if (Integer.parseInt(num) == 100) { // caso especial
				return " cien ";
			} else {
				return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
			}
		} else {
			// por Ej. 099
			// se quita el 0 antes de convertir a decenas
			return getDecenas(Integer.parseInt(num) + "");
		}
	}

	private static String getMiles(String numero) {
		// 999 999
		// obtiene las centenas
		String c = numero.substring(numero.length() - 3);
		// obtiene los miles
		String m = numero.substring(0, numero.length() - 3);
		String n = "";
		// se comprueba que miles tenga valor entero
		if (Integer.parseInt(m) > 0) {
			n = getCentenas(m);
			return n + "mil " + getCentenas(c);
		} else {
			return "" + getCentenas(c);
		}
	}

	private static String getMillones(String numero) {
		// 000 000 000
		// se obtiene los miles
		String miles = numero.substring(numero.length() - 6);
		// se obtiene los millones
		String millon = numero.substring(0, numero.length() - 6);
		String n = "";
		if (millon.length() > 1) {
			n = getCentenas(millon) + "millones ";
		} else {
			n = getUnidades(millon) + "millon ";
		}
		return n + getMiles(miles);
	}
	
	public static String generarCodigo(int i) 
    { 
        String theAlphaNumericS;
        StringBuilder builder;
        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"; 
        //create the StringBuffer
        builder = new StringBuilder(i); 

        for (int m = 0; m < i; m++) { 

            // generate numeric
            int myindex 
                = (int)(theAlphaNumericS.length() 
                        * Math.random()); 

            // add the characters
            builder.append(theAlphaNumericS 
                        .charAt(myindex)); 
        } 

        return builder.toString(); 
    } 
	
	public static String obtenerExtension(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("fileName must not be null!");
        }

        String extension = "";

        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
        }

        return extension;

    }
	
	public static String camelToSnake(String str)
    {
         // Empty String
        String result = "";
         // Append first character(in lower case)
        // to result string
        char c = str.charAt(0);
        result = result + Character.toLowerCase(c);
 
        // Traverse the string from
        // ist index to last index
        for (int i = 1; i < str.length(); i++) {
 
            char ch = str.charAt(i);
 
            // Check if the character is upper case
            // then append '_' and such character
            // (in lower case) to result string
            if (Character.isUpperCase(ch)) {
                result = result + '_';
                result
                    = result
                      + Character.toLowerCase(ch);
            }
 
            // If the character is lower case then
            // add such character into result string
            else {
                result = result + ch;
            }
        }
 
        // return the result
        return result;
    }
	
	public static String getDay(Integer dia)
    {
		String respuesta = "";
		switch (dia) {
		case 1:
			respuesta = "DOMINGO";
			break;
		case 2:
			respuesta = "LUNES";
			break;
		case 3:
			respuesta = "MARTES";
			break;
		case 4:
			respuesta = "MIERCOLES";
			break;
		case 5:
			respuesta = "JUEVES";
			break;
		case 6:
			respuesta = "VIERNES";
			break;
		case 7:
			respuesta = "SABADO";
			break;
		default:
			respuesta = "NO EXISTE";
			break;
		}
		
		return respuesta;
    }
}
