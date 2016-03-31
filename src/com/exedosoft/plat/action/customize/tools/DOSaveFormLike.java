package com.exedosoft.plat.action.customize.tools;

import java.util.List;

import com.exedosoft.plat.ExedoException;
import com.exedosoft.plat.Transaction;
import com.exedosoft.plat.action.DOAbstractAction;
import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOBO;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.ui.DOFormModel;

public class DOSaveFormLike extends DOAbstractAction {

	private static final long serialVersionUID = 3155689928718730452L;

	@Override
	public String excute() throws ExedoException {

		DOBO boForm = DOBO.getDOBOByName("do_ui_formmodel");
		BOInstance beforBiForm = boForm.getCorrInstance();
		DOFormModel beforFormModel = DOFormModel.getFormModelByID(beforBiForm
				.getUid());

		BOInstance curBiForm = null;
		if (this.service != null) {
			curBiForm = this.service.invokeUpdate();
		}

		DOService updateLikeService = DOService
				.getService("DO_UI_FormModel_Update_like");

		Transaction t = updateLikeService.currentTransaction();
		try {
			t.begin();

			if (beforFormModel.getRelationProperty() != null) {
				List<DOFormModel> likeForms = DOFormModel.getFormModelByLike(
						beforFormModel.getL10n(), beforFormModel
								.getRelationProperty().getObjUid(),
						beforFormModel.getController().getObjUid());
				for (DOFormModel aFm : likeForms) {
					curBiForm.putValue("objuid", aFm.getObjUid());
					updateLikeService.store(curBiForm);
				}
			}
			t.end();
		} catch (Exception e) {
			t.rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DEFAULT_FORWARD;

	}

}
