package it.polito.tdp.formula1.model;

public class Event implements Comparable<Event>{
	
	private FantaDriver fd;
	private int lap;
	private long time;
	
	public Event(FantaDriver fd, int lap, long time) {
		super();
		this.fd = fd;
		this.lap = lap;
		this.time = time;
	}
	public FantaDriver getFd() {
		return fd;
	}
	public void setFd(FantaDriver fd) {
		this.fd = fd;
	}	
	public int getLap(){
		return lap;
	}	
	public void setLap(int lap){
		this.lap  =lap;
	}
	public long getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@Override
	public int compareTo(Event o){
		return (int) (this.time-o.time);
	}
	
	
	
	
}
