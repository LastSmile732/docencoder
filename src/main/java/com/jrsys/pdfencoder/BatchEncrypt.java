package com.jrsys.pdfencoder;

import java.io.*;

import au.com.bytecode.opencsv.*;

public class BatchEncrypt {

    private final static int INPUT_FOLDER = 0;
    private final static int OUTPUT_FOLDER = 1;
    private final static int STRENGTH = 2;
    private String permission = "11111111";
    private String strength = "128";
    private String customInfoName = "Jrsys";
    private String customInfoValue = "Jrsys International Corp.";

    private static void usage() {
        System.out.println("usage: BathEncrypt input_folder output_folder");
        System.out.println("Please see index.csv in the input folder for encryption settings.");
        System.out.println();
    }
    
    /**
     * Encode PDF file with a password or a digital certificate, using CustEncryptPdf.java and CertEncryptPdf.java
     * @param CSVReader reader, String inputFolder, String outputFolder
     * @return Integer fileCounter
     */
	private int encodePDF(CSVReader reader,String inputFolder,String outputFolder){
		int fileCounter = 0;
		String[] fileList;
		try {
			while((fileList = reader.readNext()) != null){

				if (!fileList[1].isEmpty()){
					CustEncryptPdf encPdf = new CustEncryptPdf();
					encPdf.run(new String[]{inputFolder+"/"+fileList[0], outputFolder+"/"+fileList[0], fileList[1], fileList[1], permission, strength, customInfoName, customInfoValue});
					fileCounter++;
				}
				else if (fileList[1].isEmpty() && !fileList[2].isEmpty()){
					CertEncryptPdf encPdf = new CertEncryptPdf();
					encPdf.encryptFile(new String[]{inputFolder+"/"+fileList[0], outputFolder+"/"+fileList[0], fileList[2], permission});
					fileCounter++;
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileCounter;
	}
    
    /**
     * Reads index.csv for the PDF file list, and executes encodePDF() to encode the files in the file list.
     * @param String args[]
     */
	private void batchRun(String args[]){
		//String filePath = new File("").getAbsolutePath();
		String configCSV = args[INPUT_FOLDER]+"/"+"index.csv"; //edit the file name and the input location if needed
		String inputFolder = args[INPUT_FOLDER];
		String outputFolder = args[OUTPUT_FOLDER];
		BufferedReader br = null;
		CSVReader reader = null;
		char cvsSplitBy = ',';
		char quotechar = '"';
		long startTime = System.currentTimeMillis();
		int fileEncoded = 0;
		try {
			br = new BufferedReader(new FileReader(configCSV));
			reader = new CSVReader(br,cvsSplitBy,quotechar);
			fileEncoded = this.encodePDF(reader, inputFolder, outputFolder);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			 System.out.println("The CSV file cannot be found!");
		} finally{			
			try {
				if(br != null){
					br.close();
				}if(reader != null){
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Unable to close the Reader!");
			} 
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\n"+"Total Operating Time: " + totalTime + "ms");
		System.out.println("Total Encoded Files: " + fileEncoded);
	}
		
	
	public static void main(String[] args) {
		
		if (args.length < STRENGTH) {
        	System.out.println("args length: " + args.length + " args strength: "+ STRENGTH);
        	usage();
            return;
        }
		System.out.println("input: " + args[0] + "; output: "+ args[1]);
		BatchEncrypt batchEnc = new BatchEncrypt();
		batchEnc.batchRun(args);
	}

}
