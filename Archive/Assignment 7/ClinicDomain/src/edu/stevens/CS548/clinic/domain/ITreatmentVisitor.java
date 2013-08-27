//visitor pattern

package edu.stevens.CS548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface ITreatmentVisitor {
	public void visitDrugTreatment(long tid, long pid, String diagnosis, String drug, float dosage);
	public void visitRadiology(long tid, long pid, String diagnosis, List<RadiologyDate> dates);
	public void visitSurgery(long tid, long pid, String diagnosis, Date date);
}
