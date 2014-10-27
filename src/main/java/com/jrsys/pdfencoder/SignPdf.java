package com.jrsys.pdfencoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SignPdf {
	
    private static final int encryptionType = PdfWriter.ENCRYPTION_AES_128;
	private File inputPDF;
	private File inputCert;
    private String inputFileName;
    private String permission;
    private String pincode;
    private File ouputPDF;
    private PrivateKey privateKey;
		
	public SignPdf(){
    }
	
	public SignPdf(File inputPDF, File inputCert,String pincode, String inputFileName, String permission){	
    	this.setInputPDF(inputPDF);
    	this.setInputCert(inputCert);
    	this.setPincode(pincode);
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
	
	public File signPdfFile(){
		Security.addProvider(new BouncyCastleProvider());
		FileOutputStream fout = null;
		this.setOuputPDF(new File(this.getInputFileName()));
		//BufferedInputStream bis = null;
		
		try {
			//bis = this.getStreamFromFile(this.getInputCert());
			//Certificate certObj = genCert(bis);	
			KeyStore ks = KeyStore.getInstance("pkcs12");
			ks.load(new FileInputStream(this.getInputCert()), this.getPincode().toCharArray());
			String alias = (String) ks.aliases().nextElement();
			privateKey = (PrivateKey) ks.getKey(alias, this.getPincode().toCharArray());
			
			Certificate[] chain = ks.getCertificateChain(alias);
			PdfReader reader = new PdfReader(this.getInputPDF().getAbsolutePath());
			fout = new FileOutputStream(getOuputPDF());
			PdfStamper stamper = PdfStamper.createSignature(reader, fout, '\0');
			
			int pm = this.getPermissions(getPermission());
			stamper.setEncryption(new Certificate[]{chain[0]}, new int[]{pm}, encryptionType);
			PdfSignatureAppearance sap = stamper.getSignatureAppearance();
			sap.setCrypto(privateKey, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
	       
			sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, null);
	       // sap.setExternalDigest(new byte[256], null, "RSA");
			
	        stamper.close();
			fout.close();
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.getOuputPDF();
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
	 * @return the inputPDF
	 */
	public File getInputPDF() {
		return inputPDF;
	}
	/**
	 * @param inputPDF the inputPDF to set
	 */
	public void setInputPDF(File inputPDF) {
		this.inputPDF = inputPDF;
	}
	/**
	 * @return the inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}
	/**
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}
	/**
	 * @return the password
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param password the password to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public File getInputCert() {
		return inputCert;
	}
	public void setInputCert(File inputCert) {
		this.inputCert = inputCert;
	}
	public File getOuputPDF() {
		return ouputPDF;
	}
	public void setOuputPDF(File ouputPDF) {
		this.ouputPDF = ouputPDF;
	}

}
