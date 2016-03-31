package com.exedosoft.plat.aop;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.aop.DOServiceStoreAfterAOP;
import com.exedosoft.plat.lucene.LuceneManager;

public class CommonAfterService implements DOServiceStoreAfterAOP {

	@Override
	public void excute(DOService aService, BOInstance bi) {

		if (aService.isInsert()) {
			bi.putValue("status", "CREATE");
			LuceneManager.createIndex(bi, true);
		} else if (aService.isUpdate()) {
			LuceneManager.createIndex(bi, false);
		}
	}

}
