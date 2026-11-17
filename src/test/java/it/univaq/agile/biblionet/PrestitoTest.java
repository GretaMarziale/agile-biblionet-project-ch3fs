package it.univaq.agile.biblionet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

public class PrestitoTest {
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
}