package it.univaq.agile.biblionet;
import java.util.*;

public class Catalogo {
	//LinkedHashMap ricorda l'ordine in cui abbiamo inserito gli elementi 
	private Map<String, Libro> mappaLibri = new LinkedHashMap<>();
	
	//Costruttore per popolare inizialmente il catalogo (aggiunta libri prevista nell'UE-08)
	public Catalogo(List<Libro> libriIniziali) { 
		if (libriIniziali != null )
			for (Libro libro : libriIniziali) mappaLibri.put(libro.getIsbn(), libro);
	}
	
	public void mostraLibri() {
	        for (Libro libro : mappaLibri.values()) {
	            System.out.println("Titolo: " + libro.getTitolo());
	            System.out.println("Autori: " + libro.getAutori());
	            System.out.println("Editore: " + libro.getEditore());
	            System.out.println("Anno: " + libro.getAnno());
	            System.out.println("ISBN: " + libro.getIsbn());
	            System.out.println("Genere: " + libro.getGenere());
	            System.out.println("Lingua: " + libro.getLingua());
	            System.out.println("Fascia età: " + libro.getFasciaEta());
	            System.out.println("Novità: " + libro.isNovita());
	            System.out.println("Disponibilità: " + libro.isDisponibile());
	        } 
	} 
	
	public List<Libro> getElencoLibri() { 
		return new ArrayList<>(mappaLibri.values());
	}
	
	public boolean prenotaLibro(Libro libro) { 
		if (libro != null && mappaLibri.containsKey(libro.getIsbn()) && libro.isDisponibile()) { 
			libro.setDisponibile(false);
			System.out.println("Prenotazione effettuata con successo per " + libro.getTitolo() +
					". Puoi ritirare la tua copia presso la biblioteca.");
			return true;
		}
		return false; 
	}
}
