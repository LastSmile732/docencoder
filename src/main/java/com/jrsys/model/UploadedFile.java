package com.jrsys.model;
import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {
	
	private MultipartFile pdf;
	private MultipartFile certificate;
	private String permissionPDF = "11111111";
	private String password;
    /**
	 * @return the permissionPDF
	 */
	public String getPermissionPDF() {
		return permissionPDF;
	}

	/**
	 * @param permissionPDF the permissionPDF to set
	 */
	public void setPermissionPDF(String permissionPDF) {
		this.permissionPDF = permissionPDF;
	}
	
	/**
	 * @return the pdf
	 */
	public MultipartFile getPdf() {
		return pdf;
	}

	/**
	 * @param pdf the pdf to set
	 */
	public void setPdf(MultipartFile pdf) {
		this.pdf = pdf;
	}

	
	/**
	 * @return the certificate
	 */
	public MultipartFile getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(MultipartFile certificate) {
		this.certificate = certificate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
