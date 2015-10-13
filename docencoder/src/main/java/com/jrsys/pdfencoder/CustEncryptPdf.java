package com.jrsys.pdfencoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.tools.EncryptPdf;

/**
 * Encrypts a PDF document with a password. It needs iText (http://www.lowagie.com/iText).
 * Based on tools written by Paulo Soares
 * @author Shawn Chen
 */
public class CustEncryptPdf extends EncryptPdf{
	
    public static final int encryptionType = PdfWriter.ENCRYPTION_AES_128;
    private File inputPDF;
    private String inputFileName;
    private File ouputPDF;
    private String permission;
    private String password;
	
	public CustEncryptPdf(){super();}
	
	public CustEncryptPdf(File inputPDF, String inputFileName, String permission){
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
     * Encrypts a PDF file in Web with a password imported from the constructor
     * @param
     * @return File
     */
    public File encryptFilePassword(String password){
    	Security.addProvider(new BouncyCastleProvider());
    	this.setPassword(password);
    	FileInputStream pdfis = null;
    	FileOutputStream fout = null;
    	PdfReader reader = null;
    	this.setOuputPDF(new File(this.getInputFileName()));
    	try {
        	pdfis = new FileInputStream(getInputPDF());
			reader = new PdfReader(pdfis);
			fout = new FileOutputStream(getOuputPDF());
			PdfStamper stamper = new PdfStamper(reader,fout);
			stamper.setEncryption(getPassword().getBytes(), getPassword().getBytes(), getPermissions(getPermission()), encryptionType);
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
     * Execute main() function in EncryptPdf from iText2.1.7
     * @param String[] args
     */
	public void run(String[] args){
		super.main(args);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public File getInputPDF() {
		return inputPDF;
	}

	public void setInputPDF(File inputPDF) {
		this.inputPDF = inputPDF;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public File getOuputPDF() {
		return ouputPDF;
	}

	public void setOuputPDF(File ouputPDF) {
		this.ouputPDF = ouputPDF;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
