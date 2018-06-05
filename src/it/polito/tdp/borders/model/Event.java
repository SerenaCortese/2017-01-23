package it.polito.tdp.borders.model;

public class Event implements Comparable<Event> {//c'è solo un tipo di evento

	private int t;//tempo
	
	private int num; //numero persone che si spostano
	private Country destination; //stato di destinazione
	
	
	public Event(int t, int num, Country destination) {
		super();
		this.t = t;
		this.num = num;
		this.destination = destination;
	}

	public int getT() {
		return t;
	}

	public int getNum() {
		return num;
	}

	public Country getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return "Event t=" + t + ", num=" + num + ", destination=" + destination;
	}

	@Override
	public int compareTo(Event other) {
		return this.t-other.t;
	}
	
	

}
