package com.ibm.block.model.dc.impl;

import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.Resource;
import com.ibm.block.model.dc.intf.ResourceOfferInterface;

public class ResourceOffer extends Resource implements ResourceOfferInterface {

	private int capacity;
	private int usage;
	private PEntity parent;
	
	public ResourceOffer(ResourceType type, int capacity) {
		super(type);
		super.setName("RO_"+super.getId());
		this.capacity = capacity;
		usage = 0;
	}
	
	@Override
	public String pretty() {
		String s = "[";
		s += "class:" +super.getClass()+ ", ";
		s += "id:" +super.getId()+ ", ";
		s += "name:" +super.getName()+ ", ";
		s += "type:" +super.getType()+ ", ";
		s += "capacity:" +capacity+ ", ";
		s += "usage:" +usage+ ", ";
		s += "utilization:" +getUtilization();
		s += "]";
		return s;
	}
	
	@Override
	public Integer getCapacity() {
		return capacity;
	}

	@Override
	public Integer getUsage() {
		return usage;
	}

	@Override
	public Integer getUtilization() {
		if( usage == 0 ) {
			return 0;
		}
		return (int) (capacity / usage);
	}

	@Override
	public void setUsage(Integer usage) {
		if( usage <= capacity ) {
			this.usage = usage;
		}
	}

	@Override
	public Integer getLeftCapacity() {
		return (capacity-usage);
	}

	@Override
	public void setParent(PEntity parent) {
		this.parent = parent;
	}

	@Override
	public PEntity getPEntity() {
		return parent;
	}

}
