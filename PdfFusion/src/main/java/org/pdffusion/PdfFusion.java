package org.pdffusion;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PdfFusion extends GUIApplication {
	
    public static void main(String[] args) {
		try {			
			createAndShowGUI();
			
			String inputFolder = FileHelper.getCurrentPath() + File.separator + Const.INPUT_FOLDER;
			String outputFolder = FileHelper.getCurrentPath() + File.separator + Const.OUTPUT_FOLDER;
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String outputFileName = Const.OUTPUT_FILE_PREFIX + timeStamp + Const.OUTPUT_FILE_SUFFIX;
			String outputFilePath = outputFolder + File.separator + outputFileName;
			
			FileHelper.ensureFolder(inputFolder);
			FileHelper.ensureFolder(outputFolder);
			
			List<InputStream> pdfs = FileHelper.getPdfFilesFromFolder(inputFolder);
			
			if (pdfs.size() == 0) {
				displayWarningPopup("No PDF file to merge. Please put PDF files in the folder:\n"
						+ FileHelper.getWindowsPath(inputFolder));
			}
			else {
				OutputStream outputPdf = FileHelper.createPdfFile(
						outputFolder, 
						outputFileName);
				
				PdfEngine.concatPDFs(pdfs, outputPdf);
				
				displayMessagePopup("The PDF files have been merged successfully. The merge PDF file is available here:\n"
						+ FileHelper.getWindowsPath(outputFilePath));
			}
        }
		catch (Exception e) {
			String errorMessage = "An error occurred during the process. Details:\n\n";
			
			System.out.print(errorMessage);
			e.printStackTrace();
			
			if (frame != null) {				
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				displayErrorPopup(errorMessage + sw.toString());
			}		
        }
		finally {
			terminateGUI();
		}
    }
}
