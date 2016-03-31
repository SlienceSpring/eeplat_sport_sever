package com.exedosoft.plat.ui.jquery.grid.echarts;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.ui.DOFormModel;
import com.exedosoft.plat.ui.DOGridModel;
import com.exedosoft.plat.ui.DOIModel;
import com.exedosoft.plat.ui.DOViewTemplate;
import com.exedosoft.plat.ui.echarts.XAxis;
import com.exedosoft.plat.ui.echarts.YAxis;

/**
 * @author aa
 */
public class Pie extends DOViewTemplate {

    private static Log log = LogFactory.getLog(Pie.class);

    public Pie() {

        dealTemplatePath("/grid/echarts/Pie.ftl");
    }

    public Map<String, Object> putData(DOIModel doimodel) {

        DOGridModel gm = (DOGridModel) doimodel;
        if (gm.getService() == null) {
            return null;
        }
        Map<String, Object> data = super.putData(doimodel);

        if (gm.getService() != null) {
            String note = gm.getService().getNote();
            String xCol = "name";
            String yCol = "value";

            List<DOFormModel> forms = gm.getAllGridFormLinks();

            if (forms != null && forms.size() >= 2) {

                for (DOFormModel aFm : forms) {
                    if (aFm.getController().getViewJavaClass().equals(XAxis.class.getCanonicalName())) {
                        xCol = aFm.getColName();
                    } else if (aFm.getController().getViewJavaClass().equals(YAxis.class.getCanonicalName())) {
                        yCol = aFm.getColName();
                    }

                }
            } else if (note != null && note.indexOf(",") != -1) {
                String[] cols = note.split(",");
                xCol = cols[0];
                yCol = cols[1];
            }

            List<BOInstance> list = gm.getService().invokeSelect();
            StringBuffer nameValues = new StringBuffer("[");

            StringBuffer names = new StringBuffer("[");

            for (Iterator<BOInstance> it = list.iterator(); it.hasNext();) {
                BOInstance anBI = it.next();
                nameValues.append("{ name:'")
                        .append(anBI.getValue(xCol))
                        .append("',  value: '")
                        .append(anBI.getValue(yCol))
                        .append("'}");

                names.append("'").append(anBI.getValue(xCol)).append("'");

                if (it.hasNext()) {
                    nameValues.append(",");
                    names.append(",");
                }
            }
            nameValues.append("]");
            names.append("]");
            data.put("nameValues", nameValues.toString());
            data.put("names", names.toString());

        }

        if (gm.getContainerPane() != null) {
            data.put("pmlName", gm.getContainerPane().getName());
        }
        return data;
    }

}
