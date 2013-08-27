//implement the IPatientFactory interface
//every time JPA get patient records from database, it will create patient objects and  initialize it.
package edu.stevens.CS548.clinic.domain;

import java.util.Date;

public class PatientFactory implements IPatientFactory {

	@SuppressWarnings("deprecation")
	@Override
	public Patient createPatient(long pid, String name, Date dob, int age) {
		Date nowDate = new Date();
		//calculate age, system do not recommend to use getYear(), getMonth(), getDate()
		int tempAge = nowDate.getYear()-dob.getYear();
		if (nowDate.getMonth()<dob.getMonth()){
			tempAge --;
		}
		else if (nowDate.getMonth()==dob.getMonth() && nowDate.getDate()<dob.getDate()){
			tempAge --;
		}
		if (tempAge == age){
			Patient p = new Patient();
			p.setPatientId(pid);
			p.setName(name);
			p.setBirthDate(dob);
			return p;
		}
		else{
			return null;
		}
		
	}
}
