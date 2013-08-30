package com.ibm.block.generator.fac.core.intf;

import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public interface ResourceFactoryInterface {
	
	public ResourceType getResourceType();
	public PlacementModel getModel();
	
}
