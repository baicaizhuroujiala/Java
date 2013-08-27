import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQSequence;

import ch.ethz.mxquery.xqj.MXQueryXQDataSource;

public class RWD2CA {
	private XQDataSource xqjd = null;
	private XQConnection xqjc = null;
	private XQExpression xqje = null;
	private XQSequence xqjs = null;

	RWD2CA() {
		try {
			xqjd = new MXQueryXQDataSource();
			xqjc = xqjd.getConnection();
			xqje = xqjc.createExpression();
		} catch (XQException e) {
			e.printStackTrace();
		}
	}

	public void processFile(String ifile, String xfile, String cfFile, String ofile) {
		try {
			// load xquery from file
			byte[] encoded = Files.readAllBytes(Paths.get(xfile));
			String query = "declare variable $configName as xs:string :=\"file:///"+ cfFile.replace('\\', '/') +"\";\n";
			query += "declare variable $fileName as xs:string :=\"file:///"+ ifile.replace('\\', '/') +"\";\n";
			query += StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
			
			// run query
			xqjs = xqje.executeQuery(query);

			// save result to file
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(ofile));
			Properties serializationProps = new java.util.Properties();
			// make sure we output xml
			serializationProps.setProperty("method", "xml");
			// pretty printing
			serializationProps.setProperty("indent", "yes");
			// serialize as UTF-8
			serializationProps.setProperty("encoding", "UTF-8");
			// want an XML declaration
			serializationProps.setProperty("omit-xml-declaration", "no");
			//while (xqjs.next()) {
			//	System.out.println((String)xqjs.getObject());
			//}
			if(xqjs.next()){
				xqjs.writeSequence(fos, serializationProps);
			}
			fos.close();
			xqjc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void processFolder(String inFolder, String xqFile, String cfFile, String outFloder) {
		File folder = new File(inFolder);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				processFile(inFolder + "\\" + file.getName(), xqFile, cfFile, outFloder + "\\" + file.getName());
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 8) {
			System.out.println("Usage: java RWD2CA -i inputFolder -x xqueryFile -c configFile -o outputFolder");
		} else {
			RWD2CA transformer = new RWD2CA();
			transformer.processFolder(args[1], args[3], args[5], args[7]);
		}
	}
}



