package it.univaq.agile.biblionet;

public class SistemaBiblioteca {
	
	// Contatore progressivo per le tessere. Parte da 1 e aumenta di 1 ogni volta che qualcuno si registra.
	private int contatoreTessere = 1;

	// Registrazione di una persona
    public Persona registra(String nome, String cognome, Ruolo ruolo) {
    	
        // Crea il codice della tessera unendo prefisso e contatore formattato a 4 cifre (poiché sono circa 6000 iscritti)
    	String codice = ruolo.getPrefisso() + String.format("%04d", contatoreTessere++);
        
        // Restituisce la nuova persona registrata con l'account creato
        return new Persona(nome, cognome, ruolo, codice);
    }
}