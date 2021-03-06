package com.exedosoft.plat.ui.jquery.form;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;


public class DOValueInputFileCloud extends DOBaseForm {
	
	private static Log log = LogFactory.getLog(DOValueInputFileCloud.class);


	
	public DOValueInputFileCloud(){
		super();
	}
	public String getHtmlCode(DOIModel aModel) {
		
		
		if(isUsingTemplate){
			return super.getHtmlCode(aModel);
		}

		DOFormModel property = (DOFormModel) aModel;

		if (property.getValue() != null) {
			
			
			
			String theValue = property.getValue();
			if (theValue.indexOf(";") != -1) {

				theValue = theValue.substring(0,
						theValue.indexOf(";"));
			}

			StringBuffer buffer = new StringBuffer();

			buffer.append("<a  class='exedo_link'  target='_opener' href='");
			try {
				String bucketPath = "http://eeplatfile.oss.aliyuncs.com/";
				if(DOGlobals.getValue("oss.bucket.path")!=null){
					bucketPath = DOGlobals.getValue("oss.bucket.path");
				}
				buffer.append( bucketPath )
				.append(URLEncoder.encode(property.getValue(),"utf-8")).append("'>");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			buffer.append(theValue);

			buffer.append("</a>");
			return buffer.toString();
		}
		return "&nbsp;";
	}


	
	
	public static void main(String[] args){
		
		String aa = "200007\\99.doc";
		
	    System.out.println(aa.substring(aa.indexOf("\\")+1));
		
	}
	
	
	

}
