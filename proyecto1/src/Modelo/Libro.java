package Modelo;

public class Libro {
	private String ISBN;
	private String Titulo;
	private String Autor;
	private String Editorial;
	private float Precio;
	private String Formato;
	private String estado;
	private int Cantidad;

	public Libro(String iSBN, String titulo, String autor, String editorial, float precio, String formato,
			String estado, int cantidad) {
		super();
		ISBN = iSBN;
		Titulo = titulo;
		Autor = autor;
		Editorial = editorial;
		Precio = precio;
		Formato = formato;
		this.estado = estado;
		Cantidad = cantidad;
	}
	

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		Titulo = titulo;
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		Autor = autor;
	}

	public String getEditorial() {
		return Editorial;
	}

	public void setEditorial(String editorial) {
		Editorial = editorial;
	}

	public float getPrecio() {
		return Precio;
	}

	public void setPrecio(float precio) {
		Precio = precio;
	}

	public String getFormato() {
		return Formato;
	}

	public void setFormato(String formato) {
		Formato = formato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getCantidad() {
		return Cantidad;
	}

	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}

	@Override
	public String toString() {
		return ISBN + " | " + Titulo + " | " + Autor + " | " + Editorial + " | " + Precio + "€ | " + Formato + " | "
				+ estado + " | Cantidad: " + Cantidad;
	}

}