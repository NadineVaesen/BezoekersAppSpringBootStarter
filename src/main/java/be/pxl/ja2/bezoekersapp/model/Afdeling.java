package be.pxl.ja2.bezoekersapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Afdeling {

	@Id
	private String code;
	private String naam;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
}
