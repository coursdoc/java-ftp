package backup_ftp.beans;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConfigBean {

	String host;
	int port;
	String user;
	String pass;
	String local;
	boolean createDate;
	
	List<String> data;
	List<String> noDownload;
	
	public ConfigBean(JSONObject json)
	{
		host = json.get("host").toString();
		port = Integer.parseInt(json.get("port").toString());
		user = json.get("user").toString();
		pass = json.get("pass").toString();
		local = json.get("local").toString();
		createDate = (json.get("createDate").toString().equals("true"))?true:false;
		
		JSONArray dataArray = (JSONArray) json.get("data");
		data = new ArrayList<String>();
		for (int i = 0 ; i < dataArray.size() ; i++) {
			data.add(dataArray.get(i).toString());
		}
		
		
		dataArray = (JSONArray) json.get("noDownload");
		noDownload = new ArrayList<String>();
		for (int i = 0 ; i < dataArray.size() ; i++) {
			noDownload.add(dataArray.get(i).toString());
		}
		
		
	}
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public boolean isCreateDate() {
		return createDate;
	}
	public void setCreateDate(boolean createDate) {
		this.createDate = createDate;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public List<String> getNoDownload() {
		return noDownload;
	}
	public void setNoDownload(List<String> noDownload) {
		this.noDownload = noDownload;
	}
	
	
	
	
}
