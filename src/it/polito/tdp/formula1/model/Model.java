package it.polito.tdp.formula1.model;


import java.util.List;

import it.polito.tdp.formula1.db.F1DAO;

public class Model {

	private F1DAO dao;
	private Race race;
	
	public Model(){
		dao= new F1DAO();
	}
	
	public List<Season> getAllSeasons(){
		return dao.getAllSeasons();
	}

	public List<Circuit> getCircuitiForSeason(Season season) {
		return dao.getCircuitsForSeason(season);
	}

	public Race getGara(Season s, Circuit c) {
		 race=dao.getGara(s, c);
		 return race;
	}

	public List<Driver> getPiloti(Race r) {
		return dao.getDrivers(r);
	}
	
	public List<FantaDriver> doFantaGara(Driver d, Circuit c){
		 
		Simulator sim= new Simulator(d, c);
		  
		sim.loadPartenza();
		 
		sim.run();
		 
		return sim.getClassifica();
	}
}
