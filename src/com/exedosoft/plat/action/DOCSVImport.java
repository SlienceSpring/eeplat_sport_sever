package com.exedosoft.plat.action;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOBOProperty;
import com.exedosoft.plat.bo.DOParameterService;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.I18n;

public class DOCSVImport extends DOAbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4798265805984034747L;
	private static Log log = LogFactory.getLog(DOCSVImport.class);

	public DOCSVImport() {
	}

	public String excute() {

		String tableName = this.actionForm.getValue("tableName");

		DOBO bo = DOBO.getDOBOByName(tableName);

		if (bo == null) {
			this.setEchoValue(I18n.instance().get("没有找到当前对象！"));
			return NO_FORWARD;
		}

		DOService theService = bo.getDInsertService();

		if (theService == null) {
			this.setEchoValue(I18n.instance().get("没有当前对象对应Insert服务！"));
			return NO_FORWARD;
		}

		Transaction t = theService.currentTransaction();
		try {
			t.begin();

			String fileName = this.actionForm.getValue("csvfile");

			if (fileName == null || fileName.trim().equals("")) {
				this.setEchoValue(I18n.instance().get("你还没有选择文件！"));

				return NO_FORWARD;
			}

			fileName = DOGlobals.UPLOAD_TEMP.trim() + "/" + fileName.trim();
			System.out.println("FileName::" + fileName);

			int lines = this.getNumberLines(fileName);

			if (lines == 0) {
				setEchoValue(I18n.instance().get("没有数据"));
				return NO_FORWARD;
			}

			if (lines > 20010) {
				this.setEchoValue(I18n.instance().get(
						"CSV data can not be more than 20,000 lines."));
				return NO_FORWARD;
			}
			try {
				CSVReader reader = new CSVReader(new FileReader(fileName));
				theService.beginBatch();

				String[] nextLine;
				List paras = theService.retrieveParaServiceLinks();

				// ////////////////第一行作为字段名称

				Map<Integer, String> csvNames = new HashMap<Integer, String>();

				if ((nextLine = reader.readNext()) != null) {// /第一行数据
					for (int i = 0; i < nextLine.length; i++) {
						for (Iterator it = paras.iterator(); it.hasNext();) {
							DOParameterService dops = (DOParameterService) it
									.next();
							if (nextLine[i].trim().equalsIgnoreCase(
									dops.getDop().getPropName())) {
								csvNames.put(i, nextLine[i]);
								break;
							}
						}
					}
				}

				if (csvNames.size() == 0) {
					setEchoValue(I18n.instance().get("文件格式不正确，请确保第一行为列名称。"));
					return NO_FORWARD;
				}

				List<DOBOProperty> props = bo.retrieveProperties();
				
				boolean leastOne = false;

				for (DOBOProperty prop : props) {

					if (!prop.isKeyCol() && prop.getIsNull() != null && prop.getIsNull() == 0) {

						if (!csvNames.containsValue(prop.getColName())) {
							setEchoValue(prop.getColName()
									+ I18n.instance().get("不能为空"));
							return NO_FORWARD;
						}
					}
					
					if(csvNames.containsValue(prop.getColName())){
						leastOne = true;
					}
				}
				
				if(!leastOne){
					setEchoValue(I18n.instance().get("没有对应的字段！"));
					return NO_FORWARD;					
				}
				
				while ((nextLine = reader.readNext()) != null) {// /一行数据
					Map aMap = new HashMap();
					for (int i = 0; i < nextLine.length; i++) {
						if (csvNames.containsKey(i)) {
							aMap.put(csvNames.get(i), nextLine[i]);
						}
					}
					theService.addBatch(aMap);
				}

				theService.endBatch();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			t.rollback();
			e.printStackTrace();
		} finally {
			t.end();
		}

		this.setEchoValue(I18n.instance().get("导入成功!"));

		return DEFAULT_FORWARD;
	}

	private int getNumberLines(String fileName) {

		File test = new File(fileName);
		long fileLength = test.length();
		LineNumberReader rf = null;
		int lines = 0;
		try {
			rf = new LineNumberReader(new FileReader(test));
			if (rf != null) {

				rf.skip(fileLength);
				lines = rf.getLineNumber();
				rf.close();
			}
		} catch (IOException e) {
			if (rf != null) {
				try {
					rf.close();
				} catch (IOException ee) {
				}
			}
		}
		return lines;

	}

}
