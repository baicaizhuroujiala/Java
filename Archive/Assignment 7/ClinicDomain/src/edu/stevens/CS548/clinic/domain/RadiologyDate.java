package edu.stevens.CS548.clinic.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: RadiologyDate
 *
 */
@Entity

public class RadiologyDate implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "radiology_fk", referencedColumnName = "id")
	private Radiology radiology;

	public Radiology getRadiology() {
		return radiology;
	}


	public void setRadiology(Radiology radiology) {
		this.radiology = radiology;
	}


	public RadiologyDate() {
		super();
	}
   
}
