package com.vms.cti.jtapi.tsapiclient;

public class TServerNotInServiceException extends Exception 
{
	public String _callee = null;
	public TServerNotInServiceException(String callee) 
	{		
		_callee = callee;
	}

	private static final long serialVersionUID = 1L;
}
