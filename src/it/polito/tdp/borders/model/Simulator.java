package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {
	
	//Tipi di Eventi/ Coda degli eventi
	PriorityQueue<Event> queue ;
	
	
	//Modello del mondo: qual è la parte statica della simulazione? elenco stati con numero di persone stanziali
	private Map<Country, Integer> stanziali;
	private Graph<Country, DefaultEdge> grafo;
	
	//Parametri di simulazione: person einiettate al tempo 0
	private int N_MIGRANTI = 1000 ;
	
	
	//Variabili in output
	private int T; //numero di passi di simulazione
	
	public void init(Graph<Country,DefaultEdge> grafo, Country partenza ){
		
		//azzerare tempo 
		this.T = 1 ;
		
		//azzerare mappa stanziali
		this.stanziali = new HashMap<Country, Integer>();
		for(Country c : grafo.vertexSet()) { //popolo la mappa con tanti 0 quanti country nel grafo
			this.stanziali.put(c, 0);
		}
		
		//creare coda aggiungendo evento iniziale 
		this.queue = new PriorityQueue<Event>() ;
		this.queue.add(new Event(T, this.N_MIGRANTI, partenza)); //a t=1 si spostano 1000 persone
		
		//il grafo mi serve ancora=> lo salvo localmente
		this.grafo = grafo ;
		
	}
	
	public void run() {
		Event e ;
		while ((e = queue.poll())!= null ) {
			
			//il T da restituire è il massimo che ho visto, cioè l'ultimo estratto della coda (perché ordinata per tempo)
			this. T = e.getT();
			
			int arrivi = e.getNum();
			Country stato = e.getDestination();
			
			//divido arrivi tra chi rimane e chi si sposta: guardo quante nazioni adiacenti ha quello stato
			List<Country> confinanti = Graphs.neighborListOf(this.grafo, stato);
			
			int migranti = (arrivi / 2) / confinanti.size(); 
				//confinanti.size() non può essere = 0 perché nel grafo ci sono solo stati che confinano con altri
			
			//deposito migranti negli stati confinati se divisione!=0 cioè c'è qualcuno che si può spostare
			if(migranti != 0) {
				for(Country arrivo: confinanti) {
					queue.add(new Event(e.getT()+1, migranti, arrivo));
				}
			}
			
			int rimasti = arrivi - migranti * confinanti.size() ;
			
			this.stanziali.put(stato, this.stanziali.get(stato) + rimasti);
			
			
		}
	}

	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}

	public int getT() {
		return T;
	}
	
	
	
}
