package edu.stevens.CS548.clinic.domain;

public interface IRadiologyDateDAO {
	public static class RadiologyDateExn extends Exception {
		private static final long serialVersionUID = 1L;
		public RadiologyDateExn (String msg) {
			super(msg);
		}
	}
	public RadiologyDate getRadiologyDateByDbId(long id) throws RadiologyDateExn;
	public void addRadiologyDate(RadiologyDate date);
	public void deleteRadiologyDate(RadiologyDate date);
}
