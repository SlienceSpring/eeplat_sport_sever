package com.exedosoft.plat.ui.bootstrap.grid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;

/**
 * @author aa
 */
public class GridListJSON extends GridList {

	private static Log log = LogFactory.getLog(GridListJSON.class);

	public GridListJSON() {
	
		dealTemplatePath("/grid/GridListJSON.ftl");
	}
}
