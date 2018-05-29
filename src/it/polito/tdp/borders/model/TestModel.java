package it.polito.tdp.borders.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		m.createGrafo(1950);//confronto con numero di righe delle query, è giusto!
		
		List<CountryAndNumber> list = m.getCountryAndNumber();
		
		for(CountryAndNumber c: list) {
			System.out.format("%s %d\n", c.getCountry().getStateName(),c.getNumber());
		}
		

	}

}
