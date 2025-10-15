package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class UI2 extends JFrame {

	protected static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	protected JLabel lblNewLabel;
	protected JLabel lblNewLabel_1;
	protected JTextField textISBN;
	protected JTextField textTitulo;
	protected JTextField textAutor;
	protected JTextField textEditorial;
	protected JTextField textPrecio;
	protected JTable tablaLibros;
	protected JButton btnGuardar; // ahora sí se usan los atributos
	protected JButton btnSalir;
	protected JButton btnBorrar;
	protected JButton btnConsultar;
	protected JButton btnLimpiar;

	public UI2() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\mario\\OneDrive\\Escritorio\\adelaida\\proyecto1\\images-removebg-preview.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 597, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// PANEL SUPERIOR
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(new Color(151, 252, 151));
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblNewLabel = new JLabel("LIBRERIA DE MARCOS");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		panelSuperior.add(lblNewLabel);

		// PANEL INFERIOR
		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(new Color(151, 252, 151));
		contentPane.add(panelInferior, BorderLayout.SOUTH);

		// ✅ aquí ya no declaramos botones nuevos, usamos los atributos
		btnGuardar = new JButton("GUARDAR");
		panelInferior.add(btnGuardar);

		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(e -> dispose());
		panelInferior.add(btnSalir);

		btnBorrar = new JButton("BORRAR");
		panelInferior.add(btnBorrar);

		btnConsultar = new JButton("CONSULTAR");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panelInferior.add(btnConsultar);

		btnLimpiar = new JButton("LIMPIAR");
		panelInferior.add(btnLimpiar);

		// TABBED PANE
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(192, 192, 192));
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel Libro = new JPanel();
		Libro.setBackground(new Color(221, 160, 221));
		tabbedPane.addTab("LIBRO", null, Libro, null);

		lblNewLabel_1 = new JLabel("ISBN:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblTitulo = new JLabel("TÍTULO:");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblAutor = new JLabel("AUTOR:");
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblEditorial = new JLabel("EDITORIAL:");
		lblEditorial.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel lblPrecio = new JLabel("PRECIO:");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 18));

		textISBN = new JTextField();
		textISBN.setColumns(10);

		textTitulo = new JTextField();
		textTitulo.setColumns(10);

		textAutor = new JTextField();
		textAutor.setColumns(10);

		textEditorial = new JTextField();
		textEditorial.setColumns(10);

		textPrecio = new JTextField();
		textPrecio.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(
				"C:\\Users\\mario\\OneDrive\\Escritorio\\adelaida\\proyecto1\\images-removebg-preview.png"));

		GroupLayout gl_Libro = new GroupLayout(Libro);
		gl_Libro.setHorizontalGroup(gl_Libro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Libro.createSequentialGroup().addContainerGap()
						.addGroup(gl_Libro.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_Libro.createSequentialGroup()
										.addGroup(gl_Libro.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_Libro.createSequentialGroup().addComponent(lblEditorial)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textEditorial, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_Libro.createSequentialGroup().addComponent(lblPrecio)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(textPrecio, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
										.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 230,
												GroupLayout.PREFERRED_SIZE)
										.addGap(106))
								.addGroup(gl_Libro.createSequentialGroup().addGroup(gl_Libro
										.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_Libro.createSequentialGroup().addComponent(lblTitulo)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_Libro.createSequentialGroup().addComponent(lblNewLabel_1)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(textISBN, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(14)))
										.addContainerGap(458, Short.MAX_VALUE))
								.addGroup(
										gl_Libro.createSequentialGroup().addComponent(lblAutor)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(textAutor, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addContainerGap()))));
		gl_Libro.setVerticalGroup(gl_Libro.createParallelGroup(Alignment.LEADING).addGroup(gl_Libro
				.createSequentialGroup().addGap(23)
				.addGroup(gl_Libro.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(
						textISBN, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_Libro.createParallelGroup(Alignment.LEADING)
						.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTitulo))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_Libro.createParallelGroup(Alignment.LEADING).addComponent(lblAutor).addComponent(textAutor,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_Libro
						.createParallelGroup(Alignment.LEADING).addComponent(lblEditorial).addComponent(textEditorial,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_Libro.createParallelGroup(Alignment.LEADING).addComponent(lblPrecio).addComponent(
						textPrecio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(326, Short.MAX_VALUE))
				.addGroup(gl_Libro.createSequentialGroup().addGap(123)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(173, Short.MAX_VALUE)));
		Libro.setLayout(gl_Libro);

		JPanel Libreria = new JPanel();
		Libreria.setBackground(new Color(255, 255, 128));
		tabbedPane.addTab("LIBRERIA", null, Libreria, null);
		Libreria.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		Libreria.add(scrollPane);

		tablaLibros = new JTable();
		scrollPane.setViewportView(tablaLibros);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UI frame = new UI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
