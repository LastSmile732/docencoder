/*
 * CertEncryptPdf.java
 * Shawn Chen 01/05/2014
 * Jrsys International Corp.
 */
package com.jrsys.pdfencoder;

import java.io.*;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.regex.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.Base64;


/**
 * Encrypts a PDF document. It needs iText (http://www.lowagie.com/iText).
 * Based on tools written by Paulo Soares
 * @author Shawn Chen
 */
public class CertEncryptPdf {
    
    private final static int INPUT_FILE = 0;
    private final static int OUTPUT_FILE = 1;
    private final static int CERTIFICATE = 2;
    private final static int PERMISSIONS = 3;
    private final static int STRENGTH = 4;
    private final static String CERT_PATTERN = "([^\\s]+(\\.(?i)(pem))$)";
    
    private Pattern pattern;
    private Matcher matcher;
    private static final int encryptionType = PdfWriter.ENCRYPTION_AES_128;
    private File inputPDF;
    private String inputFileName;
    private File ouputPDF;
    private File cert;
    private String permission;
    
    public CertEncryptPdf(){
    }
    
    public CertEncryptPdf(File inputPDF, String inputFileName, String permission){
    	this.setInputPDF(inputPDF);
    	this.setInputFileName(inputFileName);
    	this.setPermission(permission);
    }
    
    private final static int permit[] = {
    	PdfWriter.ALLOW_PRINTING,
    	PdfWriter.ALLOW_MODIFY_CONTENTS,
    	PdfWriter.ALLOW_COPY,
    	PdfWriter.ALLOW_MODIFY_ANNOTATIONS,
    	PdfWriter.ALLOW_FILL_IN,
    	PdfWriter.ALLOW_SCREENREADERS,
    	PdfWriter.ALLOW_ASSEMBLY,
        PdfWriter.ALLOW_DEGRADED_PRINTING};

    private static void usage() {
        System.out.println("usage: input_file output_file certificate_file permissions");
        System.out.println("permissions is 8 digit long 0 or 1. Each digit has a particular security function:");
        System.out.println();
        System.out.println("AllowPrinting");
        System.out.println("AllowModifyContents");
        System.out.println("AllowCopy");
        System.out.println("AllowModifyAnnotations");
        System.out.println("AllowFillIn (128 bit only)");
        System.out.println("AllowScreenReaders (128 bit only)");
        System.out.println("AllowAssembly (128 bit only)");
        System.out.println("AllowDegradedPrinting (128 bit only)");
        System.out.println("Example permissions to copy and print would be: 10100000");
    }
    
    /**
     * Check the input parameter, returns true if the parameter matches PEM file extension.
     * @param String inputString
     * @return boolean
     */
    public boolean validateCertPatt(final String inputStr){
		this.pattern = Pattern.compile(CERT_PATTERN);
    	this.matcher = pattern.matcher(inputStr);
    	
    	return matcher.matches(); 	
    }
     
    /**
     * Adds PEM certificate header and footer to input PEM string, returns as BufferedInputStream
     * @param String inputPEM
     * @return BufferedInputStream
     */
    public BufferedInputStream getCertByteStream(String inputPEM){
    	String cert_begin = "-----BEGIN CERTIFICATE-----";
        String end_cert = "-----END CERTIFICATE-----";
        String allcert = cert_begin + inputPEM + end_cert;   
        
        ByteArrayInputStream stream = null;
		try {
			stream = new ByteArrayInputStream(allcert.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("Cannot convert String to ByteArray in UTF-8");
		}            
        BufferedInputStream bis = new BufferedInputStream(stream);
        
        return bis;
    }
    
    /**
     * Decodes PEM certificate to DER byte array with Base64 decoder
     * @param String inputDecStr
     * @return BufferedInputStream
     */
    public BufferedInputStream getDecodedStream(String inputDecStr){
    	
        byte[] allcert = Base64.decode(inputDecStr);
        ByteArrayInputStream stream = new ByteArrayInputStream(allcert);            
        BufferedInputStream bis = new BufferedInputStream(stream);
        
        return bis;
    }
    
    /**
     * Reads the PEM certificate with the file path and file name, returns BufferedInputStream
     * @param String fileStr
     * @return BufferedInputStream
     */
    public BufferedInputStream getStreamFromFile(String fileStr){
        FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileStr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        BufferedInputStream bis = new BufferedInputStream(fis);
        
        return bis;
    }
    
    /**
     * Reads the certificate File object, returns BufferedInputStream
     * @param File file
     * @return BufferedInputStream
     */
    public BufferedInputStream getStreamFromFile(File file){
        FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        BufferedInputStream bis = new BufferedInputStream(fis);
        
        return bis;
    }
    
    /**
     * getPermissions
     * @param String permission
     * @return Integer permission
     */
    public int getPermissions(String argPermission){
    	int permissions = 0;
        for (int k = 0; k < argPermission.length(); ++k) {
            permissions |= (argPermission.charAt(k) == '0' ? 0 : permit[k]);
        }
    	return permissions;
    }
    
    /**
     * Generates a java.security.cert.Certificate with an input stream
     * @param BufferedInputStream
     * @return Certificate
     */  
    public Certificate genCert(BufferedInputStream bis){ 	
    	Certificate cert = null; 
        try {
        	CertificateFactory cf = CertificateFactory.getInstance("X.509");
			while (bis.available() > 0) {
			    cert = cf.generateCertificate(bis);
			    //System.out.println(cert.toString());
			}
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
        return cert;
    }
    /**
     * Encrypts a PDF file in Web with a certificate imported from the constructor
     * @param none
     * @return File
     */
    public File encryptFileWeb(File certificate){
    	Security.addProvider(new BouncyCastleProvider());
    	this.setCert(certificate);
    	FileInputStream pdfis = null;
    	FileOutputStream fout = null;
    	PdfReader reader = null;
    	BufferedInputStream bis = null;
    	this.setOuputPDF(new File(this.getInputFileName()));
    	try {
    		bis = this.getStreamFromFile(getCert());
        	pdfis = new FileInputStream(getInputPDF());
			reader = new PdfReader(pdfis);
			fout = new FileOutputStream(getOuputPDF());
			Certificate certObj = genCert(bis);
			PdfStamper stamper = new PdfStamper(reader,fout);
			stamper.setEncryption(new Certificate[]{certObj}, new int[]{getPermissions(getPermission())}, encryptionType);
            stamper.close(); // IOexception
            fout.close(); // IOexception
    	}
    	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    catch (DocumentException e) {
	        e.printStackTrace();
	    }
    	catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return this.getOuputPDF();
    }
    
    /**
     * Encrypts a PDF file
     * @param args
     */
    public void encryptFile(String args[]){
    	Security.addProvider(new BouncyCastleProvider());
    	PdfReader reader = null;
        BufferedInputStream bis = null;
        FileOutputStream fout = null;
        
        System.out.println("Reading... " + args[INPUT_FILE]);
        System.out.println("Writing... " + args[OUTPUT_FILE]);
        System.out.println("Permission: " + args[PERMISSIONS]);	
    	int permission = getPermissions(args[PERMISSIONS]);
    	System.out.println("Permission Output: " + permission);	
        if(validateCertPatt(args[CERTIFICATE])){
        	bis = getStreamFromFile(args[CERTIFICATE]);
           	System.out.println("PEM Certificate found: " + args[CERTIFICATE]);
        }else{
           	bis = getDecodedStream(args[CERTIFICATE]);
           	System.out.println("Loading encoded certificate.");
        }
    	
    	try {
            reader = new PdfReader(args[INPUT_FILE]);
            fout = new FileOutputStream(args[OUTPUT_FILE]);  	
        	Certificate cert = genCert(bis);
			PdfStamper stamper = new PdfStamper(reader,fout);        
            stamper.setEncryption(new Certificate[]{cert}, new int[]{permission}, encryptionType);
            stamper.close(); // IOexception
            fout.close(); // IOexception
            
            System.out.println("--Encryption Completed.");
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (DocumentException e) {
            e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
    
    /**
     * Encrypts a PDF document.
     * 
     * @param args input_file output_file certificate_file permissions
     */
    public static void main (String args[]) {
        System.out.println("PDF document encryptor");
        if (args.length < STRENGTH || args[PERMISSIONS].length() != 8) {
        	System.out.println("args length: " + args.length + " args strength: "+ STRENGTH);
        	usage();
            return;
        }
        
        CertEncryptPdf cePdf = new CertEncryptPdf();
        cePdf.encryptFile(args);
    }

	public File getInputPDF() {
		return inputPDF;
	}

	public void setInputPDF(File inputPDF) {
		this.inputPDF = inputPDF;
	}

	public File getOuputPDF() {
		return ouputPDF;
	}

	public void setOuputPDF(File ouputPDF) {
		this.ouputPDF = ouputPDF;
	}

	public File getCert() {
		return cert;
	}

	public void setCert(File cert) {
		this.cert = cert;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
}