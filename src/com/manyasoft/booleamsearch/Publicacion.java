package com.manyasoft.booleamsearch;

public class Publicacion {
	
	private String tiulo;
	private String autor;
	private String resumen;
	private String imagen;
	private String rating;
	
	public Publicacion(){}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getTiulo() {
		return tiulo;
	}

	public void setTiulo(String tiulo) {
		this.tiulo = tiulo;
	}
}
