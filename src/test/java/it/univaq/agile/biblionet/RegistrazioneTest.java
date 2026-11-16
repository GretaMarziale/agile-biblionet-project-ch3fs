package it.univaq.agile.biblionet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrazioneTest {
	@Test
	@DisplayName("Dato che una persona apre la registrazione selezionando il ruolo Utente, quando compila i dati "
			+ "e invia, allora il sistema crea l'account, genera una tessera univoca con prefisso 'U' e sblocca l'accesso.")
	void testRegistrazioneUtente() {
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		Persona persona = sistema.registra("Aurora", "Braconi", Ruolo.UTENTE);
		assertNotNull(persona, "L'account non deve essere nullo");
        assertEquals("Aurora", persona.getNome());
        assertEquals("Braconi", persona.getCognome());
        assertEquals(Ruolo.UTENTE, persona.getRuolo());
        assertTrue(persona.getCodiceTessera().startsWith("U"), "La tessera deve iniziare con U");
	}
	
	@Test
	@DisplayName("Dato che una persona apre la registrazione selezionando il ruolo Bibliotecario, quando compila i dati "
			+ "e invia, allora il sistema crea l'account, genera una tessera univoca con prefisso 'B' e sblocca l'accesso.")
	void testRegistrazioneBibliotecario() {
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		Persona persona = sistema.registra("Anna", "Verdi", Ruolo.BIBLIOTECARIO);
		assertNotNull(persona, "L'account non deve essere nullo");
        assertEquals("Anna", persona.getNome());
        assertEquals("Verdi", persona.getCognome());
        assertEquals(Ruolo.BIBLIOTECARIO, persona.getRuolo());
        assertTrue(persona.getCodiceTessera().startsWith("B"), "La tessera deve iniziare con B-");
	}
	
	@Test
	@DisplayName("Dato che una persona apre la registrazione selezionando il ruolo Responsabile, quando compila i dati "
			+ "e invia, allora il sistema crea l'account, genera una tessera univoca con prefisso 'R' e sblocca l'accesso.")
	void testRegistrazioneResponsabile() {
		SistemaBiblioteca sistema = new SistemaBiblioteca();
		Persona persona = sistema.registra("Greta", "Rossi", Ruolo.RESPONSABILE);
		assertNotNull(persona, "L'account non deve essere nullo");
        assertEquals("Greta", persona.getNome());
        assertEquals("Rossi", persona.getCognome());
        assertEquals(Ruolo.RESPONSABILE, persona.getRuolo());
        assertTrue(persona.getCodiceTessera().startsWith("R"), "La tessera deve iniziare con R");
	}
}

