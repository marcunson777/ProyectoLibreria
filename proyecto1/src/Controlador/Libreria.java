package Controlador;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modelo.Libro;

public class Libreria {
	private ArrayList<Libro> listaLibros; // Renombrado de arrayLibro a listaLibros

	public Libreria() {
		listaLibros = new ArrayList<>();

	}

	public void mostrarlibros() {
		System.out.println("----- LISTA DE LIBROS -----");
		for (Libro libro : listaLibros) {
			System.out.println(libro.toString());
		}
	}

	public void agregarLibro(Libro libro) {
		listaLibros.add(libro);
	}

	public ArrayList<Libro> getListaLibros() {
		return listaLibros;
	}

	/**
	 * Rellena la JTable con los datos de la colección de libros. NOTA: Mover este
	 * método al Controlador (ParaUI) es una mejor práctica.
	 */
	public void rellenarTabla(JTable tablaLibros) {
		String[] nombresColumnas = { "ISBN", "TITULO", "AUTOR", "EDITORIAL", "PRECIO", "FORMATO", "ESTADO",
				"CANTIDAD" };
		DefaultTableModel modelo = new DefaultTableModel(nombresColumnas, 0);

		for (Libro libro : listaLibros) {
			Object[] fila = { libro.getISBN(), libro.getTitulo(), libro.getAutor(), libro.getEditorial(),
					libro.getPrecio(), libro.getFormato(), libro.getEstado(), libro.getCantidad() };
			modelo.addRow(fila);
		}

		tablaLibros.setModel(modelo);
	}

	// Método eliminado: obtenerIdSelccionado (Lógica de tabla en el Modelo es
	// acoplamiento)

	// MANTENIDO: Borrado por índice de ArrayList (usado en ParaUI original)
	public void borrarLibros(int indice) {
		listaLibros.remove(indice);
	}

	/**
	 * Busca y devuelve un libro por su ISBN. (Renombrado de obtenerLibroDos)
	 */
	public Libro obtenerLibroPorISBN(String isbn) {
		for (Libro libro : listaLibros) {
			if (libro.getISBN().equals(isbn)) {
				return libro;
			}
		}
		return null;
	}

	public boolean comprobarIsbnExistente(String isbn) {
		return obtenerLibroPorISBN(isbn) != null;
	}

	/**
	 * Regla de Negocio: Gestiona la venta (resta stock).
	 */
	public boolean venderLibro(String ISBN, int cantidad) {
		Libro libro = obtenerLibroPorISBN(ISBN);

		if (libro != null) {
			if (libro.getCantidad() >= cantidad) {
				libro.setCantidad(libro.getCantidad() - cantidad); // Restar cantidad vendida
				return true; // Venta realizada
			} else {
				return false; // No hay suficiente stock
			}
		}
		return false; // No se encontró el libro
	}

	/**
	 * Regla de Negocio: Aumenta el stock del libro (Compra/Reposición).
	 */
	public boolean comprarLibro(String isbn, int cantidad) {
		Libro libro = obtenerLibroPorISBN(isbn);
		if (libro != null) {
			libro.setCantidad(libro.getCantidad() + cantidad);
			return true;
		}
		return false;
	}

	/**
	 * Reemplaza el libro antiguo por el nuevo usando el ISBN como clave. Se usa
	 * para la funcionalidad GUARDAR/ACTUALIZAR en ParaUI.
	 */
	public boolean actualizarLibro(String isbnAntiguo, Libro libroNuevo) {
		for (int i = 0; i < listaLibros.size(); i++) {
			Libro libroActual = listaLibros.get(i);
			if (libroActual.getISBN().equals(isbnAntiguo)) {
				listaLibros.set(i, libroNuevo);
				return true;
			}
		}
		return false;
	}

}