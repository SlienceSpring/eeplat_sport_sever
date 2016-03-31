package com.exedosoft.plat.ui.jquery.form.my97;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.jquery.form.DOBaseForm;
import com.exedosoft.plat.util.DOGlobals;
import com.exedosoft.plat.util.StringUtil;

/**
 * my97 是非常强大的日期控件。 可以通过配置类改变其显示行为。 默认是日期显示，可以显示成日期时间，时间等。
 * 日期时间的配置：{dateFmt:'yyyy-MM-dd HH:mm:ss'}
 * 时间的配置如：{skin:'whyGreen',dateFmt:'H:mm:ss'}
 * 
 * @author IBM
 * 
 */
public class DatePicker extends DOBaseForm {

	@Override
	public String getHtmlCode(DOIModel aModel) {

		DOFormModel formModel = (DOFormModel) aModel;

		StringBuffer buffer = new StringBuffer();

		buffer.append("<input class='my97date' ").append(" id='")
				.append(formModel.getFullColID()).append("' name='")
				.append(formModel.getFullColName()).append("'");

		buffer.append(this.appendValidateConfig(formModel, false, true));

		buffer.append(getDecoration(formModel));

		buffer.append(" title='").append(formModel.getL10n()).append("'");

		buffer.append(" onClick=\"WdatePicker(");

		String formatStr = "yyyy-M-d HH:mm";
		if (formModel.getInputConstraint() != null) {
			buffer.append(formModel.getInputConstraint());
		} else {
			boolean isDateTime = false;
			if (formModel.getRelationProperty() != null
					&& (formModel.getRelationProperty().isDateTimeType() || formModel
							.getRelationProperty().isTimestampType())) {
				isDateTime = true;
			}
			if ("en".equals(DOGlobals.getValue("lang.local"))) {
				if (isDateTime) {
					formatStr = "M/d/yyyy HH:mm";
					buffer.append("{lang:'en', dateFmt:'M/d/yyyy HH:mm'}");
				} else {
					formatStr = "M/d/yyyy";
					buffer.append("{lang:'en', dateFmt:'M/d/yyyy'}");
				}
			} else {
				if (isDateTime) {
					formatStr = "yyyy-M-d HH:mm";
					buffer.append("{dateFmt:'yyyy-M-d HH:mm'}");
				} else {
					formatStr = "yyyy-M-d";
					buffer.append("{dateFmt:'yyyy-M-d'}");
				}
			}
		}

		buffer.append(")\" ");

		if (formModel.getValue() != null) {

			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

			String dataValue = formModel.getValue();
			// if (!isValidDate(dataValue)) {
			// if (dataValue.indexOf(" ") != -1) {
			// dataValue = dataValue.split(" ")[0];
			// }
			// }
			try {

				if (dataValue.indexOf(".") != -1) {

					Timestamp tstp = Timestamp.valueOf(dataValue);
					buffer.append(" value='").append(sdf.format(tstp))
							.append("'");
				} else {
					buffer.append(" value='")
							.append(sdf.format(StringUtil.getDateTimeFormat(
									dataValue).parse(dataValue))).append("'");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		buffer.append(" size='20' />");

		// if (property.isNotNull()) {
		// buffer.append("&nbsp;<font color='red'>*</font>");
		// }

		return buffer.toString();
	}

	public static boolean isValidDate(String aDateValue) {

		if (aDateValue == null) {
			return false;
		}

		// if (aDateValue.length() <= 7) {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M");
		//
		// try {
		// aDateValue = aDateValue+"-01 00:00:00";
		// dateFormat.parse(aDateValue);
		// return true;
		// } catch (Exception e) {
		// return false;
		// }
		// }

		if (aDateValue.length() <= 10) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");

			try {
				dateFormat.parse(aDateValue);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		if (aDateValue.length() <= 16) {
			SimpleDateFormat dateFormat1 = new SimpleDateFormat(
					"yyyy-M-dd HH:mm");

			try {
				dateFormat1.parse(aDateValue);
				return true;
			} catch (Exception e) {
				return false;
			}
		}

		if (aDateValue.length() <= 19) {
			SimpleDateFormat dateFormat2 = new SimpleDateFormat(
					"yyyy-M-dd HH:mm:ss");
			try {
				dateFormat2.parse(aDateValue);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		String str = "1998-08-03";

		System.out.print(DatePicker.isValidDate(str));

	}

}
