package backup_ftp.fonctions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

	public static String readFile(String fileName) 
	{
		File file = new File(fileName );
		
		if(!file.exists())
		{
			return null;
		}//if
		
		
		BufferedReader br = null;
		StringBuilder res = new StringBuilder();
		try 
		{

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				res.append(sCurrentLine);
			}

		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)
					br.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}//finally

		return res.toString();
	}
	
	
	
	
	
}
