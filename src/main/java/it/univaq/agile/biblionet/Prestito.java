package it.univaq.agile.biblionet;

// Un prestito collega una persona a un libro
public class Prestito {

	private final Persona persona;
	private final Libro libro;

	public Prestito(Persona persona, Libro libro) {
		this.persona = persona;
		this.libro = libro;
	}

	public Persona getPersona() {
		return persona;
	}

	public Libro getLibro() {
		return libro;
	}
}