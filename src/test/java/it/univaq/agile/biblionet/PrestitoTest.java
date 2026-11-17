package it.univaq.agile.biblionet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}