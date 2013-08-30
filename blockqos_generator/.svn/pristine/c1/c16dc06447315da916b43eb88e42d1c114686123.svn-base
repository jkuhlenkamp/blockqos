package com.ibm.block.generator.fac.core.impl;

import com.ibm.block.generator.fac.core.intf.ResourceFactoryInterface;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class ResourceFactory implements ResourceFactoryInterface {

	private ResourceType type;
	private PlacementModel model;
	
	public ResourceFactory(PlacementModel model, ResourceType type) {
		this.model = model;
		this.type = type;
	}
	
	@Override
	public ResourceType getResourceType() {
		return type;
	}
	
	public PlacementModel getModel() {
		return model;
	}

}
