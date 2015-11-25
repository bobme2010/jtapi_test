package com.vms.cti;

public interface ICTI {
	
	public void insertContact(String roomid,String ani, String appdata) throws Exception;
	
	public void insertContact(String targetQueue,String roomid,String ani, String appdata) throws Exception;
	
	public void dropContact(String roomid) throws Exception;
	
	public String getContact() throws Exception;
	
	public boolean connect() throws Exception;
	
	public boolean disconnect()throws Exception;
}
