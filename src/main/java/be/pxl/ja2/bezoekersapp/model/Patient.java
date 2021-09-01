package be.pxl.ja2.bezoekersapp.model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patient")
@NamedQueries({
		@NamedQuery(name = "findAllPatients", query = "SELECT p FROM Patient p")
})
public class Patient {

	@Id
	private String code;
	private LocalDateTime opname;
	@OneToOne
	private Afdeling afdeling;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getOpname() {
		return opname;
	}

	public void setOpname(LocalDateTime opname) {
		this.opname = opname;
	}

	public Afdeling getAfdeling() {
		return afdeling;
	}

	public void setAfdeling(Afdeling afdeling) {
		this.afdeling = afdeling;
	}
}
