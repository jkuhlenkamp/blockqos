package com.ibm.block.model.app.intf;

import java.util.HashMap;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.intf.EntityInterface;

public interface VEntityInterface extends EntityInterface {

	// Getters
	public ResourceDemand getResource(ResourceType type);
	public HashMap<Integer, ResourceDemand> getResourceMap();
	
	// Setters
	public void addResource(ResourceDemand resource);
	
	// Util
	public boolean hasResource(ResourceType type);
}
