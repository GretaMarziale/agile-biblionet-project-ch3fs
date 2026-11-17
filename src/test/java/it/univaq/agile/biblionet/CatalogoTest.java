package it.univaq.agile.biblionet;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CatalogoTest {
	private Catalogo catalogo;

	//eseguito prima di ogni test e per creare un catalogo con 2 libri
	@BeforeEach
	void preparaCatalogo() {
		//Primo libro: è una novità ed è disponibile
		Libro harryPotter = new Libro("Harry Potter", List.of("J.K. Rowling"), "Salani", 2000,
				"978-88-04-6", "Fantasy", "Italiano", "10-12", true, true);
		// Secondo libro: non è una novità e non è disponibile
		Libro promessiSposi = new Libro("I promessi sposi", List.of("Alessandro Manzoni"), "Mondadori", 1827,
				"978-88-04-7", "Romanzo", "Italiano", "14+", false, false);
		catalogo = new Catalogo(List.of(harryPotter, promessiSposi));
	}

	@Test
	@DisplayName("UE-03A: Dato che l'utente è nella schermata del catalogo, quando visualizza "
			+ "la lista dei libri, allora il sistema mostra l'elenco completo...")
	void testElencoCompleto() {
	//chiedo l'elenco al catalogo
		List<Libro> elenco = catalogo.getElencoLibri();

		// Controlliamo che ci siano 2 libri, nell'ordine in cui li abbiamo inseriti
		assertEquals(2, elenco.size());
		assertEquals("Harry Potter", elenco.get(0).getTitolo());
		assertEquals("I promessi sposi", elenco.get(1).getTitolo());
	}
	
	@Test
	@DisplayName("UE-03A: ... con dettagli,...")
	void testDettagliDelLibro() {
		// Prendiamo il primo libro dell'elenco
		Libro libro = catalogo.getElencoLibri().get(0);
		
		// Controlliamo che ogni dettaglio sia quello che abbiamo inserito
		assertEquals("Harry Potter", libro.getTitolo());
		assertEquals(List.of("J.K. Rowling"), libro.getAutori());
		assertEquals("Salani", libro.getEditore());
		assertEquals(2000, libro.getAnno());
		assertEquals("978-88-04-6", libro.getIsbn());
		assertEquals("Fantasy", libro.getGenere());
		assertEquals("Italiano", libro.getLingua());
		assertEquals("10-12", libro.getFasciaEta());
		assertTrue(libro.isNovita());
	}

	@Test
	@DisplayName("UE-03A: ... stato (disponibile o non disponibile)")
	void testStatoDisponibileENonDisponibile() {
		// Chiediamo l'elenco al catalogo
		List<Libro> elenco = catalogo.getElencoLibri();
		
		// Il primo libro deve risultare disponibile, il secondo no
		assertTrue(elenco.get(0).isDisponibile(), "Harry Potter deve risultare disponibile");
		assertFalse(elenco.get(1).isDisponibile(), "I promessi sposi deve risultare non disponibile");
	}

	@Test
	@DisplayName("Un catalogo senza libri restituisce un elenco vuoto")
	void testCatalogoVuoto() {
		// Creiamo un catalogo senza nessun libro
		Catalogo vuoto = new Catalogo(new ArrayList<>());
		// L'elenco deve essere vuoto
		assertTrue(vuoto.getElencoLibri().isEmpty());
	}
	
	@Test
	@DisplayName("UE-04: Dato che l'utente seleziona un libro disponibile, quando clicca su 'Prenota', allora il sistema registra la prenotazione,...")
	void testPrenotazioneLibroDisponibile() {
		// Prendiamo il primo libro del catalogo (Harry Potter, disponibile)
		Libro libro = catalogo.getElencoLibri().get(0);
		// Prenotiamo il libro
		boolean prenotato = catalogo.prenotaLibro(libro);
		// La prenotazione deve essere andata a buon fine
		assertTrue(prenotato);
	}

	@Test
	@DisplayName("UE-04: ... blocca la copia per gli altri")
	void testLibroBloccatoDopoPrenotazione() {
		// Prendiamo Harry Potter, che è disponibile
		Libro libro = catalogo.getElencoLibri().get(0);
		// Prenotiamo il libro
		catalogo.prenotaLibro(libro);
		// Il libro prenotato non deve più essere disponibile
		assertFalse(catalogo.getElencoLibri().get(0).isDisponibile());
	}
	
	@Test
	@DisplayName("Un libro non disponibile non si può prenotare")
	void testPrenotazioneLibroNonDisponibile() {
		// Prendiamo I promessi sposi, che non è disponibile
		Libro libro = catalogo.getElencoLibri().get(1);
		// La prenotazione non deve riuscire
		assertFalse(catalogo.prenotaLibro(libro));
	}

	@Test
	@DisplayName("Lo stesso libro non si può prenotare due volte")
	void testPrenotazioneDoppia() {
		// Prendiamo Harry Potter, che è disponibile
		Libro libro = catalogo.getElencoLibri().get(0);
		// La prima prenotazione riesce, la seconda no
		assertTrue(catalogo.prenotaLibro(libro));
		assertFalse(catalogo.prenotaLibro(libro));
	}

	@Test
	@DisplayName("Un libro che non è nel catalogo non si può prenotare")
	void testPrenotazioneLibroNonPresenteNelCatalogo() {
		// Creiamo la lista degli autori del libro
		List<String> autori = new ArrayList<>();
		autori.add("J.R.R. Tolkien");
		// Creiamo un libro che non inseriamo nel catalogo
		Libro estraneo = new Libro("Il Signore degli Anelli", autori, "Bompiani", 1954,
				"978-88-04-8", "Fantasy", "Italiano", "14+", false, true);
		// La prenotazione non deve riuscire
		assertFalse(catalogo.prenotaLibro(estraneo));
	}

	@Test
	@DisplayName("Non si può prenotare senza scegliere nessun libro")
	void testPrenotazioneSenzaLibro() {
		// Se non scegliamo nessun libro (null), la prenotazione non deve riuscire
		assertFalse(catalogo.prenotaLibro(null));
	}
}
