package com.ibm.block.model.core.enums;

import com.ibm.block.model.util.impl.Id;

public enum ResourceType {

	CORES("#"),
	RAM("GB"),
	STORAGE("GB"),
	BANDWIDTH("Gbit/s");
	
	private final int id;
	private final String metric;
	
	private ResourceType(String metric) {
		this.id = Id.getInstance().getId();
		this.metric = metric;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getMetric() {
		return metric;
	}
	
	public String pretty() {
		return "[id:" +id+ ", name:" +super.toString()+ ", metric: " +metric+ "]";
	}
}
