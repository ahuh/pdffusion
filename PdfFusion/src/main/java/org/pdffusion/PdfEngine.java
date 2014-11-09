package org.pdffusion;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfEngine {
	
	public static void concatPDFs(List<InputStream> streamOfPDFFiles,
            OutputStream outputStream) throws Exception {
 
        Document document = new Document();
        try {
            List<InputStream> pdfs = streamOfPDFFiles;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            Iterator<InputStream> iteratorPDFs = pdfs.iterator();
 
            // Create Readers for the pdfs.
            while (iteratorPDFs.hasNext()) {
                InputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
            }
            // Create a writer for the outputstream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
 
            document.open();
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
            // data
 
            PdfImportedPage page;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();
 
            // Loop through the PDF files and add to the output.
            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();
 
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {                    
                    pageOfCurrentReaderPDF++;
                    
                    // Get page size only
                    //Rectangle currentPageRect = pdfReader.getPageSize(pageOfCurrentReaderPDF);
                    
                    // Get page size and rotation                    
                    Rectangle currentPageRect = pdfReader.getPageSizeWithRotation(pageOfCurrentReaderPDF);
                                      

                    document.setPageSize(currentPageRect);                    
                    document.newPage(); 
                    
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    
                    // Add page with size only
                    //cb.addTemplate(page, 0, 0);
                    
                    // Add page with size and rotation (transformation matrix
                    switch (currentPageRect.getRotation()) {
                        case 0:
                            cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                            break;
                        case 90:
                            cb.addTemplate(page, 0, -1f, 1f, 0, 0, currentPageRect.getHeight());
                            break;
                        case 180:
                            cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
                            break;
                        case 270:
                            cb.addTemplate(page, 0, 1.0F, -1.0F, 0, currentPageRect.getWidth(), 0);
                            break;
                        default:
                            break;
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        }
        finally {
        	try {
	            if (document.isOpen()) {
	                document.close();
	            }
        	}
        	catch (Exception e) {
        		// Ignore
        	}
        	try {
	            if (outputStream != null) {
	                outputStream.close();
	            }
        	}
        	catch (Exception e) {
        		// Ignore
        	}
        }
    }	
}
