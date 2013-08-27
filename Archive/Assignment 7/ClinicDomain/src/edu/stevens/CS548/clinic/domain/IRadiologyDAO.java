package edu.stevens.CS548.clinic.domain;

public interface IRadiologyDAO {
	public static class RadiologyExn extends Exception {
		private static final long serialVersionUID = 1L;
		public RadiologyExn (String msg) {
			super(msg);
		}
	}
	public Radiology getRadiologyByDbId(long id) throws RadiologyExn;
	public void addRadiology(Radiology r);
	public void deleteRadiology(Radiology r);
}
