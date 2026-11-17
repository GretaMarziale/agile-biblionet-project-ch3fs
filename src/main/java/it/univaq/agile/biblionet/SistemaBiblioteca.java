package it.univaq.agile.biblionet;

import java.util.*;

public class SistemaBiblioteca {
	// Contatore progressivo per le tessere. Parte da 1 e aumenta di 1 ogni volta che qualcuno si registra.
	private int contatoreTessere = 1;
	// Rubrica delle persone registrate: si cerca con il codice della tessera
	private Map<String, Persona> persone = new HashMap<>();
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

	// Cerca una persona con il codice della tessera. Se non esiste restituisce null (cioè "nessuno")
	public Persona trovaPersona(String codiceTessera) {
		return persone.get(codiceTessera);
	}
}