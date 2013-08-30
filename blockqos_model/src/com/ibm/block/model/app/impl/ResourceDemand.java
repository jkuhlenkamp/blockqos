package com.ibm.block.model.app.impl;

import com.ibm.block.model.app.intf.ResourceDemandInterface;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.Resource;

public class ResourceDemand extends Resource implements ResourceDemandInterface {

	private int amount;
	private VEntity parent;
	
	public ResourceDemand(ResourceType type, int amount) {
		super(type);
		this.amount = amount;
	}

	@Override
	public String pretty() {
		String s = "[";
		s += "class:" +super.getClass()+ ", ";
		s += "id:" +super.getId()+ ", ";
		s += "name:" +super.getName()+ ", ";
		s += "type:" +super.getType()+ ", ";
		s += "amount:" +amount;
		s += "]";
		return s;
	}

	@Override
	public Integer getAmount() {
		return amount;
	}

	@Override
	public VEntity getPEntity() {
		return parent;
	}

	@Override
	public void setVEntity(VEntity parent) {
		this.parent = parent;
	}

}
