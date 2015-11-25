package com.vms.cti.jtapi.tsapiclient;

import javax.telephony.Address;
import javax.telephony.Call;
import javax.telephony.Connection;
import javax.telephony.Provider;
import javax.telephony.Terminal;

import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentCall;
import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.UserToUserInfo;
import com.vms.util.Logs;


public class Extension  {
		
	private String _extnumber ="";
	private Provider _provider = null;
	private Address _address = null;
	private Terminal _terminal = null;
	private String _roomid = "";
	
	public Extension(Provider provider,String extnumber) throws Exception
	{
		_extnumber = extnumber.trim();
		if(_extnumber.length() == 0) 
			throw new Exception("extnumber can not be null");
		if(provider == null) 
			throw new Exception("provider is null, please create TSAPI provider firstly.");
		
		_provider = provider;
		_address = provider.getAddress(_extnumber);
		_terminal = provider.getTerminal(_extnumber);

	}
	
	public boolean isIdle()
	{
		Connection[] conns = _address.getConnections();
		if(conns == null)
			return true;
		else
			return false;
	}
	
	public boolean makeCall(String destNumber,String roomid,String uui) throws Exception
	{
		UserToUserInfo          avayaUUI = null;

		String callee = destNumber.trim();
		String str = uui.trim();
		if(str.length()>0) 
			avayaUUI =  new UserToUserInfo(uui);
		else
			avayaUUI = new UserToUserInfo(roomid);
		
		try {
			Call call = _provider.createCall();
			Logs.info("originate call from [" + _extnumber +"] to [" + destNumber + "]");
			
			//call.connect(_terminal, _address, callee); //no uui supported
	   	    // makecall by using the connect method in the LucentCall interface
            Connection[] conns =( (LucentCall) call).connect( 
                                                        (LucentTerminal) _terminal, 
                                                        (LucentAddress) _address, 
                                                        callee, 
                                                        false, 
                                                        avayaUUI);
           if(conns == null) //说明呼叫出错了，木有成功
        	   throw new Exception("can not make call, please check log file for details.");
            _roomid = roomid; //标记这个extension和某个room对应上了。可能存在一个情况，即roomid被后一个call覆盖了，但是，这个没有问题，不用担心
			return true;
		} catch (Exception e) {
			//这里针对client not in service 问题进行处理一下，等于要重新连接AES服务器
			//client not in service
			String errmsg = e.getMessage();
			if(errmsg != null && errmsg.contains("client not in service"))
			{
				throw new TServerNotInServiceException(callee); //重新连接，然后重新发送
			}
			else
			{	
				Logs.debug("make call to [" + callee + "] error:" + e.getMessage());
				_roomid = "";
				throw new Exception("make call to [" + callee + "] error, " + e.getMessage());
			}
		}		
		
	}

	//判断当前的roomid是否和要求的对应，是得话，才清除呼叫，否则不清除呼叫。用在ICTI的DropContact接口里面。
	public void relaseCallByRoomID(String roomid)
	{
		if(roomid.equals(_roomid))
			releaseCall();
	}
	public void releaseCall() {
		Connection[] conns = _address.getConnections();
		
		if(conns == null) return;
		
		for (int i =0 ; i< conns.length; i++)
		{
			try {
				Logs.debug("ext["+ _extnumber + "] release connection");
				conns[i].disconnect();
			} catch (Exception e) {
				Logs.debug("ext["+ _extnumber + "] release connection error: " + e.getMessage());
			}
		}
	}
	
	public String getExtNumber()
	{
		return this._extnumber;
	}

}
