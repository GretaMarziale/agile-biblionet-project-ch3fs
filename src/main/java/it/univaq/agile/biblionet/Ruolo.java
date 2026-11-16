package it.univaq.agile.biblionet;

public enum Ruolo {
	/* Definiamo i ruoli del sistema; la lettera tra parentesi è il prefisso usato 
	 * nel codice della tessera (U per Utente, B per Bibliotecario, R per Responsabile) */
	UTENTE("U"),
	BIBLIOTECARIO("B"),
	RESPONSABILE("R");
	
	// La variabile che conterrà la lettera del ruolo ("U", "B", "R")
	private final String prefisso;
	
	// Costruttore 
    Ruolo(String prefisso) {
        this.prefisso = prefisso;
    }
    // Metodo getter per leggere la lettera
    public String getPrefisso() {
    	return prefisso;
    }
}