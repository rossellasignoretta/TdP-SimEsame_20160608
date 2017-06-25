package it.polito.tdp.formula1.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formula1.model.Circuit;
import it.polito.tdp.formula1.model.Driver;
import it.polito.tdp.formula1.model.FantaDriver;
import it.polito.tdp.formula1.model.Race;
import it.polito.tdp.formula1.model.Season;


public class F1DAO {

	public List<Season> getAllSeasons() {
		
		String sql = "SELECT year, url FROM seasons ORDER BY year" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Season> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Season(Year.of(rs.getInt("year")), rs.getString("url"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Circuit> getCircuitsForSeason(Season season) {
		
		String sql = "SELECT c.circuitId as id, c.name as name\n" + 
				"FROM races r, circuits c\n" + 
				"WHERE c.circuitId=r.circuitId AND r.year=?" ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, season.getYear().getValue());
			ResultSet rs = st.executeQuery() ;
			
			List<Circuit> list = new ArrayList<>() ;
			while(rs.next()) {
				list.add(new Circuit(rs.getInt("id"), rs.getString("name"))) ;
			}
			
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}


	public Race getGara(Season s, Circuit c) {
		String sql="SELECT *\n" + 
				"FROM races\n" + 
				"WHERE year=? AND circuitId=?";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getYear().getValue());
			st.setInt(2, c.getCircuitId());
			ResultSet rs = st.executeQuery() ;
			
			Race r=null;
			if(rs.next()) {
				LocalTime t=null;
				if(rs.getTime("time")!=null){
					t=rs.getTime("time").toLocalTime();
				}
				r= new Race(rs.getInt("raceId"),Year.of(rs.getInt("year")),rs.getInt("round"),
						rs.getInt("circuitId"),rs.getString("name"), rs.getDate("date").toLocalDate(),
						t, rs.getString("url")) ;
			}
			
			conn.close();
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<Driver> getDrivers(Race r) {
		String sql="SELECT d.driverId as id, d.number as number, code, forename, surname, dob, nationality, url\n" + 
				"FROM results r, drivers d\n" + 
				"WHERE d.driverId=r.driverId AND r.raceId=?";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, r.getRaceId());
			ResultSet rs = st.executeQuery() ;
			List<Driver> list= new ArrayList<>();
			
			while(rs.next()) {
				list.add(new Driver(rs.getInt("id"),rs.getInt("number"),rs.getString("code"),
						rs.getString("forename"),rs.getString("surname"), rs.getDate("dob").toLocalDate(),
						rs.getString("nationality"), rs.getString("url"))) ;
			}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<FantaDriver> getFantaDrivers(Driver d, Circuit c){
		String sql="SELECT DISTINCT l.raceId as id, r.year as year\n" + 
				"FROM laptimes l, races r\n" + 
				"WHERE l.raceId=r.raceId AND l.driverId=? AND r.circuitId=?";
		
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, d.getDriverId());
			st.setInt(2, c.getCircuitId());
			ResultSet rs = st.executeQuery() ;
			
			List<FantaDriver> list= new ArrayList<>();
			
			while(rs.next()) {
				list.add(new FantaDriver(d, rs.getInt("id"),Year.of(rs.getInt("year")))) ;
			}
			
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Map<Integer, Duration> getLapTimes(FantaDriver fd){
		String sql="SELECT l.raceId, l.driverId, l.lap as lap, l.milliseconds as time\n" + 
				"FROM laptimes l\n" + 
				"WHERE l.raceId=? AND l.driverId=? ";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, fd.getRaceId());
			st.setInt(2, fd.getDriver().getDriverId());
			ResultSet rs = st.executeQuery() ;
			
			Map<Integer, Duration> mappa= new HashMap<Integer, Duration>();
			
			while(rs.next()) {
				Duration d= Duration.ofMillis(rs.getInt("time"));
				mappa.put(rs.getInt("lap"),d) ;
			}
			
			conn.close();
			return mappa;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
