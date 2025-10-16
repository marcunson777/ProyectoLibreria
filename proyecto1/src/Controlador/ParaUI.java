package Controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Modelo.Libro;
import Validaciones.Validaciones;
import Vista.UI;

public class ParaUI extends UI {
	private static final long serialVersionUID = 7101499288008613647L;
	private Libreria libreria;

	public ParaUI() {
		super();
		libreria = new Libreria();
		generarLibreria();

		if (textISBN != null) {
			textISBN.setForeground(Color.RED);
		}

		activarListenerPestanas(); // Controla la visibilidad de panelBotonesGestion

		// Botón Guardar GUARDA NUEVO y ACTUALIZA
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textISBN.getText().isEmpty() || textTitulo.getText().isEmpty() || textAutor.getText().isEmpty()
						|| textEditorial.getText().isEmpty() || textPrecio.getText().isEmpty()
						|| textCantidad.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos", "Error de Formulario",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Validaciones
				String isbnLimpio = textISBN.getText().replaceAll("[^0-9]", "").trim();
				if (!Validaciones.validarISBN(isbnLimpio) || !Validaciones.validarPrecio(textPrecio.getText())
						|| !Validaciones.validarCantidad(textCantidad.getText())
						|| !Validaciones.validarLetras(textAutor.getText())
						|| !Validaciones.validarLetras(textEditorial.getText())) {
					JOptionPane.showMessageDialog(null,
							"Error en los datos. Revise ISBN (13 dígitos), Precio, Cantidad, Autor y Editorial.",
							"Error de Validación", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Extracción de datos
				float precio = Float.parseFloat(textPrecio.getText());
				int cantidad = Integer.parseInt(textCantidad.getText());
				Libro libro = new Libro(isbnLimpio, textTitulo.getText(), textAutor.getText(), textEditorial.getText(),
						precio, getRadioButtonFormato(), getRadioButtonEstado(), cantidad);

				// Lógica de Negocio: Guardar o Actualizar
				if (libreria.comprobarIsbnExistente(isbnLimpio)) {
					
					// 🚨 RESTRICCIÓN 1: No se permite cambiar la cantidad al modificar 
					Libro libroOriginal = libreria.obtenerLibroPorISBN(isbnLimpio);
					
					if (libroOriginal != null && libroOriginal.getCantidad() != cantidad) {
						JOptionPane.showMessageDialog(null, 
								"ERROR: No se permite cambiar la cantidad de stock (" + libroOriginal.getCantidad() + ") al modificar el libro.\n"
								+ "Utilice los botones 'Vender' o 'Comprar' en la pestaña LIBRERIA para ajustar el stock.", 
								"Restricción de Modificación", 
								JOptionPane.ERROR_MESSAGE);
						return; // Detiene la actualización
					}
					
					// Si existe, se actualiza (Y la cantidad es la misma)
					if (libreria.actualizarLibro(isbnLimpio, libro)) {
						JOptionPane.showMessageDialog(null, "Libro actualizado correctamente", "Éxito",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar el libro.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					// Si no existe, se añade
					libreria.agregarLibro(libro);
					JOptionPane.showMessageDialog(null, "Nuevo libro guardado correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
				}

				libreria.rellenarTabla(tablaLibros);
				handleLimpiarCampos();
			}
		});

		// Botón Modificar (CARGA DATOS A PESTAÑA LIBRO)
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleCargarDatosParaModificar();
			}
		});

		// Botón Borrar
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indice = tablaLibros.getSelectedRow();
				if (indice >= 0) {
					int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar este libro?",
							"Confirmar borrado", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION) {
						libreria.borrarLibros(indice);
						libreria.rellenarTabla(tablaLibros);
						JOptionPane.showMessageDialog(null, "Libro borrado con éxito");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Seleccione un libro para borrar de la pestaña LIBRERIA",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Botón Consultar
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ISBNsel = JOptionPane.showInputDialog("Introduce ISBN del libro a consultar/modificar:");
				if (ISBNsel == null || ISBNsel.isEmpty())
					return;

				String isbnLimpio = ISBNsel.replaceAll("[^0-9]", "");
				Libro libro = libreria.obtenerLibroPorISBN(isbnLimpio); // Usando método renombrado

				if (libro != null) {

					handleCargarDatosDesdeObjeto(libro);
					tabbedPane.setSelectedIndex(0); // Mueve a pestaña LIBRO

					JOptionPane.showMessageDialog(null,
							"Datos del libro '" + libro.getTitulo() + "' cargados en la pestaña LIBRO.\n"
									+ "Puede modificar los campos y pulsar GUARDAR para aplicar los cambios.",
							"Información y Modificación", JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(null, "No se encontró ningún libro con ese ISBN", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Botón Vender
		btnVender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleVenderLibroSeleccionado();
			}
		});

		// Botón Comprar
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleComprarLibroSeleccionado();
			}
		});

		// Botón Limpiar
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleLimpiarCampos();
			}
		});

		// Botón Salir
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// listeners

		// Listener para el campo ISBN (Control de longitud y dígitos)
		textISBN.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// Usando validateISBN del código original
				String isbnLimpio = textISBN.getText().replaceAll("[^0-9]", "");
				if (Validaciones.validateISBN(isbnLimpio)) {
					textISBN.setForeground(Color.GREEN);
				} else {
					textISBN.setForeground(Color.RED);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				String isbnActualLimpio = textISBN.getText().replaceAll("[^0-9]", "");
				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
				if (isbnActualLimpio.length() >= 13 && Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		
		// Listener para el campo CANTIDAD (Solo permite dígitos)
		textCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				// Solo permite dígitos (0-9) y la tecla de retroceso (borrar)
				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
					e.consume(); // Consume (ignora) el evento de la tecla
				}
			}
		});
		
		// 🆕 Listener para el campo PRECIO (Solo permite dígitos y un punto decimal)
		textPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				String textoActual = textPrecio.getText();
				
				// Permitir solo dígitos, el punto decimal (.), y la tecla de borrado (backspace)
				if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
					e.consume(); // Ignora cualquier otra cosa (letras, símbolos)
					return;
				}
				
				// Restringir el punto decimal: solo permitirlo si no existe ya uno
				if (c == '.') {
					if (textoActual.contains(".")) {
						e.consume(); // Si ya hay un punto, ignora el nuevo punto
					}
				}
			}
		});
	}

	// Control de Visibilidad de Botones

	private void activarListenerPestanas() {
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedIndex = tabbedPane.getSelectedIndex();

				// Índice 0 es "LIBRO"
				if (selectedIndex == 0) {
					// Pestaña LIBRO: Muestra el panel de botones de GESTIÓN
					panelBotonesGestion.setVisible(true);
				}
				// Índice 1 es "LIBRERIA"
				else if (selectedIndex == 1) {
					// Pestaña LIBRERIA: Oculta el panel de botones de GESTIÓN.
					panelBotonesGestion.setVisible(false);
				}
				contentPane.revalidate();
				contentPane.repaint();
			}
		});

		// Inicializar al empezar
		tabbedPane.setSelectedIndex(0);
		panelBotonesGestion.setVisible(true);
	}

	// Métodos Auxiliares

	private void handleCargarDatosParaModificar() {
		int indice = tablaLibros.getSelectedRow();

		if (indice < 0) {
			JOptionPane.showMessageDialog(null, "Seleccione un libro de la tabla para modificar.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			// Usamos el ISBN de la fila para obtener el objeto Libro
			String isbn = (String) tablaLibros.getValueAt(indice, 0);
			Libro libro = libreria.obtenerLibroPorISBN(isbn); // Usando método renombrado

			if (libro == null) {
				JOptionPane.showMessageDialog(null, "Error interno: No se encontró el libro seleccionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Cargar los datos a los campos de texto
			handleCargarDatosDesdeObjeto(libro);

			// Cambiar a la pestaña "LIBRO" (Índice 0)
			tabbedPane.setSelectedIndex(0);

			// Informar al usuario
			JOptionPane.showMessageDialog(null,
					"Datos del libro '" + libro.getTitulo() + "' cargados en la pestaña LIBRO.\n"
							+ "Realice los cambios necesarios y pulse el botón GUARDAR para aplicar la modificación.",
					"Modificación en Curso", JOptionPane.INFORMATION_MESSAGE);

		} catch (ClassCastException ex) {
			JOptionPane.showMessageDialog(null, "Error al procesar la información de la tabla.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

//vender un libro de la tabla
	private void handleVenderLibroSeleccionado() {
		int indice = tablaLibros.getSelectedRow();

		if (indice < 0) {
			JOptionPane.showMessageDialog(null, "Seleccione un libro para vender de la pestaña LIBRERIA", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			// Usamos el ISBN de la fila para obtener el objeto Libro
			String isbn = (String) tablaLibros.getValueAt(indice, 0);
			Libro libro = libreria.obtenerLibroPorISBN(isbn);

			if (libro == null) {
				JOptionPane.showMessageDialog(null, "Error interno: No se encontró el libro seleccionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			String strCantidad = JOptionPane
					.showInputDialog("Introduce cantidad a vender para '" + libro.getTitulo() + "':");

			if (strCantidad == null || strCantidad.isEmpty()) {
				return;
			}

			// Usando Validaciones.isNumber para verificar que sea un número
			if (!Validaciones.isNumber(strCantidad)) {
				JOptionPane.showMessageDialog(null, "Cantidad a vender inválida (debe ser un número entero > 0)",
						"Error de Validación", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int cantidad = Integer.parseInt(strCantidad);
			if (cantidad <= 0) {
				JOptionPane.showMessageDialog(null, "Cantidad a vender debe ser mayor que 0.", "Error de Validación",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Llama a la lógica de venta (MOVIDO A LIBRERIA)
			if (libreria.venderLibro(isbn, cantidad)) {
				double total = libro.getPrecio() * cantidad;

				libreria.rellenarTabla(tablaLibros); // Actualizar la tabla
				JOptionPane.showMessageDialog(null,
						"Venta realizada correctamente. Total: " + String.format("%.2f", total) + " €");
			} else {
				JOptionPane.showMessageDialog(null, "No hay suficiente stock para vender " + cantidad + " unidades.",
						"Error de Stock", JOptionPane.WARNING_MESSAGE);
			}

		} catch (NumberFormatException | ClassCastException ex) {
			JOptionPane.showMessageDialog(null, "Error al procesar la información de la tabla.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Maneja la lógica de comprar (incrementar stock) de un libro seleccionado en
	 * la tabla. (Lógica movida a Libreria)
	 */
	private void handleComprarLibroSeleccionado() {
		int indice = tablaLibros.getSelectedRow();

		if (indice < 0) {
			JOptionPane.showMessageDialog(null,
					"Seleccione un libro para comprar (aumentar stock) de la pestaña LIBRERIA", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			// Usamos el ISBN de la fila para obtener el objeto Libro
			String isbn = (String) tablaLibros.getValueAt(indice, 0);
			Libro libro = libreria.obtenerLibroPorISBN(isbn); // Usando método renombrado

			if (libro == null) {
				JOptionPane.showMessageDialog(null, "Error interno: No se encontró el libro seleccionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Pedir la cantidad a comprar (aumentar stock)
			String strCantidad = JOptionPane
					.showInputDialog("Introduce cantidad a COMPRAR para '" + libro.getTitulo() + "':");

			if (strCantidad == null || strCantidad.isEmpty()) {
				return;
			}

			// Usando Validaciones.isNumber para verificar que sea un número
			if (!Validaciones.isNumber(strCantidad)) {
				JOptionPane.showMessageDialog(null, "Cantidad a comprar inválida (debe ser un número entero > 0)",
						"Error de Validación", JOptionPane.ERROR_MESSAGE);
				return;
			}

			int cantidad = Integer.parseInt(strCantidad);
			if (cantidad <= 0) {
				JOptionPane.showMessageDialog(null, "Cantidad a comprar debe ser mayor que 0.", "Error de Validación",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Aumentar el stock del libro (Lógica movida a Libreria)
			if (libreria.comprarLibro(isbn, cantidad)) {
				libreria.rellenarTabla(tablaLibros); // Actualizar la tabla
				JOptionPane.showMessageDialog(null, "Compra registrada correctamente. Stock de " + libro.getTitulo()
						+ " aumentado en " + cantidad + " unidades.");
			} else {
				JOptionPane.showMessageDialog(null, "Error al aumentar el stock.", "Error de Stock",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (NumberFormatException | ClassCastException ex) {
			JOptionPane.showMessageDialog(null, "Error al procesar la información de la tabla.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleLimpiarCampos() {
		textISBN.setText("");
		textTitulo.setText("");
		textAutor.setText("");
		textEditorial.setText("");
		textPrecio.setText("");
		textCantidad.setText("");
		textISBN.setForeground(Color.RED);
		rdbtnCartone.setSelected(true); // Seleccionar por defecto
		buttonNovedad.setSelected(true); // Seleccionar por defecto
		
		// 🚨 RESTRICCIÓN 2: Habilitar la edición de la cantidad para agregar un nuevo libro
		textCantidad.setEditable(true);
	}

	private void handleCargarDatosDesdeObjeto(Libro libro) {
		textISBN.setText(libro.getISBN());
		textTitulo.setText(libro.getTitulo());
		textAutor.setText(libro.getAutor());
		textEditorial.setText(libro.getEditorial());
		textPrecio.setText(String.valueOf(libro.getPrecio()));
		textCantidad.setText(String.valueOf(libro.getCantidad()));

		// 🚨 RESTRICCIÓN 2: Deshabilitar la edición de la cantidad al modificar
		textCantidad.setEditable(false);

		textISBN.setForeground(Color.GREEN);

		seleccionarRadioButton(libro.getFormato(), libro.getEstado());
	}

	private void seleccionarRadioButton(String formato, String estado) {
		if ("Cartoné".equals(formato))
			rdbtnCartone.setSelected(true);
		else if ("Rústica".equals(formato))
			rdbtnRustico.setSelected(true);
		else if ("Grapada".equals(formato))
			rdbtnGrapada.setSelected(true);
		else if ("Espiral".equals(formato))
			rdbtnEspiral.setSelected(true);

		if ("Novedad".equals(estado))
			buttonNovedad.setSelected(true);
		else if ("Reedición".equals(estado))
			buttonReedicion.setSelected(true);
	}

	public String getRadioButtonFormato() {
		if (rdbtnCartone.isSelected())
			return rdbtnCartone.getText();
		if (rdbtnEspiral.isSelected())
			return rdbtnEspiral.getText();
		if (rdbtnGrapada.isSelected())
			return rdbtnGrapada.getText();
		if (rdbtnRustico.isSelected())
			return rdbtnRustico.getText();
		return null;
	}

	public String getRadioButtonEstado() {
		if (buttonNovedad.isSelected())
			return buttonNovedad.getText();
		if (buttonReedicion.isSelected())
			return buttonReedicion.getText();
		return null;
	}

	private void generarLibreria() {
		libreria.agregarLibro(new Libro("1111111111111", "La grieta del silencio", "Javier Castillo", "Suma de Letras",
				20.80f, "Tapa dura", "Novedad", 15));
		libreria.agregarLibro(new Libro("2222222222222", "Alas de hierro (Empíreo 2)", "Rebecca Yarros", "Planeta",
				22.70f, "Tapa blanda", "Novedad", 10));
		libreria.agregarLibro(new Libro("3333333333333", "Las hijas de la criada (Premio Planeta 2023)",
				"Sonsoles Ónega", "Planeta", 21.75f, "Tapa dura", "Reedición", 12));
		libreria.agregarLibro(new Libro("4444444444444", "Un animal salvaje", "Joël Dicker", "Alfaguara", 21.90f,
				"Rústica", "Novedad", 8));
		libreria.agregarLibro(new Libro("5555555555555", "Recupera tu mente, reconquista tu vida",
				"Marian Rojas Estapé", "Espasa", 18.90f, "Bolsillo", "Reedición", 20));

		libreria.rellenarTabla(tablaLibros);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			ParaUI frame = new ParaUI();
			frame.setVisible(true);
		});
	}
}