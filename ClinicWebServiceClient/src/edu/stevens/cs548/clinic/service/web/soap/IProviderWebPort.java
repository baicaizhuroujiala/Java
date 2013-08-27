/**
 * IProviderWebPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package edu.stevens.cs548.clinic.service.web.soap;

public interface IProviderWebPort extends java.rmi.Remote {
    public long createProvider(java.lang.String arg0, long arg1, java.lang.String arg2) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
    public java.lang.Long[] getTreatmentIdsByProviderId(long arg0) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
    public edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsOfPatientResponseReturn[] getTreatmentsOfPatient(long arg0, long arg1) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.PatientServiceExn, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
    public edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsResponseReturn[] getTreatments(long arg0, java.lang.Long[] arg1) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
    public edu.stevens.cs548.clinic.service.web.soap.ProviderDTO getProviderByProviderId(long arg0) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
    public edu.stevens.cs548.clinic.service.web.soap.ProviderDTO[] getProviderByName(java.lang.String arg0) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
}
