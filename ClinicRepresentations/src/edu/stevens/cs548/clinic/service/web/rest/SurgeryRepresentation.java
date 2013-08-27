package edu.stevens.cs548.clinic.service.web.rest;

import java.util.Date;

import edu.stevens.cs548.clinic.service.web.rest.data.SurgeryType;

public class SurgeryRepresentation extends SurgeryType {

	public SurgeryRepresentation(Date date) {
		this.date=date;
	}

}
