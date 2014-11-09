package org.pdffusion;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelper {
	
	public static String getWindowsPath(String path) {		
		String windowsPath = path.replace("/","\\");
		if (windowsPath.startsWith("\\")) {
			windowsPath = windowsPath.substring(1);
		}
		return windowsPath;
	}
	
	public static String getCurrentPath() {
		return System.getProperty("user.dir");
//		ClassLoader classLoader = FileHelper.class.getClassLoader();
//	    File classpathRoot = new File(classLoader.getResource("").getPath());
//	    return classpathRoot.getPath();
	}

	public static List<InputStream> getPdfFilesFromFolder(String folderPath) throws Exception {
		// Get files in folder
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		
		// Sort alphabetically
		Arrays.sort(listOfFiles);

		List<InputStream> pdfFilesList = new ArrayList<InputStream>();
		for (File file : listOfFiles) 
		{
			if (file.isFile()) 
			{
				// Item is a file (not a folder)
				String filePath = file.getAbsolutePath();
				
				if (filePath.toLowerCase().endsWith(Const.PDF_EXTENSION.toLowerCase())) {					
					pdfFilesList.add(new FileInputStream(filePath));
				}
			}
		}
		return pdfFilesList;
	}
	
	public static OutputStream createPdfFile(String folderPath, String fileName) throws FileNotFoundException {		
		String filePath = folderPath + File.separator + fileName;
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		file = null;
		
		return new FileOutputStream(filePath);
	}
	
	/**
	 * Ensure that the folder exists (create all folders in path if neeeded)
	 * @param folderPath
	 */
	public static void ensureFolder(String folderPath) {
		File folder = new File(folderPath);
		folder.mkdirs();
	}
}
