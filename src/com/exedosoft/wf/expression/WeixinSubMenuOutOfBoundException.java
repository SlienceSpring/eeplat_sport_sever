package com.exedosoft.wf.expression;

import com.exedosoft.plat.weixin.exception.WeixinException;

public class WeixinSubMenuOutOfBoundException extends WeixinException{
 
	private static final long serialVersionUID = 1119630888551409929L;
	
	public WeixinSubMenuOutOfBoundException() {
		super("sub_menu max count 5");
	}
}
