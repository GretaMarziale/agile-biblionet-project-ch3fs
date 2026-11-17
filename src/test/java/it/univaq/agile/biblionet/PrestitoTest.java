package it.univaq.agile.biblionet;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

public class PrestitoTest {
	private SistemaBiblioteca biblioteca;
	private Persona utente;
	private Persona bibliotecario;
	private Libro harryPotter;
	private Libro promessiSposi;

	@BeforeEach
	void preparaBiblioteca() {
		// Harry Potter è disponibile
		harryPotter = new Libro("Harry Potter", List.of("J.K. Rowling"), "Salani", 2000,
				"978-88-04-6", "Fantasy", "Italiano", "10-12", true, true);
		// I promessi sposi non è disponibile
		promessiSposi = new Libro("I promessi sposi", List.of("Alessandro Manzoni"), "Mondadori", 1827,
				"978-88-04-7", "Romanzo", "Italiano", "14+", false, false);
		Catalogo catalogo = new Catalogo(List.of(harryPotter, promessiSposi));

		biblioteca = new SistemaBiblioteca(catalogo);
		utente = biblioteca.registra("Aurora", "Braconi", Ruolo.UTENTE);
		bibliotecario = biblioteca.registra("Anna", "Verdi", Ruolo.BIBLIOTECARIO);
	}
		
	@Test
	@DisplayName("UE-07: Dato che un utente idoneo è al banco, quando il bibliotecario scansiona tessera e libro, allora"
			+ "il sistema associa l'utente alla copia")
	void testPersonaRitrovataDallaTessera() {
		// Registriamo un utente
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		Persona persona = sistema.registra("Aurora", "Braconi", Ruolo.UTENTE);
		// Cerchiamo la persona con il codice della sua tessera
		Persona trovata = sistema.trovaPersona(persona.getCodiceTessera());
		// Deve essere proprio la persona che abbiamo registrato
		assertSame(persona, trovata);
	}

	@Test
	@DisplayName("Una tessera inesistente non corrisponde a nessuna persona")
	void testTesseraInesistente() {
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		// Cerchiamo un codice che non abbiamo mai creato: non deve trovare nessuno
		assertNull(sistema.trovaPersona("U9999"));
	}
	
	@Test
	@DisplayName("Il catalogo ritrova un libro dal suo ISBN")
	void testLibroRitrovatoDalIsbn() {
		// Creiamo un catalogo con un solo libro
		Libro libro = new Libro("Harry Potter", List.of("J.K. Rowling"), "Salani", 2000,
				"978-88-04-6", "Fantasy", "Italiano", "10-12", true, true);
		Catalogo catalogo = new Catalogo(List.of(libro));
		// Cerchiamo il libro con il suo ISBN
		Libro trovato = catalogo.trovaLibro("978-88-04-6");
		// Deve essere proprio quel libro
		assertSame(libro, trovato);
	}

	@Test
	@DisplayName("Un ISBN inesistente non corrisponde a nessun libro")
	void testIsbnInesistente() {
		// Catalogo senza libri
		Catalogo catalogo = new Catalogo(new ArrayList<>());
		// Un ISBN che non esiste non deve trovare nessun libro
		assertNull(catalogo.trovaLibro("000-00-00-0"));
	}
	
	@Test
	@DisplayName("Un utente iscritto è idoneo al prestito")
	void testUtenteIdoneoAlPrestito() {
		// Registriamo un utente
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		Persona utente = sistema.registra("Aurora", "Braconi", Ruolo.UTENTE);
		// Un utente può prendere un libro in prestito
		assertTrue(utente.isIdoneaAlPrestito());
	}

	@Test
	@DisplayName("Un bibliotecario non è idoneo al prestito")
	void testBibliotecarioNonIdoneoAlPrestito() {
		// Registriamo un bibliotecario
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		Persona bibliotecario = sistema.registra("Anna", "Verdi", Ruolo.BIBLIOTECARIO);
		// Un bibliotecario non può prendere un libro in prestito con la sua tessera di servizio
		assertFalse(bibliotecario.isIdoneaAlPrestito());
	}
	
	@Test
	@DisplayName("Il bibliotecario registra il prestito e il sistema associa l'utente al libro")
	void testPrestitoAssociaUtenteELibro() {
		// Il bibliotecario inserisce la tessera dell'utente e l'ISBN di Harry Potter
		Prestito prestito = biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6");
		// Il prestito esiste e collega quell'utente a quel libro
		assertNotNull(prestito);
		assertSame(utente, prestito.getPersona());
		assertSame(harryPotter, prestito.getLibro());
	}

	@Test
	@DisplayName("Dopo il prestito il libro risulta non disponibile")
	void testLibroNonDisponibileDopoIlPrestito() {
		biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6");
		// Il libro prestato non deve più essere disponibile
		assertFalse(harryPotter.isDisponibile());
	}

	@Test
	@DisplayName("Il prestito registrato si può ritrovare dall'ISBN del libro")
	void testPrestitoRitrovabileDallIsbn() {
		Prestito prestito = biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6");
		// Il sistema ricorda il prestito in corso per quel libro
		assertSame(prestito, biblioteca.trovaPrestito("978-88-04-6"));
	}
	
	@Test
	@DisplayName("Un bibliotecario non può prendere un libro in prestito")
	void testPrestitoNegatoAlBibliotecario() {
		// Il bibliotecario non è idoneo: il prestito non si fa
		assertNull(biblioteca.registraPrestito(bibliotecario.getCodiceTessera(), "978-88-04-6"));

		// Il libro resta disponibile
		assertTrue(harryPotter.isDisponibile());
	}

	@Test
	@DisplayName("Un libro non disponibile non si può dare in prestito")
	void testPrestitoNegatoSeLibroNonDisponibile() {
		// I promessi sposi non è disponibile
		assertNull(biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-7"));
	}

	@Test
	@DisplayName("UE-07: lo stesso libro non si può prestare due volte")
	void testPrestitoNegatoSeLibroGiaInPrestito() {
		// Il primo prestito riesce
		biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6");

		// Il secondo no, perché il libro non è più disponibile
		assertNull(biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6"));
	}

	@Test
	@DisplayName("Con una tessera inesistente non si può fare il prestito")
	void testPrestitoNegatoConTesseraInesistente() {
		assertNull(biblioteca.registraPrestito("U9999", "978-88-04-6"));
	}

	@Test
	@DisplayName("Con un ISBN inesistente non si può fare il prestito")
	void testPrestitoNegatoConIsbnInesistente() {
		assertNull(biblioteca.registraPrestito(utente.getCodiceTessera(), "000-00-00-0"));
	}
	
	 @Test
	 @DisplayName("UE-10: Dato che un utente restituisce un libro al banco, quando il"
	 		+ "bibliotecario ne registra il rientro, allora il sistema rende la copia "
	 		+ "di nuovo disponibile")
	 void testRestituzioneRendeLibroDisponibile() {
		 // Prestiamo Harry Potter all'utente: ora non è disponibile
		 biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6");
	
		 // Il bibliotecario registra il rientro del libro
		 boolean restituito = biblioteca.registraRestituzione("978-88-04-6");
	
		 // La restituzione riesce e il libro è di nuovo disponibile
		 assertTrue(restituito);
		 assertTrue(harryPotter.isDisponibile());
	 }

	 @Test
	 @DisplayName("Dopo il rientro il prestito non risulta più in corso")
	 void testRestituzioneChiudeIlPrestito() {
		 biblioteca.registraPrestito(utente.getCodiceTessera(), "978-88-04-6");
	
		 biblioteca.registraRestituzione("978-88-04-6");
	
		 // Non c'è più nessun prestito in corso per quel libro
		 assertNull(biblioteca.trovaPrestito("978-88-04-6"));
	 }
	 
	 @Test
	 @DisplayName("Un libro non disponibile che non è in prestito non si può restituire")
	 void testRestituzioneDiLibroNonInPrestito() {
		 // I promessi sposi non è disponibile, ma nessuno lo ha preso in prestito
		 assertFalse(biblioteca.registraRestituzione("978-88-04-7"));
	
		 // Il libro resta com'era
		 assertFalse(promessiSposi.isDisponibile());
	 }
	 
	 @Test 
	 @DisplayName("Un libro già disponibile non si può restituire") 
	 void testRestituzioneDiLibroDisponibile() { 
		  // Harry Potter è disponibile e nessuno lo ha preso in prestito 
		  boolean risultato = biblioteca.registraRestituzione("978-88-04-6"); 
		  // La restituzione deve fallire 
		  assertFalse(risultato); 
		  // Lo stato del libro non deve cambiare 
		  assertTrue(harryPotter.isDisponibile()); 
	 }  

	 @Test
	 @DisplayName("Con un ISBN inesistente la restituzione non riesce")
	 void testRestituzioneConIsbnInesistente() {
		 assertFalse(biblioteca.registraRestituzione("000-00-00-0"));
	 }


}