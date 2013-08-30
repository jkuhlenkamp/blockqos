package com.ibm.block.model.core.enums;

import com.ibm.block.model.util.impl.Id;

public enum PropertyType {
	CPU_AMD(), CPU_INTEL(), 
	STORAGE_SHARED(), STORAGE_EXCLUSIVE(),
	STORAGE_SSD(), STORAGE_HDD;
	
	private final Integer id;
	
	private PropertyType() {
		this.id = Id.getInstance().getId();
	}
	
	public Integer getId() {
		return id;
	}
	
}
