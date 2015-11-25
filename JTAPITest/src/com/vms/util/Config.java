package com.vms.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vms.util.Logs;
import com.framework.util.CreateProperties;
import com.framework.util.FileIo;
import com.framework.util.StringEx;

@SuppressWarnings("unchecked")
public class Config {

	private static String CONFIG_FILE_NAME = "VMSConfig.ini";
	private static String CONFIG_USER_ACCOUNT_FILE_NAME = "WebUserAccount.txt";
	
	private static String CONFIG_FILE_PATH = "../conf/";
	
	private static String VMS_SERVER_LIST = "VMS_SERVER_LIST";
	private static String VMS_SERVER_PORT = "VMS_SERVER_PORT";
	private static String VMS_SERVER_HOST = "VMS_SERVER_HOST";

	private static String VMS_NOTIFY_MESSAGE_DISPATCHER_PORT = "VMS_NOTIFY_MESSAGE_DISPATCHER_PORT";
	private static String VMS_NOTIFY_MESSAGE_DISPATCHER_IP = "VMS_NOTIFY_MESSAGE_DISPATCHER_IP";
	
	
	private static String VMS_SERVER_SN = "VMS_SERVER_SN";
	private static String VMS_WEB_ROOT = "VMS_WEB_ROOT";
	private static String VMS_CDR_ROOT = "VMS_CDR_ROOT";
	private static String VMS_ISDEBUG = "VMS_IS_DEBUG";
	private static String VMS_ISVERBOSEMODEL = "VMS_IS_VERBOSE_MODEL";
	private static String VMS_SERVICE_IS_AUTOSTART = "VMS_SERVICE_IS_AUTOSTART";
	private static String VMS_KEEPALIVE_TIME_INTERVAL = "VMS_KEEPALIVE_TIME_INTERVAL";
	private static String VMS_MAX_KEEPALIVE_FAIL_COUNT = "VMS_MAX_KEEPALIVE_FAIL_COUNT";	
	private static String VMS_CONFERENCE_AUTO_RECORDING = "VMS_CONFERENCE_AUTO_RECORDING";
	private static String VMS_RECORDING_FROM_START_TO_END = "VMS_RECORDING_FROM_START_TO_END";
	
	private static String VMS_LOG_KEEPLIVE_RESULT = "VMS_LOG_KEEPLIVE_RESULT";
	private static String VMS_HISTORY_LOG_ROOT = "VMS_HISTORY_LOG_ROOT";
	
	private static String VMS_IVIEW_REQUEST_WAIT_TIMEOUT = "VMS_IVIEW_REQUEST_WAIT_TIMEOUT";
	
	
	private static String MCU_IP = "MCU_IP";	
	
	private static String MCU_IVIEW_IP = "MCU_IVIEW_IP";	
	
	private static String MCU_PORT = "MCU_PORT";	
	private static String MCU_API_VERSION = "MCU_API_VERSION";
	private static String MCU_USER = "MCU_USER";
	private static String MCU_PWD = "MCU_PWD";
	
	private static String MCU_SERVICE_TEMPLATE_LOGINID = "MCU_SERVICE_TEMPLATE_LOGINID";
	private static String MCU_SERVICE_TEMPLATE_PREFIX = "MCU_SERVICE_TEMPLATE_PREFIX";
	
	private static String MCU_SDS_URL = "MCU_SDS_URL";
	
	private static String MCU_VIRTUAL_MEETING_ID_PREFIX = "MCU_VIRTUAL_MEETING_ID_PREFIX";
	private static String VMS_MEETING_ROOM_BASE = "VMS_MEETING_ROOM_BASE";	
	
	private static String VMS_RESTORE_ONGOINGC_ROOM = "VMS_RESTORE_ONGOINGC_ROOM";
	private static String VMS_RESTORE_ONGOINGC_ROOM_TIMEOUT = "VMS_RESTORE_ONGOINGC_ROOM_TIMEOUT";
	private static String VMS_RESTORE_ONGOINGC_ROOM_MAXCOUNT = "VMS_RESTORE_ONGOINGC_ROOM_MAXCOUNT";
	
	private static String VMS_TERMINATE_CALL_WHEN_SHUTDOWN = "VMS_TERMINATE_CALL_WHEN_SHUTDOWN";
	private static String VMS_TERMINATE_CALL_WHEN_START = "VMS_TERMINATE_CALL_WHEN_START";
	
	private static String VMS_TERMINATE_MININUM_PARTY = "VMS_TERMINATE_MININUM_PARTY";
	private static String VMS_TERMINATE_WITH_SIPGW = "VMS_TERMINATE_WITH_SIPGW";
	
	private static String VMS_ROOMID_FIXED = "VMS_ROOMID_FIXED";
	private static String VMS_ROOMID_LENGTH = "VMS_ROOMID_LENGTH";
	
	private static String VMS_SERVLET_ACCESS_CODE = "VMS_SERVLET_ACCESS_CODE";
	
	private static String JMS_IS_ACTIVE = "JMS_IS_ACTIVE";
	private static String JMS_BROKER = "JMS_BROKER";
	private static String JMS_TOPIC = "JMS_TOPIC";	
	private static String JMS_MSG_TTL = "JMS_MSG_TTL"; 
	
	private static String CTI_DEFAULT_QUEUE = "CTI_DEFAULT_QUEUE";

	private static Config config;
	
	private static String CTI_TYPE = "CTI_TYPE";
	
	private static final String CTI_IP = "CTI_IP";

	private static final String CTI_PORT = "CTI_PORT";

	private static final String CTI_LOGINID = "CTI_LOGINID";

	private static final String CTI_PWD = "CTI_PWD";

	private static final String CTI_DOMAIN = "CTI_DOMAIN";
	
	private CreateProperties cp;
	
	private String _mcu_ip;
	
	private String _mcu_port=  "3336";
	
	private String _mcu_api_version = "8.0";
	
	private String _mcu_user = "admin";
	
	private String _mcu_pwd = "admin";
	
	private String _vms_keep_alive_time_interval = "20";
	
	private String _vms_max_keep_alive_fail_count = "2";
	
	private String _mcu_service_template_loginid = "admin";
	
	private String _mcu_service_template_prefix = "71";
		
	private String _mcu_virtal_meeting_id_prefix = "6";
	
	private String _mcu_sds_url = "";
	
	private String _vms_meeting_room_base = "";
	
	private String _vms_terminate_shutdown = "true";
	
	private String _vms_terminate_start = "false";
	
	private String _vms_terminate_mininum_party = "2";
	
	private String _vms_terminate_with_sip_gw = "false";
	
	private String _cti_type = "aacc";

	private String _cti_ip;

	private String _cti_loginid;

	private String _cti_port;

	private String _cti_pwd;

	private String _cti_domain;
	
	private String _cti_deafult_queue;
	
	private String _vms_server_list="127.0.0.1";
	
	private String _vms_roomid_length = "20";
	
	private String _vms_server_port;
	
	private String _vms_web_root;
	
	private String _vms_cdr_root = "../cdr";
	
	private String _vms_roomid_fixed;
	
	private String _vms_isdebug;
	
	private String _vms_isverbose;
	
	private String _vms_is_auto_start_service;
	
	private String _vms_is_auto_start_recording;
	
	private String _vms_is_restore_ongoing_conferenece = "false";
	private String _vms_restore_ongoing_conferenece_timeout = "60";
	private String _vms_restore_ongoing_conferenece_maxcount = "-1";
	
	private String _sn=""; //序列号
	
	private String _vms_log_root = "../logs";
	
	private String _vms_log_histroy_root = "../logs/histroy"; //历史的日志会被转存压缩到这个目录的。
	
	private String _vms_is_log_keeplive_result="true";
	
	
	private String _vms_server_host = "0.0.0.0";
	
	private String _vms_servlet_access_code = ""; //默认不需要访问密码
	private String _vms_iview_request_wait_timeout = "5";// 默认api请求超时5秒
	
	private String _jms_is_active = "false";
	private String _jms_broker  = "tcp://127.0.0.1:61616";
	private String _jms_topic = "vmsevent";
	private String _jms_msg_ttl = "600000"; //默认10分钟中消息有效
	
	private String _vms_recording_from_start_to_end = "true"; //默认是全程录像的
	private int _vms_notify_message_dispatcher_port = 63000; //默认的upd发送包的端口
	private String _vms_notify_message_dispatcher_ip = "127.0.0.1"; //默认的upd发送包的端口
	
	public static Config getInstance()
	{
		if(config == null) 
		{
			config = new Config();
			config.loadConfig();
		}
		return config;
	}

	public void loadConfig()
	{
		cp = getProperties(CONFIG_FILE_PATH,CONFIG_FILE_NAME);
		
		
		config._mcu_ip = cp.getValue(MCU_IVIEW_IP);
		if(config._mcu_ip== null)
			config._mcu_ip = cp.getValue(MCU_IP);
		
		config._mcu_ip = config._mcu_ip.trim();
			
		config._mcu_port = cp.getValue(MCU_PORT).trim();
		config._mcu_api_version = cp.getValue(MCU_API_VERSION).trim();
		
		if(cp.getValue(MCU_USER)!=null)
			config._mcu_user = cp.getValue(MCU_USER).trim();
		
		if(cp.getValue(MCU_PWD)!=null)
			config._mcu_pwd = cp.getValue(MCU_PWD).trim();
		
		config._mcu_service_template_loginid = cp.getValue(MCU_SERVICE_TEMPLATE_LOGINID).trim();
		config._mcu_service_template_prefix = cp.getValue(MCU_SERVICE_TEMPLATE_PREFIX).trim();
		config._mcu_sds_url = cp.getValue(MCU_SDS_URL).trim();
		
		config._mcu_virtal_meeting_id_prefix = cp.getValue(MCU_VIRTUAL_MEETING_ID_PREFIX).trim();
		config._vms_meeting_room_base = cp.getValue(VMS_MEETING_ROOM_BASE).trim();
		config._vms_terminate_shutdown = cp.getValue(VMS_TERMINATE_CALL_WHEN_SHUTDOWN).trim();
		config._vms_terminate_start = cp.getValue(VMS_TERMINATE_CALL_WHEN_START).trim();
		config._vms_terminate_mininum_party = cp.getValue(VMS_TERMINATE_MININUM_PARTY ).trim();
	
		if(cp.getValue(VMS_TERMINATE_WITH_SIPGW)!=null)
			config._vms_terminate_with_sip_gw =  cp.getValue(VMS_TERMINATE_WITH_SIPGW ).trim(); 
 	
		config._vms_is_auto_start_recording = cp.getValue(VMS_CONFERENCE_AUTO_RECORDING ).trim();
		
		if(cp.getValue(VMS_LOG_KEEPLIVE_RESULT)!=null)
			config._vms_is_log_keeplive_result =  cp.getValue(VMS_LOG_KEEPLIVE_RESULT ).trim(); 
 		
		config._cti_type = cp.getValue(CTI_TYPE).trim();
		config._cti_ip = cp.getValue(CTI_IP).trim();
		config._cti_port = cp.getValue(CTI_PORT).trim();
		config._cti_loginid = cp.getValue(CTI_LOGINID).trim();
		config._cti_pwd = cp.getValue(CTI_PWD).trim();
		config._cti_domain = cp.getValue(CTI_DOMAIN).trim();	
		config._cti_deafult_queue = cp.getValue(CTI_DEFAULT_QUEUE).trim();
		
		if(cp.getValue(VMS_SERVER_LIST)!=null)
			config._vms_server_list = cp.getValue(VMS_SERVER_LIST).trim();

		if(cp.getValue(VMS_ROOMID_LENGTH)!=null)
			config._vms_roomid_length = cp.getValue(VMS_ROOMID_LENGTH).trim();
		
		
		config._vms_server_port = cp.getValue(VMS_SERVER_PORT).trim();
		
		if(cp.getValue(VMS_SERVER_HOST)!=null)
			config._vms_server_host  = cp.getValue(VMS_SERVER_HOST).trim();
		
		config._vms_web_root = cp.getValue(VMS_WEB_ROOT).trim();
		
		config._vms_isdebug = cp.getValue(VMS_ISDEBUG).trim();
		config._vms_isverbose = cp.getValue(VMS_ISVERBOSEMODEL).trim();		
		config._vms_is_auto_start_service = cp.getValue(VMS_SERVICE_IS_AUTOSTART).trim();
		config._vms_keep_alive_time_interval = cp.getValue(VMS_KEEPALIVE_TIME_INTERVAL).trim();
		config._vms_max_keep_alive_fail_count = cp.getValue(VMS_MAX_KEEPALIVE_FAIL_COUNT).trim();
		config._vms_roomid_fixed = cp.getValue(VMS_ROOMID_FIXED).trim();
		
	
		
		if(cp.getValue(VMS_SERVLET_ACCESS_CODE)!=null)
			config._vms_servlet_access_code  = cp.getValue(VMS_SERVLET_ACCESS_CODE).trim();
		
		if(cp.getValue(JMS_IS_ACTIVE)!=null)
			config._jms_is_active  = cp.getValue(JMS_IS_ACTIVE).trim();
		
		if(cp.getValue(JMS_BROKER)!=null)
			config._jms_broker  = cp.getValue(JMS_BROKER).trim();
		
		if(cp.getValue(JMS_TOPIC)!=null)
			config._jms_topic  = cp.getValue(JMS_TOPIC).trim();

		if(cp.getValue(JMS_MSG_TTL)!=null)
			config._jms_msg_ttl  = cp.getValue(JMS_MSG_TTL).trim();
		
		if(cp.getValue(VMS_RESTORE_ONGOINGC_ROOM)!=null)
			config._vms_is_restore_ongoing_conferenece  = cp.getValue(VMS_RESTORE_ONGOINGC_ROOM).trim();

		if(cp.getValue(VMS_RESTORE_ONGOINGC_ROOM_TIMEOUT)!=null)
			config._vms_restore_ongoing_conferenece_timeout  = cp.getValue(VMS_RESTORE_ONGOINGC_ROOM_TIMEOUT).trim();

		if(cp.getValue(VMS_RESTORE_ONGOINGC_ROOM_MAXCOUNT)!=null)
			config._vms_restore_ongoing_conferenece_maxcount  = cp.getValue(VMS_RESTORE_ONGOINGC_ROOM_MAXCOUNT).trim();
		
		if(cp.getValue(VMS_IVIEW_REQUEST_WAIT_TIMEOUT)!=null)
			config._vms_iview_request_wait_timeout  = cp.getValue(VMS_IVIEW_REQUEST_WAIT_TIMEOUT).trim();
		
		if(cp.getValue(VMS_SERVER_SN)!=null)
			config._sn  = cp.getValue(VMS_SERVER_SN).trim();
		
		if(cp.getValue(VMS_RECORDING_FROM_START_TO_END)!=null)
			config._vms_recording_from_start_to_end = cp.getValue(VMS_RECORDING_FROM_START_TO_END).trim();

		if(cp.getValue(VMS_CDR_ROOT)!=null)
			config._vms_cdr_root = cp.getValue(VMS_CDR_ROOT).trim();
		
		if(cp.getValue(VMS_HISTORY_LOG_ROOT)!=null)
			config._vms_log_histroy_root = cp.getValue(VMS_HISTORY_LOG_ROOT).trim();

		if(cp.getValue(VMS_NOTIFY_MESSAGE_DISPATCHER_PORT)!=null)
			config._vms_notify_message_dispatcher_port  = Integer.parseInt(cp.getValue(VMS_NOTIFY_MESSAGE_DISPATCHER_PORT));

		if(cp.getValue(VMS_NOTIFY_MESSAGE_DISPATCHER_IP)!=null)
			config._vms_notify_message_dispatcher_ip  = cp.getValue(VMS_NOTIFY_MESSAGE_DISPATCHER_IP).trim();

	}
	
	public String getUserDir()
	{
		return System.getProperty("user.dir");
	}
	
	public String getResourceAbsolutePath(String filepath,String filename)
	{
		//System.out.println("filepath:"+ filepath + "  filename:"+filename);
		String userDir = getUserDir();
		//System.out.println("user directory:"+userDir);
		String absolutePath = StringEx.getConcatPath(userDir, filepath + filename);
		//System.out.println("absolutePath:" +absolutePath);
		if(new File(absolutePath).exists()) 
		{
			//System.out.println("absolutePath file exists,reading from absolute path.");
			return absolutePath;
		}
		else{
			//System.out.println("absolutePath file does not exist, try to reading from userdir path");
			return StringEx.getConcatPath(userDir, filename);
		}
	}
	
	private CreateProperties getProperties(String filepath,String filename)
	{
		String absolutePath = getResourceAbsolutePath(filepath,filename);
		Logs.info("Reading from config file:" + absolutePath);
		return new CreateProperties(absolutePath);
	}
	
	private String getAbsolutePath(String cdrroot)
	{
		String userDir = getUserDir();
		String filepath = StringEx.getConcatPath(userDir, cdrroot);
		
		if(!new File(filepath).exists()) FileIo.createFolders(filepath);
		
		return filepath;
	}
	
	public String readConfig()
	{
		String content = "";
		try
		{
			String absolutePath = getResourceAbsolutePath(CONFIG_FILE_PATH,CONFIG_FILE_NAME);
			content = FileIo.readText(absolutePath,"utf-8");
		}catch(IOException e)
		{
			Logs.info(e.toString());
		}
		return content;		
	}
	
	public synchronized boolean writeConfig(String configContent)
	{
		try
		{
			String absolutePath = getResourceAbsolutePath(CONFIG_FILE_PATH,CONFIG_FILE_NAME);
			FileIo.writeText(absolutePath, configContent, "utf-8");
			return true;
		}catch(IOException e)
		{
			Logs.info(e.toString());
		}
		return false;
	}
	
	public Map getConfig()
	{
		Map map = new HashMap<String, String>();
		String content = readConfig();
		String[] lineA = content.split("\n");
		String line;
		String[] nameValue;
		String name;
		String value;
		for(int i = 0;i< lineA.length;i++)
		{
			line = lineA[i].trim();
			if(!"".equals(line) && !line.startsWith("#"))
			{
				nameValue = line.split("=");
				if(nameValue.length != 2) continue;
				name = nameValue[0].trim();
				value = nameValue[1].trim();
				
				if(!map.containsKey(name))
					map.put(name, value);
			}
		}
		return map;
	}

	public boolean setConfig(Map nameValueM)
	{
		if(nameValueM == null) return false;
		if(nameValueM.size() == 0) return true;
		Iterator<String> it = nameValueM.keySet().iterator();
		
		String content = readConfig();
		String regex;
		String name;
		String value;
		while(it.hasNext())
		{
			name = it.next();
			value = nameValueM.get(name).toString();
			regex = "(?m)^" + name + ".*$";
			content = content.replaceFirst(regex, name + " = " + value);
		}
		return writeConfig(content);
	}
	
	public boolean setConfig(String name,String value)
	{
		//"(?m)^CTI_DOMAIN.*$"
		String content = readConfig();
		String regex = "(?m)^" + name + ".*$";
		content = content.replaceFirst(regex, name + " = " + value);
		
		return writeConfig(content);
	}
	
	public String getMcuIp() {
		return _mcu_ip;
	}

	public String getMcuPort() {
		return _mcu_port;
	}

	public String getMcuUser() {
		return _mcu_user;
	}

	public String getMcuPwd() {
		return _mcu_pwd;
	}

	public String getServiceTemplateLoginID() {
		return _mcu_service_template_loginid;
	}
	
	public String getServiceTemplatePrefix(){
		return _mcu_service_template_prefix;
	}
	
	public String getAPIVersion(){
		return _mcu_api_version;
	}
	
	public boolean isTerminateWhenShutdown() {
		return "True".equalsIgnoreCase(_vms_terminate_shutdown);
	}
	
	public boolean isTerminateWhenStart() {
		return "True".equalsIgnoreCase(_vms_terminate_start);
	}
	
	public boolean isRoomidFixed() {
		return "True".equalsIgnoreCase(_vms_roomid_fixed);
	}
	
	public int getTerminateWhenMininumParty() {
		return StringEx.getInt(_vms_terminate_mininum_party, 2);
	}
	
	public String getCTIType()
	{
		return this._cti_type;
	}
	
	public String getCTIIP()
	{
		return this._cti_ip;
	}
	
	public String getCTIPort()
	{
		return this._cti_port;
	}
	
	public String getCTILoginID()
	{
		return this._cti_loginid;
	}
	
	public String getCTIPWD()
	{
		return this._cti_pwd;
	}
	
	public String getCTIDomain()
	{
		return this._cti_domain;
	}
	
	public String getCTIDefaultQueue()
	{
		return this._cti_deafult_queue;
	}
	
	public String getMcuSdsUrl()
	{
		return this._mcu_sds_url;
	}
	
	public String getRoomidPrefix()
	{
		return this._mcu_virtal_meeting_id_prefix + _vms_meeting_room_base;
	}
	
	public String getVMSServerList()
	{
		return this._vms_server_list;
	}
	
	public int getVMSServerPort()
	{
		return StringEx.getInt(this._vms_server_port,9100);
	}
	
	public String getVMSServerHost()
	{
		return  this._vms_server_host;
	}
		
	public String getVMSWebRoot()
	{
		return this._vms_web_root;
	}
	
	public String getVMSCdrRoot()
	{
		return getAbsolutePath(this._vms_cdr_root);
	}
	
	public boolean getIsVerboseModel()
	{
		return "True".equalsIgnoreCase(_vms_isverbose);
	}
	
	public boolean getIsdebug()
	{
		return "True".equalsIgnoreCase(_vms_isdebug);
	}
	
	public boolean getIsAutoStartService()
	{
		return "True".equalsIgnoreCase(_vms_is_auto_start_service);
	}
	
	public boolean getIsAutoStartRecording()
	{
		return "True".equalsIgnoreCase(_vms_is_auto_start_recording);
	}
	
	public int getKeepAliveTimeInterval()
	{
		return StringEx.getInt(this._vms_keep_alive_time_interval,20);
	}
	
	public int getMaxKeepAliveFailCount()
	{
		return StringEx.getInt(this._vms_max_keep_alive_fail_count,2);
	}
	
	public String getSN()
	{
		return this._sn;
	}
	
	public void setSN(String sn)
	{
		this._sn = sn;
	}
	
	public boolean getIsLogKeepLiveResult()
	{
		return "True".equalsIgnoreCase(this._vms_is_log_keeplive_result);
	}
	
	public int getRoomIDLength()
	{
		int l = 20;
		
		try
		{
			l = Integer.parseInt(this._vms_roomid_length);
		}catch(Exception e)
		{
			Logs.debug("VMS_ROOMID_LENGH[" + this._vms_roomid_length+ "] value is invalid.");
		}
		return l;
	}
	
	public String getACLCode() {
			return this._vms_servlet_access_code;
	}
	
	public boolean isMQActive()
	{
		return "true".equalsIgnoreCase(this._jms_is_active);
	}
	
	public String getJMSBroker()
	{
		return this._jms_broker;
	}
	
	public String getJMSTopic()
	{
		return this._jms_topic;
	}
	
	public long getJMSTTL()
	{
		long ttl = 600000;
		
		try
		{
			ttl = Integer.parseInt(this._jms_msg_ttl);
		}catch(Exception e)
		{
			Logs.debug("JMS_MSG_TTL[" + this._jms_msg_ttl+ "] value is invalid.");
		}
		return ttl;
	}
	
	public boolean isLoadOnGoingConference()
	{
		if("true".equalsIgnoreCase( this._vms_is_restore_ongoing_conferenece))
			return true;
		else
			return false;
	}
	
	public long getLoadOnGoingConfereneceTimeout()
	{
		long ttl = 60;
		
		try
		{
			ttl = Integer.parseInt(this._vms_restore_ongoing_conferenece_timeout);
		}catch(Exception e)
		{
			Logs.debug("VMS_RESTORE_ONGOINGC_ROOM_TIMEOUT[" + this._vms_restore_ongoing_conferenece_timeout+ "] value is invalid.");
		}
		return ttl;		
		
	}

	public int getiViewAPIRequestResponseTimeOut()
	{
		return Integer.parseInt(this._vms_iview_request_wait_timeout);
	}
	
	public List<String> getWebAccountList()
	{
		List<String> userLst = new ArrayList<String>();
		String content = readUserFromFile();
		String[] lineA = content.split("\n");
		String line;
		for(int i = 0;i< lineA.length;i++)
		{
			line = lineA[i].trim();
			if(!"".equals(line) && !line.startsWith("#"))
				userLst.add(line);
		}
		return userLst;
	}
	
	public boolean setUserToFile(String user,String info)
	{
		String content = readUserFromFile();
		String[] lineA = content.split("\n");
		String line;
		StringBuffer contents = new StringBuffer();
		for(int i = 0;i< lineA.length;i++)
		{
			line = lineA[i].trim();
			if("".equals(line) || line.startsWith("#") || !line.startsWith(user + ":"))
			{
				contents.append(line + "\n");
				continue;
			}
			contents.append(user + ":" + info + "\n");
		}
		content = StringEx.subSuffix(contents.toString());
		return writeContentToFile(content);
	}

	private String readUserFromFile()
	{
		String content = "";
		try
		{
			String absolutePath = getResourceAbsolutePath(CONFIG_FILE_PATH,CONFIG_USER_ACCOUNT_FILE_NAME);
			content = FileIo.readText(absolutePath,"utf-8");
		}catch(IOException e)
		{
			Logs.info(e.toString());
		}
		return content;		
	}
	
	private synchronized boolean writeContentToFile(String content)
	{
		boolean ret = false;
		try
		{
			String absolutePath = getResourceAbsolutePath(CONFIG_FILE_PATH,CONFIG_USER_ACCOUNT_FILE_NAME);
			FileIo.writeText(absolutePath, content, "utf-8");
			ret = true;
		}catch(IOException e)
		{
			Logs.info(e.toString());
		}	
		return ret;
	}

	public long getLoadOnGoingConfereneceMaxCount() {
		long ttl = 60;
		
		try
		{
			ttl = Integer.parseInt(this._vms_restore_ongoing_conferenece_maxcount);
		}catch(Exception e)
		{
			Logs.debug("VMS_RESTORE_ONGOINGC_ROOM_MAXCOUNT[" + this._vms_restore_ongoing_conferenece_maxcount+ "] value is invalid.");
		}
		return ttl;		
	}

	public boolean isTerminateWithSipGW()
	{
		return "true".equalsIgnoreCase(this._vms_terminate_with_sip_gw);
	}

	public boolean getIsRecordingFromStart2End() 
	{
		return "true".equalsIgnoreCase(this._vms_recording_from_start_to_end );
	}

	public String getLogRoot()
	{
		return getAbsolutePath(this._vms_log_root);
	}
	
	public String getHistroyLogRoot()
	{
		return getAbsolutePath(this._vms_log_histroy_root);
	}
	
	public int getMsgUDPDispatcherPort()
	{
		return this._vms_notify_message_dispatcher_port;
	}

	public String getMsgUDPDispatcherIP()
	{
		return this._vms_notify_message_dispatcher_ip;
	}

}