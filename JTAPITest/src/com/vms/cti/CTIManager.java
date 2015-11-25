package com.vms.cti;

import com.vms.cti.jtapi.JTAPI;
import com.vms.util.Config;

public class CTIManager {

	private static CTIManager ctiManager = null;
	
	private Boolean _isruning = false;
	
	private ICTI _cti = null;
	
	public synchronized static CTIManager getInstance() 
	{
		if(ctiManager == null)
		{
			ctiManager = new CTIManager();
		}
		return ctiManager;
	}
	
	//留给启动程序进行初始化连接和安全关闭连接
	public void startService() throws Exception
	{
		synchronized(this._isruning)
		{
			if(this._isruning == true) return;
			
			this.intCTIInstance(); 
			this._cti.connect();
			
			this._isruning = true;
		}
	}
	
	public void stopService()throws Exception
	{
		synchronized(this._isruning)
		{
			if(this._isruning == false) return;
		
			this._cti.disconnect();
			this._cti= null;
			
			this._isruning = false;
		}
	}
	
	public ICTI getCTI() throws Exception
	{
		if(_cti==null) throw new Exception("cti instance is null, please restart service");
		return _cti;
	}

	private void intCTIInstance() throws Exception
	{
		//根据配置文件初始化CTI类
		Config cf = Config.getInstance();
		//cf.loadConfig();
		if("jtapi".equalsIgnoreCase(cf.getCTIType()))
			_cti = new JTAPI();
		else 
			throw new Exception("unknown CTI type: " + cf.getCTIType());	
	}
}
