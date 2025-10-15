package Validaciones;

import java.util.regex.Pattern;

public class Validaciones {

	/**
	 * Valida que el texto contenga solo letras (incluyendo acentos) y espacios.
	 */
	public static boolean validarLetras(String nombre) {
		if (nombre == null || nombre.isEmpty())
			return false;
		// \p{L} incluye letras en cualquier idioma, \s incluye espacios
		return Pattern.matches("[\\p{L}\\s]+", nombre);
	}

	/**
	 * Valida el formato final del ISBN (exactamente 13 dígitos numéricos).
	 */
	public static boolean validarISBN(String isbn) {
		if (isbn == null)
			return false;
		return isbn.length() == 13 && isNumber(isbn);
	}

	/**
	 * Valida que el texto sea un número entero y que el valor sea NO negativo.
	 */
	public static boolean validarCantidad(String texto) {
		if (texto == null || texto.isEmpty())
			return false;
		try {
			int cantidad = Integer.parseInt(texto.trim());
			return cantidad >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Valida que el texto sea un número decimal y que el valor sea positivo (> 0).
	 */
	public static boolean validarPrecio(String texto) {
		if (texto == null || texto.isEmpty())
			return false;
		try {
			float precio = Float.parseFloat(texto.trim());
			return precio > 0.0f;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Verifica que la cadena contenga SÓLO dígitos numéricos.
	 */
	public static boolean isNumber(String texto) {
		if (texto == null || texto.isEmpty())
			return false;
		// La expresión regular "\\d+" verifica uno o más dígitos
		return Pattern.matches("\\d+", texto.trim());
	}

	/**
	 * Valida el ISBN para la retroalimentación visual (rojo/verde). Verifica que el
	 * texto SÓLO contenga 13 dígitos.
	 */
	public static boolean validateISBN(String isbn) {
		if (isbn == null)
			return false;
		// \\d{13} verifica que la cadena contenga EXACTAMENTE 13 dígitos.
		return Pattern.matches("\\d{13}", isbn.trim());
	}
}