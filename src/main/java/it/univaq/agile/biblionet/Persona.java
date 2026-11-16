package it.univaq.agile.biblionet;

public class Persona {
	
	private String nome;
	private String cognome;
	private Ruolo ruolo;
	private String codice;

	public Persona(String nome, String cognome, Ruolo ruolo, String codice) {
	    this.nome = nome;
	    this.cognome = cognome;
	    this.ruolo = ruolo;
	    this.codice = codice;
	}
	
	public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public String getCodiceTessera() {
        return codice;
    }
}