package it.polito.tdp.borders.model;
/**
 * Oggetto per restituire la lista di stati e numero di stati confinanti al controller dal model.
 * @author sere9
 *
 */
public class CountryAndNumber implements Comparable<CountryAndNumber>{
	
	private Country country;
	private int number;
	
	public CountryAndNumber(Country country, int number) {
		super();
		this.country = country;
		this.number = number;
	}
	
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int compareTo(CountryAndNumber other) {
		//ordine decrescente per numero
		return other.getNumber()- this.getNumber();
	}
	
	

}
