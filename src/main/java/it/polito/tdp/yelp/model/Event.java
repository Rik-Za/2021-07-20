package it.polito.tdp.yelp.model;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		DA_INTERVISTARE,
		FERIE,
	}
	
	private EventType type;
	private int giorno;
	private User intervistato;
	private Giornalista giornalista;
	public Event(EventType type, int giorno, User intervistato, Giornalista giornalista) {
		super();
		this.type=type;
		this.giorno = giorno;
		this.intervistato = intervistato;
		this.giornalista = giornalista;
	}
	public int getGiorno() {
		return giorno;
	}
	public User getIntervistato() {
		return intervistato;
	}
	public Giornalista getGiornalista() {
		return giornalista;
	}
	
	public EventType getType() {
		return type;
	}
	@Override
	public int compareTo(Event o) {
		return this.giorno-o.giorno;
	}
	
	

}
