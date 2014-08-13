package cn.way.wandroid.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.util.Log;

public class FileUtil {
	
	public static void unZip(InputStream zipFileInputStream,
			String outputDirectory) throws IOException {
		
		// if outputDirectory is not exists create it
		File file = new File(outputDirectory);
		if (!file.exists()) {
			file.mkdirs();
			Log.d("test", "make dir :"+file.toString());
		}
		
		// used as a ZipInputStream
		ZipInputStream zipInputStream = new ZipInputStream(zipFileInputStream);
		// access to the ZIP file entry
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		// create a buffer for reading each entry
		byte[] buffer = new byte[1024 * 1024];
		
		// deal with each file entry
		while (zipEntry != null) {
			Log.d("test", "a zip entry :"+zipEntry.toString());
			// if file entry is a directory , create the directory
			if (zipEntry.isDirectory()) {
				file = new File(outputDirectory + File.separator
						+ zipEntry.getName());
				file.mkdir();
				Log.d("test", "make dir in root dir:"+file.toString());
			} else { // the entry is a file
				// create the file
				file = new File(outputDirectory + File.separator
						+ zipEntry.getName());
				// write the file
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				int length = 0;
				while ((length = zipInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, length);
				}
				fileOutputStream.close();
			}
			// move to next file entry in while cycle
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.close();
	}
	
}
