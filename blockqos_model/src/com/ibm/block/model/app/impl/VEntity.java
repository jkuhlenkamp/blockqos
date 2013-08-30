package com.ibm.block.model.app.impl;

import java.security.InvalidParameterException;
import java.util.HashMap;

import com.ibm.block.model.app.intf.VEntityInterface;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.Entity;

public abstract class VEntity extends Entity implements VEntityInterface {

	private HashMap<Integer, ResourceDemand> resources;
	
	public VEntity(String name) {
		super(name);
		resources = new HashMap<>();
	}

	@Override
	public ResourceDemand getResource(ResourceType type) {
		for( ResourceDemand r : resources.values() ) {
			if( r.getType().equals(type) ) {
				return r;
			}
		}
		throw new InvalidParameterException("Error: No resource of the provided" +
				"argument type for this VEntity! ResourceType:" +type+ "; VEntity:" +
				pretty());
	}

	@Override
	public HashMap<Integer, ResourceDemand> getResourceMap() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, ResourceDemand> clone = 
			(HashMap<Integer, ResourceDemand>) resources.clone();
		return clone;
	}

	@Override
	public boolean hasResource(ResourceType type) {
		for( ResourceDemand r : resources.values() ) {
			if( r.getType().equals(type) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addResource(ResourceDemand resource) {
		resources.put(resource.getId(), resource);
		resource.setVEntity(this);
	}
	
}