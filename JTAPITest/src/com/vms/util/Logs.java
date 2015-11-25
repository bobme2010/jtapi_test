package com.vms.util;

import com.framework.util.BaseLog;

/**
 * 日志类
 * @author 9995857
 * @version 2.0
 */
public class Logs {
	
	public static void info(String str)
	{	
		BaseLog.isVerboseModel = Config.getInstance().getIsVerboseModel();
		BaseLog.info(str);
	}
	
	public static void debug(String str)
	{
		BaseLog.isVerboseModel = Config.getInstance().getIsVerboseModel();
		if(Config.getInstance().getIsdebug())
			BaseLog.info(str);
	}
	
	public static void printStacks(String prefix)
	{
		 Throwable ex = new Throwable();
	     StackTraceElement[] stackElements = ex.getStackTrace();

		if (stackElements != null) {
		    for (int i = 1; i < stackElements.length; i++) {
		    	BaseLog.info(prefix + " level["+i+"]"+ stackElements[i].getClassName() + "." + stackElements[i].getMethodName()+"() line["+stackElements[i].getLineNumber() +"]");
		    }

		    BaseLog.info(prefix + "-------------------------------");
		}

	}
}
