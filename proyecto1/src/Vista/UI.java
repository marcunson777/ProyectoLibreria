package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL; // Importación necesaria

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * Clase principal para la interfaz de usuario de la Librería.
 */
public class UI extends JFrame {

	// --- ATRIBUTOS DE LA INTERFAZ ---
	protected JPanel contentPane;
	protected JPanel panelFormato;

	// Componente de pestañas
	protected JTabbedPane tabbedPane;

	// Panel de botones de gestión (para controlar su visibilidad)
	protected JPanel panelBotonesGestion;

	// Etiquetas
	protected JLabel lblNewLabel;
	protected JLabel lblNewLabel_1;

	// Campos de Texto
	protected JTextField textISBN;
	protected JTextField textTitulo;
	protected JTextField textAutor;
	protected JTextField textEditorial;
	protected JTextField textPrecio;
	protected JTextField textCantidad;

	// Botones
	// Botones Generales de Gestión
	protected JButton btnGuardar;
	protected JButton btnModificar; // Se ha movido a panel de stock
	protected JButton btnConsultar;
	protected JButton btnLimpiar;

	// Botón Universal
	protected JButton btnSalir;

	// Botones de Librería/Stock
	protected JButton btnBorrar;
	protected JButton btnVender;
	protected JButton btnComprar;

	// Radio Buttons
	protected JRadioButton buttonReedicion;
	protected JRadioButton buttonNovedad;
	protected JRadioButton rdbtnEspiral;
	protected JRadioButton rdbtnGrapada;
	protected JRadioButton rdbtnRustico;
	protected JRadioButton rdbtnCartone;

	// Componente de Tabla
	protected JTable tablaLibros;

	// --- CONSTRUCTOR ---
	public UI() {
		inicializarFrame();
		inicializarComponentes();
	}

	// --- MÉTODOS DE INICIALIZACIÓN ---

	private void inicializarFrame() {
		// ¡CORREGIDO! Carga del ícono principal usando el Class Loader.
		// Asume que images-removebg-preview.png está en la carpeta 'Vista'
		URL iconURL = getClass().getResource("/Vista/images-removebg-preview.png");
		if (iconURL != null) {
			setIconImage(Toolkit.getDefaultToolkit().getImage(iconURL));
		} else {
			// Usar un icono por defecto o ignorar si no se encuentra (para evitar fallos)
			System.err.println("Icono de ventana no encontrado en /Vista/images-removebg-preview.png");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 750);

		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
	}

	private void inicializarComponentes() {
		crearPanelSuperior();
		crearTabbedPaneCentral();
		crearPanelInferiorGeneral(); // Contiene el panel de gestión y el botón SALIR
	}

	private void crearPanelSuperior() {
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(new Color(8, 31, 92));
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel = new JLabel("LIBRERIA DE MARCOS");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		panelSuperior.add(lblNewLabel);
	}

	private void crearTabbedPaneCentral() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(192, 192, 192));
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panelLibro = crearPanelLibro();
		tabbedPane.addTab("LIBRO", null, panelLibro, null);

		JPanel panelLibreria = crearPanelLibreria();
		tabbedPane.addTab("LIBRERIA", null, panelLibreria, null);
	}

	private JPanel crearPanelLibro() {
		JPanel panelLibro = new JPanel();
		panelLibro.setBackground(new Color(221, 160, 221));
		panelLibro.setLayout(new BorderLayout());

		JPanel panelCampos = new JPanel(new GridLayout(7, 2, 10, 30));
		panelCampos.setBackground(new Color(112, 150, 209));

		crearCamposYEtiquetasLibro(panelCampos);

		JPanel panelEstado = new JPanel();
		panelEstado.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFormato = new JPanel();
		panelFormato.setBorder(new LineBorder(new Color(0, 0, 0)));

		inicializarRadioButtons(panelEstado, panelFormato);

		panelCampos.add(panelFormato);
		panelCampos.add(panelEstado);

		panelLibro.add(panelCampos, BorderLayout.CENTER);
		return panelLibro;
	}

	private void crearCamposYEtiquetasLibro(JPanel panel) {

		// CLASE CORREGIDA: Usa ClassLoader para cargar desde el classpath
		class IconLabel extends JLabel {
			public IconLabel(String text, String path) {
				super(text);
				URL imageURL = getClass().getResource(path);
				if (imageURL != null) {
					ImageIcon icon = new ImageIcon(imageURL);
					Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
					setIcon(new ImageIcon(img));
				} else {
					System.err.println("Icono no encontrado en la ruta: " + path);
				}
				setHorizontalAlignment(JLabel.RIGHT);
				setFont(new Font("Tahoma", Font.PLAIN, 18));
			}
		}

		class TextFieldPanel extends JPanel {
			public TextFieldPanel(JTextField field) {
				super(new FlowLayout(FlowLayout.LEFT));
				setBackground(new Color(112, 150, 209));
				add(field);
			}
		}

		// ISBN - RUTA CORREGIDA
		lblNewLabel_1 = new IconLabel("ISBN:", "/img/pngtree-isbn-barcodes-vector-png-image_7108020.png");
		textISBN = new JTextField(20);
		panel.add(lblNewLabel_1);
		panel.add(new TextFieldPanel(textISBN));

		// TÍTULO - RUTA CORREGIDA
		JLabel lblTitulo = new IconLabel("TÍTULO:", "/img/4693065.png");
		textTitulo = new JTextField(20);
		panel.add(lblTitulo);
		panel.add(new TextFieldPanel(textTitulo));

		// AUTOR - RUTA CORREGIDA
		JLabel lblAutor = new IconLabel("AUTOR:", "/img/17_19005_73815130-b378-4be6-8e8a-cc581811bd54.png");
		textAutor = new JTextField(20);
		panel.add(lblAutor);
		panel.add(new TextFieldPanel(textAutor));

		// EDITORIAL - RUTA CORREGIDA
		JLabel lblEditorial = new IconLabel("EDITORIAL:",
				"/img/pngtree-editorial-line-icon-vector-png-image_6649706.png");
		textEditorial = new JTextField(20);
		panel.add(lblEditorial);
		panel.add(new TextFieldPanel(textEditorial));

		// PRECIO - RUTA CORREGIDA
		JLabel lblPrecio = new IconLabel("PRECIO:", "/img/126169.png");
		textPrecio = new JTextField(20);
		panel.add(lblPrecio);
		panel.add(new TextFieldPanel(textPrecio));

		// CANTIDAD - RUTA CORREGIDA
		JLabel lblCantidad = new IconLabel("CANTIDAD:", "/img/14184.png");
		textCantidad = new JTextField(20);
		panel.add(lblCantidad);
		panel.add(new TextFieldPanel(textCantidad));
	}

	private void inicializarRadioButtons(JPanel panelEstado, JPanel panelFormato) {
		Font radioFont = new Font("Tahoma", Font.PLAIN, 22);

		ButtonGroup grupoFormato = new ButtonGroup();
		ButtonGroup grupoEstado = new ButtonGroup();

		// Estado
		buttonReedicion = new JRadioButton("Reedición");
		buttonReedicion.setFont(radioFont);
		buttonNovedad = new JRadioButton("Novedad");
		buttonNovedad.setFont(radioFont);
		grupoEstado.add(buttonReedicion);
		grupoEstado.add(buttonNovedad);
		panelEstado.add(buttonReedicion);
		panelEstado.add(buttonNovedad);

		// Formato
		rdbtnCartone = new JRadioButton("Cartoné");
		rdbtnCartone.setFont(radioFont);
		rdbtnRustico = new JRadioButton("Rústica");
		rdbtnRustico.setFont(radioFont);
		rdbtnGrapada = new JRadioButton("Grapada");
		rdbtnGrapada.setFont(radioFont);
		rdbtnEspiral = new JRadioButton("Espiral");
		rdbtnEspiral.setFont(radioFont);
		grupoFormato.add(rdbtnCartone);
		grupoFormato.add(rdbtnRustico);
		grupoFormato.add(rdbtnGrapada);
		grupoFormato.add(rdbtnEspiral);
		panelFormato.add(rdbtnCartone);
		panelFormato.add(rdbtnRustico);
		panelFormato.add(rdbtnGrapada);
		panelFormato.add(rdbtnEspiral);

		// Seleccionar uno por defecto
		rdbtnCartone.setSelected(true);
		buttonNovedad.setSelected(true);
	}

	private JPanel crearPanelLibreria() {
		JPanel panelLibreria = new JPanel();
		panelLibreria.setBackground(new Color(0, 64, 128));
		panelLibreria.setLayout(new BorderLayout());

		tablaLibros = new JTable();
		tablaLibros.setBackground(new Color(255, 255, 255));
		JScrollPane scrollLibros = new JScrollPane(tablaLibros);
		panelLibreria.add(scrollLibros, BorderLayout.CENTER);

		// Panel para los botones de Stock (MODIFICAR, BORRAR, COMPRAR, VENDER)
		JPanel panelBotonesStock = crearPanelBotonesStock();
		panelLibreria.add(panelBotonesStock, BorderLayout.SOUTH);

		return panelLibreria;
	}

	/**
	 * Crea los botones específicos de gestión de Stock/Librería.
	 */
	private JPanel crearPanelBotonesStock() {
		JPanel panelBotonesStock = new JPanel();
		panelBotonesStock.setBackground(new Color(8, 31, 92));

		// CLASE CORREGIDA: Usa ClassLoader para cargar desde el classpath
		class IconeButton extends JButton {
			public IconeButton(String text, String path) {
				super(text);
				URL imageURL = getClass().getResource(path);

				if (imageURL != null) {
					ImageIcon icon = new ImageIcon(imageURL);
					Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					setIcon(new ImageIcon(img));
				} else {
					System.err.println("Icono de botón no encontrado en la ruta: " + path);
				}
			}
		}

		// MODIFICAR (Nuevo: por selección, carga datos a pestaña LIBRO) - RUTA
		// CORREGIDA
		btnModificar = new IconeButton("MODIFICAR", "/img/2570442.png");
		panelBotonesStock.add(btnModificar);

		// BORRAR - RUTA CORREGIDA
		btnBorrar = new IconeButton("BORRAR", "/img/1345874.png");
		panelBotonesStock.add(btnBorrar);

		// COMPRAR - RUTA CORREGIDA
		btnComprar = new IconeButton("COMPRAR", "/img/2649150.png");
		panelBotonesStock.add(btnComprar);

		// VENDER - RUTA CORREGIDA
		btnVender = new IconeButton("VENDER", "/img/1992622.png");
		panelBotonesStock.add(btnVender);

		return panelBotonesStock;
	}

	/**
	 * Crea el contenedor inferior, dividiendo los botones de GESTIÓN y SALIR.
	 */
	private void crearPanelInferiorGeneral() {
		JPanel panelContenedorInferior = new JPanel(new BorderLayout());
		panelContenedorInferior.setBackground(new Color(8, 31, 92));
		contentPane.add(panelContenedorInferior, BorderLayout.SOUTH);

		// 1. Panel Izquierdo: Botones de GESTIÓN (Guardar, Consultar, Limpiar)
		panelBotonesGestion = crearPanelBotonesGestion(); // Asignamos a la variable de instancia
		panelContenedorInferior.add(panelBotonesGestion, BorderLayout.WEST);

		// 2. Panel Derecho: Botón SALIR (Siempre visible)
		JPanel panelSalir = crearPanelBotonSalir();
		panelContenedorInferior.add(panelSalir, BorderLayout.EAST);
	}

	/**
	 * Crea los botones de gestión General (Guardar, Consultar, Limpiar).
	 */
	private JPanel crearPanelBotonesGestion() {
		JPanel panelGestion = new JPanel();
		panelGestion.setBackground(new Color(8, 31, 92));

		// CLASE CORREGIDA: Usa ClassLoader para cargar desde el classpath
		class IconeButton extends JButton {
			public IconeButton(String text, String path) {
				super(text);
				URL imageURL = getClass().getResource(path);

				if (imageURL != null) {
					ImageIcon icon = new ImageIcon(imageURL);
					Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
					setIcon(new ImageIcon(img));
				} else {
					System.err.println("Icono de botón no encontrado en la ruta: " + path);
				}
			}
		}

		// GUARDAR - RUTA CORREGIDA
		btnGuardar = new IconeButton("GUARDAR", "/img/4856668.png");
		panelGestion.add(btnGuardar);

		// CONSULTAR - RUTA CORREGIDA
		btnConsultar = new IconeButton("CONSULTAR", "/img/4715177.png");
		panelGestion.add(btnConsultar);

		// LIMPIAR - RUTA CORREGIDA
		btnLimpiar = new IconeButton("LIMPIAR", "/img/Clean-Download-Transparent-PNG-Image.png");
		panelGestion.add(btnLimpiar);

		return panelGestion;
	}

	/**
	 * Crea el panel con el botón SALIR (universal).
	 */
	private JPanel crearPanelBotonSalir() {
		JPanel panelSalir = new JPanel();
		panelSalir.setBackground(new Color(8, 31, 92));

		// SALIR - RUTA CORREGIDA (Carga manual sin IconeButton)
		URL urlSalir = getClass().getResource("/img/salir.png");
		btnSalir = new JButton("SALIR");

		if (urlSalir != null) {
			ImageIcon iconSalir = new ImageIcon(urlSalir);
			Image imgSalir = iconSalir.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
			btnSalir.setIcon(new ImageIcon(imgSalir));
		} else {
			System.err.println("Icono de salir no encontrado en /Controlador/img/salir.png");
		}

		panelSalir.add(btnSalir);

		return panelSalir;
	}
}