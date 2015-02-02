package backup_ftp;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import backup_ftp.backup.BackupFtp;
import backup_ftp.beans.ConfigBean;
import backup_ftp.fonctions.Utils;

public class Execute {

	public static String FILE_CONFIG_NAME = "config.json";
	
	
	public static void main(String[] args) {

		String contentFileConfig = Utils.readFile(FILE_CONFIG_NAME);
		
		if(contentFileConfig == null)
		{
			System.err.println("Fichier non trouver : " + FILE_CONFIG_NAME);
			return;
		}//if
		
		
		
		Object obj = JSONValue.parse(contentFileConfig);
		
		if(obj == null)
		{
			System.err.println("Le fichier de configuration est invalide.");
			return;
		}//if
		
		
		
		BackupFtp backup = new BackupFtp();
		backup.dowload(new ConfigBean((JSONObject) obj));
		
		
		
	
	}

}
