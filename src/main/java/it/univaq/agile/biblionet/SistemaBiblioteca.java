package it.univaq.agile.biblionet;

import java.util.*;

public class SistemaBiblioteca {
	// Contatore progressivo per le tessere. Parte da 1 e aumenta di 1 ogni volta che qualcuno si registra.
	private int contatoreTessere = 1;
	// Rubrica delle persone registrate: si cerca con il codice della tessera
	private Map<String, Persona> persone = new HashMap<>();
	// Il catalogo dei libri della biblioteca
	private Catalogo catalogo;
	// Prestiti in corso: si cerca con l'ISBN del libro prestato
	private Map<String, Prestito> prestitiAttivi = new HashMap<>();

	// Costruttore senza catalogo: ne crea uno vuoto (così i test di registrazione continuano a funzionare)
	public SistemaBiblioteca() {
		this(new Catalogo(new ArrayList<>()));
	}

	// Costruttore con il catalogo della biblioteca
	public SistemaBiblioteca(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	// Registrazione di una persona
	public Persona registra(String nome, String cognome, Ruolo ruolo) {
		// Crea il codice della tessera unendo prefisso e contatore formattato a 4 cifre (poiché sono circa 6000 iscritti)
		String codice = ruolo.getPrefisso() + String.format("%04d", contatoreTessere++);
		// Crea la nuova persona e la salva nella rubrica
		Persona persona = new Persona(nome, cognome, ruolo, codice);
		persone.put(codice, persona);
		// Restituisce la persona registrata
		return persona;
	}

	// Cerca una persona con il codice della tessera. Se non esiste restituisce null
	public Persona trovaPersona(String codiceTessera) {
		return persone.get(codiceTessera);
	}

	// Registra il prestito: associa la persona al libro, se è possibile
	public Prestito registraPrestito(String codiceTessera, String isbn) {
		Persona persona = trovaPersona(codiceTessera);
		Libro libro = catalogo.trovaLibro(isbn);
		// Se la persona o il libro non esistono, il prestito non si fa
		if (persona == null || libro == null) return null;
		// La persona deve essere idonea e il libro disponibile
		if (!persona.isIdoneaAlPrestito() || !libro.isDisponibile()) return null;
		// Creiamo il prestito, segniamo il libro non disponibile e ricordiamo il prestito
		Prestito prestito = new Prestito(persona, libro);
		libro.setDisponibile(false);
		prestitiAttivi.put(isbn, prestito);
		return prestito;
	}

	// Cerca il prestito in corso di un libro dal suo ISBN. Se non è in prestito restituisce null
	public Prestito trovaPrestito(String isbn) {
		return prestitiAttivi.get(isbn);
	}
	
	// Registra il rientro di un libro: chiude il prestito e rende il libro di nuovo disponibile 
	 public boolean registraRestituzione(String isbn) { 
		 Prestito prestito = prestitiAttivi.get(isbn); 
		 // Se quel libro non è in prestito, non c'è niente da restituire 
		 if (prestito == null) return false; 
		 // Chiudiamo il prestito e rendiamo il libro di nuovo disponibile 
		 prestito.getLibro().setDisponibile(true); 
		 prestitiAttivi.remove(isbn); 
		 return true;
	 }
}