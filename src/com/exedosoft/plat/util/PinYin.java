package com.exedosoft.plat.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYin {

	/**
	 * 将汉字转换为全拼
	 * 
	 * @param src
	 * @return String
	 */
	public static String getPinYin(String src) {
		
		PinyinUtilsPro pp = new PinyinUtilsPro();
		pp.convertChineseToPinyin(src);
		return pp.getPinyin();
		
	}

	
	public static String  getPingyinSM(String str){
		return getPinYinHeadChar(str);
	}

	/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str
	 * @return String
	 */
	public static String getPinYinHeadChar(String str) {
		PinyinUtilsPro pp = new PinyinUtilsPro();
		pp.convertChineseToPinyin(str);
		return pp.getHeadPinyin();
	}



	public static void main(String[] args) {
		
		String cnStr = "重庆";
		System.out.println(getPinYin(cnStr));
		System.out.println(getPinYinHeadChar(cnStr));

	}

}
