package backup_ftp.backup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import backup_ftp.beans.ConfigBean;

public class BackupFtp {

	public void dowload(ConfigBean config) 
	{

		FTPClient ftpClient = new FTPClient();

		try 
		{

			ftpClient.connect(config.getHost(), config.getPort());
			ftpClient.login(config.getUser(), config.getPass());
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			SimpleDateFormat formater2 = new SimpleDateFormat("hh-mm-ss");

			
			String pathLocal = config.getLocal() + ((config.isCreateDate()) ? "/"+formater.format(new Date()): "");
			config.setLocal(pathLocal);

			File file = new File(config.getLocal());
			if(!file.exists() || !file.isDirectory())
			{
				file.mkdirs();
			}//if
			
			for (int i = 0; i < config.getData().size(); i++) 
			{
				String path = config.getData().get(i);
				String directoryName = ((path.lastIndexOf("/") == -1)?"":path.substring(path.lastIndexOf("/")));
				
				config.setLocal(pathLocal +directoryName);
				
				File f = new File(config.getLocal() +directoryName);
				if(f.exists())
				{
					directoryName += formater2.format(new Date());
					
					config.setLocal(config.getLocal() +directoryName);
				}//if
				
				copyDirectory(config, ftpClient, path , "");
			}//for
			

		} 
		catch (IOException ex) 
		{
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (ftpClient.isConnected()) 
				{
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}//dowload
	

	private void copyDirectory(ConfigBean config, FTPClient ftpClient, String path, String pathLocal) 
	{

		//Path localPathFile = Paths.get(config.getLocal() + "/" + pathLocal );
		//Path pathFile = Paths.get(path);

		try
		{
			System.out.println("pathFile : " + path);
			
			for (String tmp : config.getNoDownload()) 
			{
				if(path.startsWith(tmp))
				{
					System.out.println("file not download : " + path);
					return; 
				}
			}//for
			
		}catch (Exception ex)
		{
			System.err.println(ex.getMessage());
		}
		
		
		try 
		{

			FTPFile[] list = ftpClient.listFiles(path);
			
			System.out.println("list : " + list.length);

			for (FTPFile file : list) 
			{

				if (file.isDirectory()) 
				{

					System.out.println(file.getName());

					File f = new File(config.getLocal() + "/" + pathLocal + "/" + file.getName());
					if (!f.exists() && !f.isDirectory()) {
						f.mkdirs();
					}

					copyDirectory(config, ftpClient, ((path.length() == 0) ? "" : path + "/") + file.getName(), ((path.length() == 0) ? "" : pathLocal + "/") + file.getName());

				} 
				else 
				{

					System.out.println("copy file : " + config.getLocal() + "/" + pathLocal + "/" + file.getName());
					System.out.println("to file : " + ((path.length() == 0) ? "" : path + "/") + file.getName());

					try
					{
						// get output stream
						OutputStream output = new FileOutputStream(config.getLocal() + "/" + pathLocal + "/" + file.getName());
						// get the file from the remote system
						ftpClient.retrieveFile(((path.length() == 0) ? "" : path + "/") + file.getName(), output);
						// close output stream
						output.close();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					

				}// else

			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

}
