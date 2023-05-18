package app;

import entities.Concerto;
import entities.Evento;
import entities.GenereConcerto;
import entities.Location;
import entities.Partecipazione;
import entities.Persona;
import entities.Sesso;
import entities.StatoPartecipazione;
import entities.TipoEvento;

import java.util.Arrays;
import java.util.Date;

import dao.EventoDAO;
import dao.LocationDAO;
import dao.PartecipazioneDAO;
import dao.PersonaDAO;

public class GestioneEventi {
public static void main(String[] args) {
		
		Location location = saveLocation();
		Evento evento = saveEvento(location);
		Persona persona = savePersona();
		
		Partecipazione partecipazione = savePartecipazione(evento,persona);


	}

private static void saveConcerto() {
	Concerto con = new Concerto();
	con.setTitolo("Marco-Mengoni");
	con.setGenere(GenereConcerto.POP);
	con.setDataEvento(new Date());

	EventoDAO eventoDAO = new EventoDAO();
	eventoDAO.save(con);

	eventoDAO.getConcertiInStreaming(true);
	eventoDAO.getConcertiPerGenere(Arrays.asList(GenereConcerto.CLASSICO));


}

private static Partecipazione savePartecipazione(Evento evento, Persona persona) {
	Partecipazione part = new Partecipazione();
	part.setEvento(evento);
	part.setPersona(persona);
	part.setStato(StatoPartecipazione.CONFERMATA);
	
	PartecipazioneDAO partecipazioneDAO = new PartecipazioneDAO();
	partecipazioneDAO.save(part);
	return part;
}

private static Persona savePersona() {
	Persona per = new Persona();
	per.setNome("pierluigi");
	per.setCognome("marzo");
	per.setEmail("pierluigiepicode@gmail.com");
	per.setSesso(Sesso.MASCHIO);
	PersonaDAO personaDAO = new PersonaDAO();
	personaDAO.save(per);
	return per;
}


private static Location saveLocation() {
	Location loc = new Location();
	loc.setCitta("Lecce");
	loc.setNome("Via del Mare");
	
	LocationDAO locationDAO = new LocationDAO();
	locationDAO.save(loc);
	return loc;
}

private static Evento saveEvento(Location loc) {
	Evento ev = new Evento();
	ev.setDataEvento(new Date());
	ev.setTitolo("Partita");
	ev.setDescrizione("Inter-city finale champions");
	ev.setNumeroMassimoPartecipanti(80000);
	ev.setTipoEvento(TipoEvento.PUBBLICO);
	ev.setLocation(loc);
	
	EventoDAO evDao = new EventoDAO();
	evDao.save(ev);
	return ev;
}



}
