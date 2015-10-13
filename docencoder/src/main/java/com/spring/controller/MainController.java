package com.spring.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jrsys.pdfencoder.CertEncryptPdf;
import com.jrsys.pdfencoder.CustEncryptPdf;
import com.jrsys.tools.ObjectCast;
import com.jrsys.model.Logs;
import com.jrsys.model.UploadedFile;

@Controller
public class MainController {
	
	//@Autowired
	//private LogService logService;
	
/*	private static final Logger logger = LoggerFactory
            .getLogger(MainController.class);*/

	public MainController(){

	}
	
	 @RequestMapping("/welcome")
	 public String welcome(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		 model.addAttribute("name", name);
		 System.out.println("welcome! "+name);
		 return "welcome";
	 }
	
	@RequestMapping(value="/billEncoder",method = RequestMethod.GET)  
    public ModelAndView billEnc_jsp(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, Model model){ 
        System.out.println("billEncoder.jsp");
		return new ModelAndView("billEncoder", "command", new UploadedFile());
    }
	@RequestMapping(value="/pdfEncoder",method = RequestMethod.GET)  
    public ModelAndView pdfEnc_jsp(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, Model model){  
        System.out.println("pdfEncoder.jsp");
        return new ModelAndView("pdfEncoder", "command", new UploadedFile());
    }
	
	@RequestMapping(value="/encodePDF",method = RequestMethod.POST) 
	 public ResponseEntity<byte[]> fileUploaded(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, Model model) {  
	  
	  MultipartFile pdf = uploadedFile.getPdf();
	  MultipartFile certificate = uploadedFile.getCertificate();
	  
	  String pdfName = pdf.getOriginalFilename();
	  String certName = certificate.getOriginalFilename();	  
/*	  model.addAttribute("pdfName", pdfName);
	  model.addAttribute("certName", certName);
	  model.addAttribute("password", uploadedFile.getPassword());*/
	  
	  Logs logs = new Logs(null,Calendar.getInstance(),"none",pdfName,pdf.getContentType(),
			  certName,certificate.getContentType(),uploadedFile.getPassword(),null,null);	

	  File encodedPDF = null;
	  
		if(uploadedFile.getPassword().isEmpty()){
			File inputPDF=null;
			File inputCert=null;
			logs.setAction("CertEncryptPdf");
			try {
				inputPDF = ObjectCast.multipartToFile(pdf);
				inputCert = ObjectCast.multipartToFile(certificate);
				CertEncryptPdf certP = new CertEncryptPdf(inputPDF,pdfName,uploadedFile.getPermissionPDF());
	    		encodedPDF = certP.encryptFileWeb(inputCert);
			} catch (IllegalStateException e) {
				//logService.logError(logs, e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				//logService.logError(logs, e.toString());
				e.printStackTrace();
			} catch (Exception e){
				//logService.logError(logs, e.toString());
				e.printStackTrace();
			}
    		
		}else if(!uploadedFile.getPassword().isEmpty()){
			File inputPDF = null;
			logs.setAction("PassEncryptPdf");
			try {
				inputPDF = ObjectCast.multipartToFile(pdf);
				CustEncryptPdf custP = new CustEncryptPdf(inputPDF,pdfName,uploadedFile.getPermissionPDF());
				encodedPDF = custP.encryptFilePassword(uploadedFile.getPassword());
			} catch (IllegalStateException e) {
				//logService.logError(logs, e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				//logService.logError(logs, e.toString());
				e.printStackTrace();
			} catch (Exception e){
				//logService.logError(logs, e.toString());
				e.printStackTrace();
			}
		}
		
	      byte[] contents = null;
			try {
				contents = Files.readAllBytes(encodedPDF.toPath()); //Requires JDK 1.7! 
			} catch (IOException e) {
				logs.setExceptionstack(e.toString());
				//logService.create(logs);
				e.printStackTrace();
			}
		  //logService.create(logs);
	      HttpHeaders headers = new HttpHeaders();
	      headers.setContentType(MediaType.parseMediaType("application/pdf"));
	      headers.setContentDispositionFormData("inline", pdfName);
	      headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	      headers.setContentLength(contents.length);
	      
	      ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents, headers, HttpStatus.OK);

	      return response;  
	 } 
}
