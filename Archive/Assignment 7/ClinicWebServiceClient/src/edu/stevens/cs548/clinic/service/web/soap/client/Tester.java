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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.rpc.ServiceException;

import edu.stevens.cs548.clinic.service.web.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.web.PatientDTO;
import edu.stevens.cs548.clinic.service.web.SurgeryType;
import edu.stevens.cs548.clinic.service.web.soap.GetTreatmentsResponseReturn;
import edu.stevens.cs548.clinic.service.web.soap.IPatientWebPort;
import edu.stevens.cs548.clinic.service.web.soap.PatientServiceExn;
import edu.stevens.cs548.clinic.service.web.soap.PatientWebService;
import edu.stevens.cs548.clinic.service.web.soap.PatientWebServiceLocator;

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

	/*
	 * Line number in input file, for error reporting.
	 */
	private int lineNo = 0;

	private String readLine() {
		/*
		 * Read a command from the input.
		 */
		try {
			lineNo++;
			return InputFile.readLine();
		} catch (IOException e) {
			error("Error reading input: " + e);
			return null;
		}
	}

	private void error(String msg) {
		if (lineNo > 0) {
			System.err.print(lineNo + ": ");
		}
		System.err.println("** Error **");
		System.err.println(msg);
		System.exit(-1);
	}

	private void remoteError(Exception e) {
		e.printStackTrace(System.err);
		error("Network error: " + e);
	}

	private void warning(String msg) {
		if (lineNo > 0) {
			System.err.print(lineNo + ": ");
		}
		System.err.println("** Warning **");
		System.err.println(msg);
	}

	private void print(String s) {
		OutputFile.print(s);
	}

	private void println(String s) {
		OutputFile.println(s);
	}
	
	private void newline() {
		OutputFile.println();
	}

	private void displayLong(long n) {
		OutputFile.print(n);
	}

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

	private void displayTreatmentList(GetTreatmentsResponseReturn[] ts) {
		println("[");
		for (GetTreatmentsResponseReturn t : ts) {
			displayTreatment("  ", t, ",\n");
		}
		println("]");
	}

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

	private void displayPatient(PatientDTO p) {
		displayPatient("", p, "\n");
	}

	private void displayPatientList(PatientDTO[] ps) {
		println("[");
		for (PatientDTO p : ps) {
			displayPatient("  ", p, ",\n");
		}
		println("]");
	}

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
			return patient.create(name, age, dob, patientId);
		} catch (PatientServiceExn e) {
			throw new ServiceError(e.toString());
		} catch (RemoteException e) {
			remoteError(e);
			return -1;
		}
	}

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

	private GetTreatmentsResponseReturn[] getTreatments(String[] args)
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
			} else if ("getPatientByNameDob".equals(cmd)) {
				displayPatientList(getPatientsByNameDob(args));
				newline();
			} else if ("deletePatient".equals(cmd)) {
				deletePatient(args);
			} else if ("addDrugTreatment".equals(cmd)) {
				addDrugTreatment(args);
			} else if ("getTreatments".equals(cmd)) {
				displayTreatmentList(getTreatments(args));
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

	public void invokeProviderService(String cmd, String[] args) {
		error("Unimplemented provider client.");
	}

	private String currentWorkingDir() {
		return new File(".").getAbsolutePath();
	}

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
			} else if ("--url".equals(args[iarg]) || "-u".equals(args[iarg])) {
				if (iarg + 1 < args.length) {
					try {
						endpointUrl = new URL(args[++iarg]);
						PatientWebService service = new PatientWebServiceLocator();
						this.patient = service.getPatientWebPort(endpointUrl);
						//this.patient = service.getPatientWebPort();
					} catch (MalformedURLException e) {
						error("Bad service URL: " + args[iarg]);
					} catch (ServiceException e) {
						error("Service exception: " + e);
					}
				}
			}
		}

		if (this.patient == null) {
			error("You must specify an endpoint URL with the --url or -u option.");
		}

		return this;
	}

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

	public Tester() {
	}

	public static void main(String[] args) {
		new Tester().processArgs(args).processLines();
	}

}
