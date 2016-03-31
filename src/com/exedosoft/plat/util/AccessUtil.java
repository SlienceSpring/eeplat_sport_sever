package com.exedosoft.plat.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.DOAccess;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.js.RunJs;
import com.exedosoft.plat.js.RunJsFactory;;

public class AccessUtil {

	private static HashMap authClasses = new HashMap();
	
	
	public static final int CONDITION_TYPE_DEFAULT = 0;
	
	public static final int CONDITION_TYPE_SCRIPT = 1;

	public static final int CONDITION_TYPE_CLASS = 2;
	
	

	

	private static Log log = LogFactory.getLog(AccessUtil.class);

	/**
	 * 暂时用这种方法,下一步再考虑script.
	 * 
	 * @param conditionType
	 *            TODO
	 * 
	 * @return
	 */
	public static boolean isAccess(String condition, BOInstance aBI,
			int conditionType) {

		/**
		 * 暂时的做法，如果aBI为null 直接返回true. 不执行下面的class或者脚本验证
		 */
		
		/**
		 * condition==null
		 */
		if (condition == null  || condition.trim().equals("")) {
			return true;
		}
		
		if(conditionType == CONDITION_TYPE_DEFAULT){
			return true;
		}

		if (conditionType == CONDITION_TYPE_CLASS) {
			return getClassAccess(condition, aBI);
		}  else {

			String isTrue = RunJsFactory.getRunJs().evaluate(condition, aBI);
			if (isTrue != null && isTrue.equals("true")) {
				return true;
			} else {
				return false;
			}

		}

	}

	private static boolean getClassAccess(String accessClass, BOInstance aBI) {

		if (accessClass != null && !"".equals(accessClass.trim())) {
			accessClass = accessClass.replaceAll("\\s", "");
			DOAccess wfa = null;
			try {

				if (authClasses.get(accessClass) != null) {
					wfa = (DOAccess) authClasses.get(accessClass);
				} else {
					Class caClass = Class.forName(accessClass);
					wfa = (DOAccess) caClass.newInstance();
					authClasses.put(accessClass, wfa);
				}
				boolean ret = wfa.isAccess(aBI);
				return ret;

			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				// /////////////////应该是return false;
				return false;
			} catch (Exception ex1) {
				ex1.printStackTrace();
				return false;
			}
		}
		return false;

	}
	
	public static void main(String[] args){
		
		float i = 1000222221 * 1;
		float j = 1;
		float c = i + j;

		RunJs rhino = RunJsFactory.getRunJs();

		System.out.println("Begin:::" + System.currentTimeMillis());

		System.out
				.println(rhino.evaluate("System.out.println('hello world!')"));

		System.out.println("End:::" + System.currentTimeMillis());

		String aa = "new java.lang.Thread(); var a = new java.util.ArrayList(); a.add('1');a.add('2');a";
		// Long.parseLong(s)
		System.out.println(rhino.evaluate(aa, null));

		System.out.println(rhino.evaluate("1==1"));
		
		String style = "script:z1==1";
		System.out.println(style.substring(7));
		
		String ss ="var ret = true;"
				+"	var app = DOBO.getDOBOByName('do_application');"
				+"	var appIns = app.getCorrInstance(); "
				+" 	if(appIns!=null && (appIns.getUid()=='SystemManager' || appIns.getUid()=='baseapplication')){"
				+"  ret = false;"
				+"	}ret;\n\n\n";
		System.out.println(rhino.evaluate(ss, null));

		
	}
	
	
}
