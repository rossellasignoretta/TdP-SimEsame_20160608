package it.polito.tdp.formula1.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.formula1.db.F1DAO;

public class Simulator {
	
	F1DAO dao;
	List<FantaDriver> drivers;
	
	PriorityQueue<Event> queue; 
	
	Map<Integer, List<FantaDriver>> passaggi;

	public Simulator(Driver d, Circuit c){
		dao= new F1DAO();
		queue= new PriorityQueue<>();
		
		drivers= dao.getFantaDrivers(d, c);
		for(FantaDriver fd: drivers){
			fd.setLaptimes(dao.getLapTimes(fd));
		}

		passaggi= new HashMap<Integer, List<FantaDriver>>();
	}
	
	public void loadPartenza() {
		for(FantaDriver d: drivers){
			queue.add(new Event(d, 1, d.getLaptimes().get(1).toMillis()));
		}
	}

	public void run() {
		
		while(!queue.isEmpty()){
			Event e = queue.poll();
			FantaDriver fd=e.getFd();
			int lap= e.getLap();
			
			//se il pilota è due giri indietro rispetto al primo viene eliminato
			if(passaggi.size()>lap+1){
				drivers.remove(fd);
				continue;
			}
			
			
			//se ho iniziato un nuovo giro inizializzo la lista con l'ordine del passaggio dei piloti
			if(!passaggi.containsKey(lap)){
				List<FantaDriver> ordine= new LinkedList<FantaDriver>();
				passaggi.put(lap, ordine);
			}
			
			//aggiungo il pilota alla lista
			passaggi.get(lap).add(fd);
			
			int posizione=passaggi.get(lap).size();
			
			System.out.println(fd.getYear().getValue()+" "+lap+" "+posizione);
			
			//controllo se il pilota ha compiuto un sorpasso
			if(fd.getPosition()==0){
				fd.setPosition(posizione);
			}else{
				if(fd.getPosition()>posizione){
					fd.setPunteggio(fd.getPunteggio()+1);
					fd.setPosition(posizione);
				}
			}
			
			//creo il prossimo evento (se possibile)
			if(fd.getLaptimes().containsKey(lap+1)){
				long time=fd.getLaptimes().get(lap+1).toMillis();
				queue.add(new Event(fd, lap+1, e.getTime()+time));
			}
		}
		
	}

	public List<FantaDriver> getClassifica() {
		return drivers;
	}

}
