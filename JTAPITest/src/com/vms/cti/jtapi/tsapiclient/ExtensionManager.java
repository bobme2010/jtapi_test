package com.vms.cti.jtapi.tsapiclient;

import java.util.ArrayList;

import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.JtapiPeerUnavailableException;
import javax.telephony.Provider;

import com.avaya.jtapi.tsapi.adapters.ProviderListenerAdapter;
import com.vms.util.Config;
import com.vms.util.Logs;


public class ExtensionManager extends ProviderListenerAdapter {
	
	private JtapiPeer _jtapiPeer = null;
	private Provider _provider = null;
	private int _currentExtensionIndex = 0; //当前的选中的分机索引
	private Extension[] _extensions = null; //存储所有可用的分机对象
	
	private String _cnnStr ="";// "AVAYA#CM#CSTA#POC-AES;login=ctiadaptor;passwd=Dadmin@01;servers=135.27.134.113:450";
	private String _extensionListStr = "";// "7001-7003,8009,1023";
	private String _callerSelectingPolicy = "0";
	
	//启动服务
	public synchronized void startService() throws Exception
	{
		initJtapi();
		createProvider() ;	
		createExtensionList();
		//All resources have been created yet! We can kick off now!
	
	}
	

	//停止服务
	public synchronized void stopService()
	{	
		if(_extensions!=null) releaseAllExtensionConnections();
		
		if(_provider!=null)  _provider.shutdown();
		
		_extensions = null;
		_provider = null;	
		_jtapiPeer = null;
		
	}
	
	//这个
	public synchronized void safeReStartService()
	{
		try {
			if(_extensions!=null) releaseAllExtensionConnections();			
		}catch(Exception e){	
			Logs.debug("safeReStartService->releaseAllExtensionConnection(), " + e.getMessage());
		}
		try {
			if(_provider!=null)  _provider.shutdown();		
		}catch(Exception e){	
			Logs.debug("safeReStartService->_provider.shutdown(), " + e.getMessage());
		}
		_extensions = null;
		_provider = null;	
		_jtapiPeer = null;
		
		try {
			startService();	
		}catch(Exception e){	
			Logs.debug("safeReStartService->startService(), " + e.getMessage());
		}
		
	}
	
	//从Extension List 中循环找一个可用的extension，然后发起外呼，成功后返回extesion的号码
	public synchronized void makecall(String destVDN,String roomid,String ani, String uui) throws Exception 
	{
		Extension ex = null;
		if("1".equalsIgnoreCase(_callerSelectingPolicy))
		{

			ex=getExtensionByNumber(ani);
			if(ex == null) throw new Exception("extension["+ani+"] is not defined in CTI_DOMAIN group section.");
		}
		else
		{
			ex = getNextAvaliableExtsion();
			if(ex == null) throw new Exception("no idle extension to make a route call.");
		}
		ex.makeCall(destVDN,roomid, uui);
		
	}
	//通过roomid找到对应的extnumber，然后释放相应的呼叫
	public void releaseCallByRoomID(String roomid) {
		if(_extensions == null) return;
		for (int i =0 ; i < _extensions.length; i++)
			_extensions[i].relaseCallByRoomID(roomid); // 放弃roomid符合条件的分机的已连接的呼叫。
	}


	//******************************************************/
	
	private void initJtapi() throws Exception 
    {
        try {
        	if(_jtapiPeer != null ) return ;
        	
		    //get default implementation of the JtapiPeer object by sending null,
		    //optionally you may send com.avaya.jtapi.tsapi.TsapiPeer
		    _jtapiPeer = JtapiPeerFactory.getJtapiPeer( null );
		    Logs.info("jtapiPeer object created successfully by default class name.");
        } catch (JtapiPeerUnavailableException e) {
	        try{
			_jtapiPeer = JtapiPeerFactory.getJtapiPeer( "com.avaya.jtapi.tsapi.TsapiPeer" );
			Logs.info("jtapiPeer created successfully using name 'com.avaya.jtapi.tsapi.TsapiPeer'.");
		} catch (JtapiPeerUnavailableException e2) {
			Logs.info("jtapiPeerFactory.getJtapiPeer: caught JtapiPeerUnavailableException");
			Logs.info("Error: JtapiPeer could not be created.  Verify your Jtapi client install.");
			throw new Exception(e2.getMessage());
		}
        }	    

    }	
	
	private  void createProvider() throws Exception 
	{
		try {
	
			if(_provider !=null ) return ; //已经创建了，就不要创建了！！！
			
			Logs.info("try to get tsapi provider... ");
			
			getConnectionStringFromConfig();
			Logs.debug("connection string : " + _cnnStr);	
			
			_provider = _jtapiPeer.getProvider(_cnnStr);
			Logs.info("tsapi provider has been create success!");	

			Logs.debug("waiting tsapi provider status turn to IN_SERVICE...");
			while(_provider.getState()!=Provider.IN_SERVICE)
			{
				System.out.print(".");
				try {Thread.sleep(200);} catch (InterruptedException e){e.printStackTrace();}
			}
			Logs.info("tsapi provider is now in service status.");			

		} catch (Exception e) {
			Logs.info("create tsapi provider exception: "+ e);
			throw e;
		}

	}

	private void createExtensionList() throws Exception {
		
		try
		{
			//从配置文件里读出所有的分机号码
			String[] extNumbers  = getExtensionNumbersFromConfig();
			
			if(extNumbers == null) throw new Exception("no extension number found in config file");
			
			int count = extNumbers.length;
			_extensions = new Extension[count]; //创建实例数组
			
			for(int i =0; i< count ;i++)
				_extensions[i] = new Extension(_provider,extNumbers[i]);
		
			_currentExtensionIndex = 0; //Rest Ext index pointer.

		}
		catch(Exception e)
		{
			Logs.info("create extension list error: " + e.getMessage());
			throw e;
		}
	}

	private String[] getExtensionNumbersFromConfig() {
		
		Logs.info("tsapi originating extension list: " + _extensionListStr);
		//first, we split it by ',' mark
		String[] extGroups = _extensionListStr.split(",");
		ArrayList<String> extensions = new ArrayList<String>();
		
		for(int i=0; i< extGroups.length; i++)
		{
			//单个的号码，直接加入。 连续号码，展开后加入
			if(extGroups[i].contains("-"))
				extractSegementNumber(extensions,extGroups[i]);
			else
				extensions.add(extGroups[i]);
			
		}
		
		int count  = extensions.size();
		String [ ]  ret = new String[count];

		for(int j =0 ; j < count ; j++)
			ret[j] = extensions.get(j);

		return ret ;
	}

	//讲一个连续号码段展开成每一个号码
	private void extractSegementNumber(ArrayList<String> extensions,String extNumbers)
	{
		String[] temp = extNumbers.split("-"); 
		
		int extBegin =  Integer.parseInt(temp[0]);//开始的号码值
		int extEnd   =  Integer.parseInt(temp[1]);//结束的号码值
		
		for(int i =extBegin ; i <= extEnd; i++)
			extensions.add(i+"");		
	}


	public static void main(String[] args) throws Exception 
	{
		ExtensionManager  x = new ExtensionManager();
		x.getConnectionStringFromConfig();
		x.getExtensionNumbersFromConfig();
	}

	public void getConnectionStringFromConfig() {
		Config cf = Config.getInstance();
		
		String ip   = cf.getCTIIP();
		String port = cf.getCTIPort();
		String user = cf.getCTILoginID();
		String pwd  = cf.getCTIPWD();
		String domain = cf.getCTIDomain();
		//"AVAYA#CM#CSTA#POC-AES;login=ctiadaptor;passwd=Dadmin@01;servers=135.27.134.113:450";
		//AVAYA#CM#CSTA#POC-AES|7001-7060|1
		String[] temp = domain.split("\\|"); 
		_cnnStr = temp[0] + ";login=" + user + ";passwd=" + pwd + ";servers=" + ip + ":" + port;
		
		_extensionListStr = temp[1];
		
		if(temp.length>2) //说明有第三个选线策略定义
			_callerSelectingPolicy = temp[2].trim();		
	}
	
	private Extension getNextAvaliableExtsion() {
		Extension ext = null;
		int tryCount =0; //寻找空闲Ext的次数
		
		while(tryCount < _extensions.length)
		{
			tryCount ++; //尝试次数递增
			if(_currentExtensionIndex >= _extensions.length) //指针末尾翻转检测
				_currentExtensionIndex =0;
			ext = _extensions[_currentExtensionIndex++]; //指针用完了必须后移
			if(ext.isIdle()) //分机合适，就返回这个分机,否则就循环检测
				return ext;
		}
		
		return null;
	}
	
	private Extension getExtensionByNumber(String ani) throws TServerNotInServiceException
	{
		Extension ext = null;
		
		if(_extensions==null) throw new TServerNotInServiceException(ani);
		
		for(int i =0;i < _extensions.length;i++)
		{
			ext = _extensions[i];
			if(ext.getExtNumber().equals(ani)) //判断分机号是否相符，暂时不考虑是否空闲
				return ext;
		}
		
		return null;
	}
	//用在关闭服务时，清除所有的呼叫连接
	private void releaseAllExtensionConnections()
	{
		if(_extensions == null) return;
		
		for (int i =0 ; i < _extensions.length; i++)
			_extensions[i].releaseCall(); // 放弃所有的已连接的呼叫。
	}


}
