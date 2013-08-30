package com.ibm.block.model.dc.intf;

import java.util.HashMap;

import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.intf.EntityInterface;
import com.ibm.block.model.dc.impl.ResourceOffer;

public interface PEntityInterface extends EntityInterface {

	// Getters
		public ResourceOffer getResource(ResourceType type);
		public HashMap<Integer, ResourceOffer> getResourceMap();
		
		// Setters
		public void addResource(ResourceOffer resource);
		
		// Util
		public boolean hasResource(ResourceType type);
}
