package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private Graph<User,DefaultWeightedEdge> grafo;
	private List<User> utenti;
	private YelpDao dao;
	
	public Model() {
		//this.utenti=new ArrayList<User>();
		dao = new YelpDao();
	}
	
	
	public String creaGrafo(int minRevisioni, int anno) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//creo vertici
		this.utenti=dao.getUsersWithReviews(minRevisioni);
		Graphs.addAllVertices(this.grafo, utenti);
		//creo archi
		for(User u1:this.utenti) {
			for(User u2: this.utenti) {
				if(!u1.equals(u2) && u1.getUserId().compareTo(u2.getUserId())<0) {
					int similarità = dao.calcolaSimilarita(u1, u2, anno);
					if(similarità>0)
						Graphs.addEdgeWithVertices(this.grafo, u1, u2, similarità);
				}
			}
		}
		return "Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+" archi.";
		
	}
	public List<User> utentiSimili(User u) {					//COME CONFRONTARE PESI DEGLI ARCHI!!!!!!!!!!!!!!!!!
		int max=0;												// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		for(DefaultWeightedEdge e: this.grafo.edgesOf(u)) {
			if(this.grafo.getEdgeWeight(e)>max)
				max=(int)this.grafo.getEdgeWeight(e);
		}
		List<User> ris= new ArrayList<User>();
		for(DefaultWeightedEdge e: this.grafo.edgesOf(u)) {
			if((int)this.grafo.getEdgeWeight(e)==max) {
				User u2 = Graphs.getOppositeVertex(this.grafo, e, u);	//PRENDERE IL VERTICE OPPOSTO A u COLLEGATO TRAMITE L'ARCO!!!!!!!!
				ris.add(u2);											// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			}
		}
		return ris;
	}
	
	public List<User> getUsers(){
		return this.utenti;
	}
	
}
