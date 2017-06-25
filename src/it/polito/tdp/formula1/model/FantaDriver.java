package it.polito.tdp.formula1.model;

import java.time.Duration;
import java.time.Year;
import java.util.Map;

public class FantaDriver {
	
	private Driver driver;
	private int raceId;
	private Year year;
	
	private Map<Integer, Duration> laptimes;
	
	private int position;
	private int punteggio;

	public FantaDriver(Driver driver, int raceId, Year year) {
		super();
		this.driver=driver;
		this.raceId = raceId;
		this.year = year;
		position=0;
		punteggio=0;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Map<Integer, Duration> getLaptimes() {
		return laptimes;
	}

	public void setLaptimes(Map<Integer, Duration> laptimes) {
		this.laptimes = laptimes;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
	

	
	
	
}
