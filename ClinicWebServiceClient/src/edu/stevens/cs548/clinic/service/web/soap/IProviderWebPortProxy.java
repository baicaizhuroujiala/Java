package edu.stevens.cs548.clinic.service.web.soap;

public class IProviderWebPortProxy implements edu.stevens.cs548.clinic.service.web.soap.IProviderWebPort {
  private String _endpoint = null;
  private edu.stevens.cs548.clinic.service.web.soap.IProviderWebPort iProviderWebPort = null;
  
  public IProviderWebPortProxy() {
    _initIProviderWebPortProxy();
  }
  
  public IProviderWebPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initIProviderWebPortProxy();
  }
  
  private void _initIProviderWebPortProxy() {
    try {
      iProviderWebPort = (new edu.stevens.cs548.clinic.service.web.soap.ProviderWebServiceLocator()).getProviderWebPort();
      if (iProviderWebPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iProviderWebPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iProviderWebPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iProviderWebPort != null)
      ((javax.xml.rpc.Stub)iProviderWebPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public edu.stevens.cs548.clinic.service.web.soap.IProviderWebPort getIProviderWebPort() {
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort;
  }
  
  public long createProvider(java.lang.String arg0, long arg1, java.lang.String arg2) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn{
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort.createProvider(arg0, arg1, arg2);
  }
  
  public java.lang.Long[] getTreatmentIdsByProviderId(long arg0) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn{
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort.getTreatmentIdsByProviderId(arg0);
  }
  
  public edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsOfPatientResponseReturn[] getTreatmentsOfPatient(long arg0, long arg1) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.PatientServiceExn, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn{
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort.getTreatmentsOfPatient(arg0, arg1);
  }
  
  public edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsResponseReturn[] getTreatments(long arg0, java.lang.Long[] arg1) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn{
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort.getTreatments(arg0, arg1);
  }
  
  public edu.stevens.cs548.clinic.service.web.soap.ProviderDTO getProviderByProviderId(long arg0) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn{
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort.getProviderByProviderId(arg0);
  }
  
  public edu.stevens.cs548.clinic.service.web.soap.ProviderDTO[] getProviderByName(java.lang.String arg0) throws java.rmi.RemoteException, edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn{
    if (iProviderWebPort == null)
      _initIProviderWebPortProxy();
    return iProviderWebPort.getProviderByName(arg0);
  }
  
  
}