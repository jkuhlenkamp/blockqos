package com.ibm.block.model.dc.impl;

import java.security.InvalidParameterException;
import java.util.HashMap;

import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.Entity;
import com.ibm.block.model.dc.intf.PEntityInterface;

public abstract class PEntity extends Entity implements PEntityInterface {

	private HashMap<Integer, ResourceOffer> resources;
	
	public PEntity(String name) {
		super(name);
		resources = new HashMap<>();
	}

	@Override
	public ResourceOffer getResource(ResourceType type) {
		for( ResourceOffer r : resources.values() ) {
			if( r.getType().equals(type) ) {
				return r;
			}
		}
		throw new InvalidParameterException("Error: No resource of the provided" +
				"argument type for this VEntity! ResourceType:" +type+ "; VEntity:" +
				pretty());
	}

	@Override
	public HashMap<Integer, ResourceOffer> getResourceMap() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, ResourceOffer> clone = 
			(HashMap<Integer, ResourceOffer>) resources.clone();
		return clone;
	}

	@Override
	public void addResource(ResourceOffer resource) {
		resources.put(resource.getId(), resource);
		resource.setParent(this);
	}

	@Override
	public boolean hasResource(ResourceType type) {
		for( ResourceOffer r : resources.values() ) {
			if( r.getType().equals(type) ) {
				return true;
			}
		}
		return false;
	}

}
