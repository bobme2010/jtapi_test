package com.vms.cti.jtapi;

import com.vms.cti.ICTI;
import com.vms.cti.jtapi.tsapiclient.ExtensionManager;
import com.vms.cti.jtapi.tsapiclient.TServerNotInServiceException;
import com.vms.util.Config;
import com.vms.util.Logs;

public class JTAPI implements ICTI {

	private  ExtensionManager _tsapiClient = null;
	private int MAX_RETRY_COUNT = 5;
	
	public JTAPI()
	{
		_tsapiClient = new ExtensionManager();
	}
	

	@Override
	public void insertContact(String roomid, String ani, String appdata) throws Exception 
	{
		this.insertContact(Config.getInstance().getCTIDefaultQueue(),roomid,ani,appdata);
		
	}

	@Override
	public void insertContact(String targetQueue, String roomid, String ani,String appdata) throws Exception {
		try
		{
			for(int i =0 ; i< MAX_RETRY_COUNT; i++)
			{
				boolean ret = safeMakeCall(targetQueue,roomid,ani,appdata);
				if(ret == true) return;
				Logs.debug("JTAPI->insertContact() fail count: " + i);
			}
			
			//这个地方是已经错误了好几次了，试着重连AES都不成功了，必须记录日志后，自己结束。
			throw new Exception("AES server can not be connected over " + MAX_RETRY_COUNT + " times");
		}catch(Exception e)
		{
			//这里判断一下失败类型，如果是网络断开的错误，那么重新连一下就差不多了吧
			Logs.info("insert a Contact into JTAPI failed, " + e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}

	//重复判断
	private boolean safeMakeCall(String targetQueue, String roomid, String ani,String appdata) throws Exception
	{
		try
		{
			Logs.info("insert a contact to JTAPI : targetQueue[" + targetQueue + "] RoomID[" + roomid + "] ANI[" + ani + "] Appdata[" + appdata + "]" );
			synchronized(this._tsapiClient)
			{
				//'UUI' 是roomid=xxx&ani=xxx&appdata=xxx"的组合，为了便于客户端回调vms servlet用
				String uui =  "roomid=" +roomid + "&ani=" + ani + "&appdata=" + appdata;
			    this._tsapiClient.makecall(targetQueue, roomid, ani,uui);
			}
			Logs.info("insert a Contact into JTAPI success");	
			return true;
		}catch(TServerNotInServiceException tex)
		{
			//这种情况单独处理，不能简单粗暴的对待。 重新连接一下应该可以吧。
			this._tsapiClient.safeReStartService();
			return false;
		}	
		
	}
	
	@Override
	public void dropContact(String roomid) throws Exception {
		try
		{
			Logs.info("drop Contact from JTAPI :RoomID["+roomid +"]" );
			synchronized(this._tsapiClient)
			{
			     this._tsapiClient.releaseCallByRoomID(roomid);
			}
			Logs.info("drop Contact[" + roomid + "] from JTAPI success");			
		}catch(Exception e)
		{
			Logs.info("drop Contact["+ roomid+"] from JTAPI failed, " + e.toString());
			throw new Exception(e.getMessage());
		}		
	}

	@Override
	public String getContact() throws Exception {
		Logs.debug("Can not get queue message from VMS,please using TSAPI client");		
		return "";
	}

	@Override
	public boolean connect() throws Exception {
		
		boolean ret = false;
		try
		{
			synchronized(this._tsapiClient)
			{
				this._tsapiClient.startService();
			}
			
			Logs.info("Connect to JTAPI success!");
			ret = true;
				
		}catch(Exception e)
		{
			Logs.info("Connect to JTAPI failed," + e.toString());
			throw new Exception("Connect to JTAPI failed," + e.getMessage());
		}
		return ret;
	}

	@Override
	public boolean disconnect() throws Exception
	{
		boolean ret = false;
		try
		{
			synchronized(this._tsapiClient)
			{
				this._tsapiClient.stopService();
			}
			
			Logs.info("Disconnect from JTAPI success!");
			ret = true;
		}catch(Exception e)
		{
			Logs.info("Disconnect from JTAPI failed," + e.getMessage());
			throw new Exception("Disconnect from JTAPI failed," + e.getMessage());
		}
		return ret;
	}


}
