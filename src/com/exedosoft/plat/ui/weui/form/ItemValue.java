package com.exedosoft.plat.ui.weui.form;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public 	class ItemValue  implements Serializable {

	private String value;
	private String label;
	private boolean checked;

	public ItemValue() {
		this.checked = false;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String id) {
		this.value = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String name) {
		this.label = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String getCheckedStr(){
		
		if(this.checked){
			return "checked='checked' selected='selected'";
		}else{
			return "";
		}
	}
	
	public String toString(){
		
		return ToStringBuilder.reflectionToString(this) + "\n";
	}

}