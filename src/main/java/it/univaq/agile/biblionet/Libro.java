package it.univaq.agile.biblionet;
import java.util.List;

public class Libro {
	private String titolo;
	private List<String> autori;
	private String editore;
	private int anno;
	private String isbn;
	private String genere;
	private String lingua;
	private String fasciaEta;
	private boolean novita;
	private boolean disponibile;
	
	public Libro(String titolo,List<String> autori,String editore,int anno,String isbn,
			String genere,String lingua,String fasciaEta,boolean novita,boolean disponibile) {
		 this.titolo = titolo;
		 this.autori = autori;
		 this.editore = editore;
		 this.anno = anno;
		 this.isbn = isbn;
		 this.genere = genere;
		 this.lingua = lingua;
		 this.fasciaEta = fasciaEta;
		 this.novita = novita;
		 this.disponibile = disponibile;
	}
	public String getTitolo() {
	    return titolo;
	}
	public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
	public List<String> getAutori() {
	    return autori;
	}
	public void setAutori(List<String> autori) {
        this.autori = autori;
    }

	public String getEditore() {
	    return editore;
	}

    public void setEditore(String editore) {
        this.editore = editore;
    }
	public int getAnno() {
	    return anno;
	}
	 public void setAnno(int anno) {
	        this.anno = anno;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getGenere() {
	    return genere;
	}
	public void setGenere(String genere) {
	        this.genere = genere;
	}
	public String getLingua() {
	    return lingua;
	}
	public void setLingua(String lingua) {
        this.lingua = lingua;
    }
	public String getFasciaEta() {
	    return fasciaEta;
	}
	public void setFasciaEta(String fasciaEta) {
	    this.fasciaEta = fasciaEta;
	}
	public boolean isNovita() {
	    return novita;
	}
	public void setNovita(boolean novita) {
        this.novita = novita;
    }
	public boolean isDisponibile() {
		return disponibile;
	}
	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}
}


