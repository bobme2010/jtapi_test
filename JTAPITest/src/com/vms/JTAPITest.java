package com.vms;

import com.vms.cti.CTIManager;
import com.vms.cti.ICTI;
import com.vms.util.Logs;

public class JTAPITest
{

	public static void main(String[] args) throws Exception
	{
		CTIManager ctiManager = CTIManager.getInstance();
		
		ctiManager.startService();
		
		ICTI cti = ctiManager.getCTI();
		while(true)
		{
			int s = System.in.read();
			if(s==113) // 
				break;
			try{
				cti.insertContact("7122", "8301", "abc");
				System.in.read();
				cti.dropContact("7122");				
			}
			catch(Exception e)
			{
				Logs.debug(e.getMessage());
			}
		}		

		ctiManager.stopService();
	}

}
