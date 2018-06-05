package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.CoppiaNoStati;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BordersDAO {
	
	public List<Country> loadAllCountries() {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public List<Country> getCountriesFromYear(int anno){
		//voglio country che hanno almeno una relazione di continuità in anni minori o uguali a quello specificato
		String sql = "SELECT * FROM country WHERE CCode in (SELECT state1no FROM contiguity WHERE year <= ? AND conttype = 1) ORDER BY StateNme ASC ";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("CCode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;

	}
	
	public List<CoppiaNoStati> getCoppieAdacenti(int anno){
		String sql = "SELECT state1no, state2no FROM contiguity WHERE year <= ? AND conttype=1 AND state1no < state2no";
		//con state1no<state2no tolgo i doppioni : italia-ungheria ungheria-italia sono lo stesso edge
		//non avrei problemi con simplegraph perché non è mutigrafo e non è pesato quindi aggiunge il primo ramo e ignora l'altro
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			List<CoppiaNoStati> result = new LinkedList<CoppiaNoStati>() ;
			
			while( rs.next() ) {
				
				result.add(new CoppiaNoStati(rs.getInt("state1no"), rs.getInt("state2no")));
			}
			
			conn.close() ;
			
			return result ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;

	}
	
	public static void main(String[] args) {
		List<Country> list ;
		BordersDAO dao = new BordersDAO() ;
		list = dao.loadAllCountries() ;
		for(Country c: list) {
			System.out.println(c);
		}
	}
	
	
}
