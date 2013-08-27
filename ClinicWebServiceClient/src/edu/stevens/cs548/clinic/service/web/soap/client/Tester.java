package edu.stevens.cs548.clinic.service.web.soap.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;
import javax.xml.rpc.ServiceException;


import edu.stevens.cs548.clinic.service.web.soap.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsOfPatientResponseReturn;
import edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsResponseReturn;
import edu.stevens.cs548.clinic.service.web.soap.IPatientWebPort;
import edu.stevens.cs548.clinic.service.web.soap.IProviderWebPort;
import edu.stevens.cs548.clinic.service.web.soap.PatientDTO;
import edu.stevens.cs548.clinic.service.web.soap.PatientServiceExn;
import edu.stevens.cs548.clinic.service.web.soap.PatientWebService;
import edu.stevens.cs548.clinic.service.web.soap.PatientWebServiceLocator;
import edu.stevens.cs548.clinic.service.web.soap.ProviderDTO;
import edu.stevens.cs548.clinic.service.web.soap.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.web.soap.ProviderWebService;
import edu.stevens.cs548.clinic.service.web.soap.ProviderWebServiceLocator;
import edu.stevens.cs548.clinic.service.web.soap.SurgeryType;

public class Tester {

	private class ServiceError extends Exception {
		private static final long serialVersionUID = 1L;

		ServiceError(String s) {
			super(s);
		}
	}

	//component using logging
	Logger logger = Logger
			.getLogger("edu.stevens.cs548.clinic.service.web.soap.client.Tester");

	/*
	 * Input file. Defaults to standard input.
	 */
	private String InputFileName = "<stdin>";

	private BufferedReader InputFile = new BufferedReader(
			new InputStreamReader(System.in));

	/*
	 * Output file. Defaults to standard output.
	 */
	private String OutputFileName;

	private PrintWriter OutputFile = new PrintWriter(new OutputStreamWriter(
			System.out));

	/*
	 * Endpoint URL for the Web service.
	 */
	private URL endpointUrl = null;

	/*
	 * Client proxy for the Web service. Generated from the endpoint URL.
	 */
	private IPatientWebPort patient = null;
	private IProviderWebPort provider = null;

	/*
	 * Line number in input file, for error reporting.
	 */
	private int lineNo = 0;

	//Read a command from the input.
	private String readLine() {
		try {
			lineNo++;
			return InputFile.readLine();
		} catch (IOException e) {
			error("Error reading input: " + e);
			return null;
		}
	}

	//Print out error.
	private void error(String msg) {
		if (lineNo > 0) {
			System.err.print(lineNo + ": ");
		}
		System.err.println("** Error **");
		System.err.println(msg);
		System.exit(-1);
	}

	//Print out remote error.
	private void remoteError(Exception e) {
		e.printStackTrace(System.err);
		error("Network error: " + e);
	}

	//Print out warning.
	private void warning(String msg) {
		if (lineNo > 0) {
			System.err.print(lineNo + ": ");
		}
		System.err.println("** Warning **");
		System.err.println(msg);
	}

	//Print s to OutPutFile.
	private void print(String s) {
		OutputFile.print(s);
	}

	//Print s to OutPutFile and add a newline.
	private void println(String s) {
		OutputFile.println(s);
	}
	
	//Print a newline to OutPutFile.
	private void newline() {
		OutputFile.println();
	}

	//Print a long number to OutPutFile.
	private void displayLong(long n) {
		OutputFile.print(n);
	}
	
	//Print a long list to OutPutFile.
	private void displayLongList(Long[] list) {
		for(int i=0 ; i < list.length ; i++){
			OutputFile.print(list[i]);
		}
		newline();
	}

	//Display a treatment according the type of treatment.
	private void displayTreatment(String prefix, GetTreatmentsResponseReturn t,
			String suffix) {
		print(prefix);
		logger.info("Displaying a treatment.");
		print("Treatment(){");
		print("diagnosis=");
		print(t.getDiagnosis());
		print(",treatment=");
		if (t.getDrugTreatment() != null) {
			DrugTreatmentType dt = t.getDrugTreatment();
			print("DrugTreatment{");
			print("name=");
			print(dt.getName());
			print(",dosage=");
			print(Float.toString(dt.getDosage()));
			print("}");
		} else if (t.getRadiology() != null) {
			Date[] rad = t.getRadiology();
			DateFormat datefmt = DateFormat.getInstance();
			print("Radiology{dates=[");
			for (int i = 0; i < rad.length - 1; i++) {
				print(datefmt.format(rad[i]));
				print(",");
			}
			if (rad.length > 0) {
				print(datefmt.format(rad[rad.length - 1]));
			}
			print("]}");
		} else if (t.getSurgery() != null) {
			SurgeryType st = t.getSurgery();
			print("Surgery{");
			print("date=");
			print(DateFormat.getInstance().format(st.getDate()));
			print("}");
		}
		print("}");
	}

	//Display a treatment list.
	private void displayTreatmentList(GetTreatmentsResponseReturn[] ts) {
		println("[");
		for (GetTreatmentsResponseReturn t : ts) {
			displayTreatment("  ", t, ",\n");
		}
		println("]");
	}
	
	//Display a treatment list of a patient.
	private void displayTreatmentListOfPatient(GetTreatmentsOfPatientResponseReturn[] ts) {
		println("[");
		for (GetTreatmentsOfPatientResponseReturn t : ts) {
			displayTreatmentOfPatient("  ", t, ",\n");
		}
		println("]");
	}
	
	//Display a treatment of a patient.
	private void displayTreatmentOfPatient(String prefix, GetTreatmentsOfPatientResponseReturn t,
			String suffix) {
		print(prefix);
		logger.info("Displaying a treatment.");
		print("Treatment(){");
		print("diagnosis=");
		print(t.getDiagnosis());
		print(",treatment=");
		if (t.getDrugTreatment() != null) {
			DrugTreatmentType dt = t.getDrugTreatment();
			print("DrugTreatment{");
			print("name=");
			print(dt.getName());
			print(",dosage=");
			print(Float.toString(dt.getDosage()));
			print("}");
		} else if (t.getRadiology() != null) {
			Date[] rad = t.getRadiology();
			DateFormat datefmt = DateFormat.getInstance();
			print("Radiology{dates=[");
			for (int i = 0; i < rad.length - 1; i++) {
				print(datefmt.format(rad[i]));
				print(",");
			}
			if (rad.length > 0) {
				print(datefmt.format(rad[rad.length - 1]));
			}
			print("]}");
		} else if (t.getSurgery() != null) {
			SurgeryType st = t.getSurgery();
			print("Surgery{");
			print("date=");
			print(DateFormat.getInstance().format(st.getDate()));
			print("}");
		}
		print("}");
	}

	//Display a PatientDTO
	private void displayPatient(String prefix, PatientDTO p, String suffix) {
		print(prefix);
		logger.info("Displaying a patient DTO.");
		print("Patient(");
		print(Long.toString(p.getId()));
		print("){");
		print("name=");
		print(p.getName());
		print(",birthDate=");
		print(DateFormat.getInstance().format(p.getDob().getTime()));
		print(",patientId=");
		print(Long.toString(p.getPatientId()));
		print("}");

	}

	//Used to invoke displayPatient(String prefix, PatientDTO p, String suffix)
	private void displayPatient(PatientDTO p) {
		displayPatient("", p, "\n");
	}

	//Display a list of PatientDTO.
	private void displayPatientList(PatientDTO[] ps) {
		println("[");
		for (PatientDTO p : ps) {
			displayPatient("  ", p, ",\n");
		}
		println("]");
	}
	
	//Display a ProviderDTO.
	private void displayProvider(String prefix, ProviderDTO p, String suffix) {
		print(prefix);
		logger.info("Displaying a provider DTO.");
		print("Provider(");
		print(Long.toString(p.getId()));
		print("){");
		print("name=");
		print(p.getName());
		print(",providerId=");
		print(Long.toString(p.getProviderId()));
		print(",specialization=");
		print(p.getSpecialization());
		print("}");

	}

	//Used to invoke displayProvider(String prefix, ProviderDTO p, String suffix)
	private void displayProvider(ProviderDTO p) {
		displayProvider("", p, "\n");
	}

	//Display a list of ProviderDTO.
	private void displayProviderList(ProviderDTO[] ps) {
		println("[");
		for (ProviderDTO p : ps) {
			displayProvider("  ", p, ",\n");
		}
		println("]");
	}

	//Add a patient using name, age, day of birth, patientId.
	private long addPatient(String[] args) throws ServiceError {
		if (args.length != 4) {
			error("Usage: patient addPatient name age dob patient-id");
		}
		String name = args[0];
		int age = Integer.valueOf(args[1]);
		Calendar dob = DatatypeConverter.parseDate(args[2]);
		long patientId = Long.parseLong(args[3]);	//add to log
		logger.info("Adding a patient (name=" + name + ").");
		try {
			return patient.createPatient(name, age, dob, patientId);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			remoteError(e);
			return -1;
		}
	}

	//Get a PatientDTO using DbId.
	private PatientDTO getPatientByDbId(String[] args) throws ServiceError {
		if (args.length != 1) {
			error("Usage: patient getPatientByDbId patient-key");
		}
		long patientKey = Long.parseLong(args[0]);
		try {
			return patient.getPatientByDbId(patientKey);
		} catch (RemoteException e) {
			error("Network error: " + e);
			return null;
		} catch (Exception e) {
			throw new ServiceError(e.toString());
		}
	}

	//Get a PatientDTO using PatientId.
	private PatientDTO getPatientByPatId(String[] args) throws ServiceError {
		if (args.length != 1) {
			error("Usage: patient getPatientByPatId patient-id");
		}
		long patientId = Long.parseLong(args[0]);
		logger.info("Getting a patient by patient id (" + patientId + ").");
		try {
			return patient.getPatientByPatientId(patientId);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			remoteError(e);
			return null;
		}
	}

	//Get a array of PatientDTOs using patient's name and day of birth.
	private PatientDTO[] getPatientsByNameDob(String[] args) {
		if (args.length != 2) {
			error("Usage: patient getPatientsByNameDob name dob");
		}
		String name = args[0];
		Calendar dob = DatatypeConverter.parseDate(args[1]);
		try {
			return patient.getPatientByNameDob(name, dob);
		} catch (RemoteException e) {
			remoteError(e);
			return null;
		}
	}

	//Delete a patient using patient name and DbId.
	private void deletePatient(String[] args) throws ServiceError {
		if (args.length != 2) {
			error("Usage: patient deletePatient name key");
		}
		String name = args[0];
		long id = Long.parseLong(args[1]);
		try {
			patient.deletePatient(name, id);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			remoteError(e);
		}
	}

	//Add drug treatment using id, providerId, diagnosis.
	private void addDrugTreatment(String[] args) throws ServiceError {
		if (args.length != 5) {
			error("Usage: patient addDrugTreatment patient-key provider-id diagnosis drug dosage");
		}
		long id = Long.parseLong(args[0]);
		long providerId = Long.parseLong(args[1]);
		String diagnosis = args[2];
		String drug = args[3];
		float dosage = Float.parseFloat(args[4]);
		try {
			patient.addDrugTreatment(id, providerId, diagnosis, drug, dosage);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			remoteError(e);
		}
	}
	
	//Add a surgery using id, providerId, diagnosis, date.
	private void addSurgery(String[] args) throws ServiceError {
		if (args.length != 4) {
			error("Usage: patient addSurgery patient-key provider-id diagnosis date");
		}
		long id = Long.parseLong(args[0]);
		long providerId = Long.parseLong(args[1]);
		String diagnosis = args[2];
		Calendar date = DatatypeConverter.parseDate(args[3]);
		try {
			patient.addSurgery(id, diagnosis, date, providerId);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			remoteError(e);
		}
	}

	//Get treatments of a patient using patient DbId and treatment ids.
	private GetTreatmentsResponseReturn[] getTreatmentsInPatient(String[] args)
			throws ServiceError {
		if (args.length < 1) {
			error("Usage: patient getTreatments patient-key tid1 tid2 ...");
		}
		long id = Long.parseLong(args[0]);
		Long[] tids = new Long[args.length];
		for (int i = 1; i < args.length; i++) {
			tids[i - 1] = Long.parseLong(args[i]);
		}
		try {
			return patient.getTreatments(id, tids);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			error("Network error: " + e);
			return null;
		}
	}

	//Delete treatment using patient DbId and treatment Id.
	private void deleteTreatment(String[] args) throws ServiceError {
		if (args.length != 2) {
			error("Usage: patient getTreatments patient-key tid");
		}
		long id = Long.parseLong(args[0]);
		long tid = Long.parseLong(args[1]);
		try {
			patient.deleteTreatment(id, tid);
		} catch (RemoteException e) {
			remoteError(e);
		} catch (Exception e) {
			throw new ServiceError(e.toString());
		}
	}

	//Get site information of web service.
	private String siteInfo(String[] args) {
		if (args.length > 0) {
			error("Usage: patient siteInfo");
			return null;
		}
		try {
			return patient.siteInfo();
		} catch (RemoteException e) {
			remoteError(e);
			return null;
		}
	}

	//Add provider using name, providerId, specialization.
	private long addProvider(String[] args) throws ServiceError {
		if (args.length != 3) {
			error("Usage: provider addProvider name providerId specialization");
		}
		String name = args[0];
		long providerId = Integer.valueOf(args[1]);
		String specialization = args[2];
		logger.info("Adding a provider (name=" + name + ").");
		try {
			return provider.createProvider(name, providerId, specialization);
		} catch (ProviderServiceExn e){
			throw new ServiceError(e.toString());
		} catch (RemoteException e){
			remoteError(e);
			return -1;
		}
	}
	
	//Get treatments of a patient using providerId and patientId.
	private GetTreatmentsOfPatientResponseReturn[] getTreatmentsOfPatient(String[] args) throws ServiceError {
		if (args.length != 2){
			error("Usage: provider getTreatmentsOfPatient");
		}
		int providerId=Integer.valueOf(args[0]);
		int patientId=Integer.valueOf(args[1]);
		try {
			return provider.getTreatmentsOfPatient(providerId, patientId);
		} catch (ProviderServiceExn e){
			throw new ServiceError(e.toString());
		} catch (RemoteException e){
			remoteError(e);
			return null;
		}
	}

	//Get a long array of treatmentIds using provider Id.
	private Long[] getTreatmentIdsByProviderId(String[] args) throws ServiceError {
		if (args.length != 1){
			error("Usage: provider getTreatmentIdsByProviderId");
		}
		int providerId = Integer.valueOf(args[0]);
		try {
			return provider.getTreatmentIdsByProviderId(providerId);
		} catch (ProviderServiceExn e){
			throw new ServiceError(e.toString());
		} catch (RemoteException e){
			remoteError(e);
			return null;
		}
	}
	
	//Get a list of treatments using providerId and treatmentIds.
	private GetTreatmentsResponseReturn[] getTreatmentsInProvider(String[] args) throws ServiceError {
		if (args.length < 1) {
			error("Usage: provider getTreatments providerId tid1 tid2 ...");
		}
		long providerId = Long.parseLong(args[0]);
		Long[] tids = new Long[args.length];
		for (int i = 1; i < args.length; i++) {
			tids[i - 1] = Long.parseLong(args[i]);
		}
		try {
			return provider.getTreatments(providerId, tids);
		} catch (ProviderServiceExn e){
			throw new ServiceError(e.toString());
		} catch (RemoteException e){
			remoteError(e);
			return null;
		}
	}

	//Get a array of ProviderDTO using provider name.
	private ProviderDTO[] getProviderByName(String[] args) throws ServiceError {
		if (args.length < 1) {
			error("Usage: provider getProviderByName name");
		}
		String name = args[0];
		try {
			return provider.getProviderByName(name);
		} catch (ProviderServiceExn e){
			throw new ServiceError(e.toString());
		} catch (RemoteException e){
			remoteError(e);
			return null;
		}
	}

	//Get ProviderDTO using providerId.
	private ProviderDTO getProviderByProviderId(String[] args) throws ServiceError {
		if (args.length < 1) {
			error("Usage: provider getProviderByProviderId providerId");
		}
		Long providerId = Long.parseLong(args[0]);
		try {
			return provider.getProviderByProviderId(providerId);
		} catch (ProviderServiceExn e){
			throw new ServiceError(e.toString());
		} catch (RemoteException e){
			remoteError(e);
			return null;
		}
	}

	//Used to invoke different method of patient service according the command.
	public void invokePatientService(String cmd, String[] args) {
		try {
			if ("addPatient".equals(cmd)) {
				displayLong(addPatient(args));
				newline();
			} else if ("getPatientByDbId".equals(cmd)) {
				displayPatient(getPatientByDbId(args));
				newline();
			} else if ("getPatientByPatId".equals(cmd)) {
				displayPatient(getPatientByPatId(args));
				newline();
			} else if ("getPatientsByNameDob".equals(cmd)) {
				displayPatientList(getPatientsByNameDob(args));
				newline();
			} else if ("deletePatient".equals(cmd)) {
				deletePatient(args);
			} else if ("addDrugTreatment".equals(cmd)) {
				addDrugTreatment(args);
			} else if ("addSurgery".equals(cmd)) {
				addSurgery(args);
			} else if ("getTreatments".equals(cmd)) {
				displayTreatmentList(getTreatmentsInPatient(args));
				newline();
			} else if ("deleteTreatment".equals(cmd)) {
				deleteTreatment(args);
			} else if ("siteInfo".equals(cmd)) {
				println(siteInfo(args));
			} else {
				error("Unrecognized patient service command: " + cmd);
			}
		} catch (ServiceError e) {
			error("Service raised exception: " + e);
		}
	}

	//Used to invoke different method of provider service according the command.
	public void invokeProviderService(String cmd, String[] args) {
		try {
			if ("addProvider".equals(cmd)) {
				displayLong(addProvider(args));
				newline();
			} else if ("getProviderByProviderId".equals(cmd)){
				displayProvider(getProviderByProviderId(args));
				newline();
			} else if ("getProviderByName".equals(cmd)){
				displayProviderList(getProviderByName(args));
				newline();
			} else if ("getTreatments".equals(cmd)){
				displayTreatmentList(getTreatmentsInProvider(args));
				newline();
			} else if ("getTreatmentIdsByProviderId".equals(cmd)){
				displayLongList(getTreatmentIdsByProviderId(args));
				newline();
			} else if ("getTreatmentsOfPatient".equals(cmd)){
				displayTreatmentListOfPatient(getTreatmentsOfPatient(args));
				newline();
			} else {
				error("Unrecognized provider service command: " + cmd);
			}
		} catch (ServiceError e) {
			error("Service raised exception: " + e);
		}
	}

	//Return current working directory's absolute path.
	private String currentWorkingDir() {
		return new File(".").getAbsolutePath();
	}

	//Process the command line arguments
	private Tester processArgs(String[] args) {
		/*
		 * Process the command line arguments:
		 * 
		 * --input filename -i filename File containing a list of commands.
		 * Default is standard input.
		 * 
		 * --output filename -o filename File to write out results of commands.
		 * Default is standard output.
		 * 
		 * --url endpoint-url -u endpoint-url Endpoint URL for the service. No
		 * default.
		 */
		for (int iarg = 0; iarg < args.length; iarg++) {
			if ("--input".equals(args[iarg]) || "-i".equals(args[iarg])) {
				if (iarg + 1 < args.length) {
					InputFileName = args[++iarg];
					try {
						InputFile = new BufferedReader(new FileReader(
								InputFileName));
					} catch (FileNotFoundException e) {
						error("Input file not found: " + InputFileName
								+ "\nDirectory: " + currentWorkingDir());
					}
				} else {
					error("Missing value for --input or -i option.");
				}
			} else if ("--output".equals(args[iarg]) || "-o".equals(args[iarg])) {
				if (iarg + 1 < args.length) {
					OutputFileName = args[++iarg];
					try {
						OutputFile = new PrintWriter(new FileWriter(
								OutputFileName));
					} catch (IOException e) {
						error("Problem opening output file: " + OutputFileName
								+ "\nDirectory: " + currentWorkingDir());
					}
				} else {
					error("Missing value for --output or -o option");
				}
			} else if ("--patienturl".equals(args[iarg]) || "-pau".equals(args[iarg])) {
				if (iarg + 1 < args.length) {
					try {
						endpointUrl = new URL(args[++iarg]);
						PatientWebService serviceOfPatient = new PatientWebServiceLocator();
						this.patient = serviceOfPatient.getPatientWebPort(endpointUrl);
					} catch (MalformedURLException e) {
						error("Bad service URL: " + args[iarg]);
					} catch (ServiceException e) {
						error("Service exception: " + e);
					}
				}
			}
			else if ("--providerurl".equals(args[iarg]) || "-pru".equals(args[iarg])) {
				if (iarg + 1 < args.length) {
					try {
						endpointUrl = new URL(args[++iarg]);
						ProviderWebService serviceOfProvider = new ProviderWebServiceLocator();
						this.provider = serviceOfProvider.getProviderWebPort(endpointUrl);
					} catch (MalformedURLException e) {
						error("Bad service URL: " + args[iarg]);
					} catch (ServiceException e) {
						error("Service exception: " + e);
					}
				}
			}
		}

		if (this.patient == null && this.provider == null) {
			error("You must specify an endpoint URL with the --url or -u option.");
		}

		return this;
	}

	//Process a single line command.
	private void processLine(String[] args) {
		if (args.length < 2) {
			error("Usage: (patient|provider) command arg1 ... argn");
		} else if ("patient".equals(args[0])) {
			invokePatientService(args[1],
					Arrays.copyOfRange(args, 2, args.length));
		} else if ("provider".equals(args[0])) {
			invokeProviderService(args[1],
					Arrays.copyOfRange(args, 2, args.length));
		} else {
			error("Service name must be \"patient\" or \"provider\".");
		}
	}

	//Process several lines.
	private void processLines() {
		String line;
		while ((line = this.readLine()) != null) {
			String[] args = line.split("\\s");
			processLine(args);
		}
		try {
			InputFile.close();
		} catch (IOException e) {
			warning("Failed to close input: " + e);
		}
		OutputFile.close();
	}

	//Empty construct method.
	public Tester() {
	}

	//Main function.
	public static void main(String[] args) {
		new Tester().processArgs(args).processLines();
	}

}
