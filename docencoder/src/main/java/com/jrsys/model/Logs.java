package com.jrsys.model;

import java.util.Calendar;

import javax.persistence.*;


//@Entity
//@Table(name = "logs")
public class Logs {
		//@Id
	    //@GeneratedValue
		private Integer id;
		
		//@Column(name = "datetime")
		private Calendar datetime;
		
		//@Column(name = "action")
		private String action;
		
		//@Column(name = "pdfname")
		private String pdfname;
		
		//@Column(name = "pdftype")
		private String pdftype;
		
		//@Column(name = "certname")
		private String certname;
		
		//@Column(name = "certtype")
		private String certtype;
		
		//@Column(name = "exception")
		private String exception;
		
		//@Column(name = "exceptionstack")
		private String exceptionstack;
		
		public Logs(Integer id,Calendar datetime,String action, String pdfname, String pdftype, String certname,
				String certtype, String password, String exception ,String exceptionstack){
			
			this.id = id;
			this.datetime = datetime;
			this.action = action;
			this.pdfname = pdfname;
			this.pdftype = pdftype;
			this.certname = certname;
			this.certtype = certtype;
			this.exception = exception;
			this.exceptionstack = exceptionstack;
		}
		
		public Logs(){
		}
		
		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return the datetime
		 */
		public Calendar getDatetime() {
			return datetime;
		}

		/**
		 * @param datetime the datetime to set
		 */
		public void setDatetime(Calendar datetime) {
			this.datetime = datetime;
		}

		/**
		 * @return the action
		 */
		public String getAction() {
			return action;
		}

		/**
		 * @param action the action to set
		 */
		public void setAction(String action) {
			this.action = action;
		}

		/**
		 * @return the pdfName
		 */
		public String getPdfName() {
			return pdfname;
		}

		/**
		 * @param pdfName the pdfName to set
		 */
		public void setPdfName(String pdfName) {
			this.pdfname = pdfName;
		}

		/**
		 * @return the pdftype
		 */
		public String getPdftype() {
			return pdftype;
		}

		/**
		 * @param pdftype the pdftype to set
		 */
		public void setPdftype(String pdftype) {
			this.pdftype = pdftype;
		}

		/**
		 * @return the certname
		 */
		public String getCertname() {
			return certname;
		}

		/**
		 * @param certname the certname to set
		 */
		public void setCertname(String certname) {
			this.certname = certname;
		}

		/**
		 * @return the certtype
		 */
		public String getCerttype() {
			return certtype;
		}

		/**
		 * @param certtype the certtype to set
		 */
		public void setCerttype(String certtype) {
			this.certtype = certtype;
		}

		/**
		 * @return the exception
		 */
		public String getException() {
			return exception;
		}

		/**
		 * @param exception the exception to set
		 */
		public void setException(String exception) {
			this.exception = exception;
		}

		/**
		 * @return the exceptionstack
		 */
		public String getExceptionstack() {
			return exceptionstack;
		}

		/**
		 * @param exceptionstack the exceptionstack to set
		 */
		public void setExceptionstack(String exceptionstack) {
			this.exceptionstack = exceptionstack;
		}
		
}
