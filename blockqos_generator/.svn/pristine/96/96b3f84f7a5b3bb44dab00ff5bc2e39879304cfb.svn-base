package com.ibm.block.generator.fac.core.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.generator.fac.core.intf.EntityFactoryInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class EntityFactory implements EntityFactoryInterface {

	private PlacementModel model;
	private HashMap<Integer, PropertyType> ptypes;
	
	public EntityFactory( PlacementModel model ) {
		this.model = model;
		ptypes = new HashMap<>();
	}

	@Override
	public PlacementModel getPlacementModel() {
		return model;
	}

	@Override
	public void addPtype(PropertyType type) {
		ptypes.put(type.getId(), type);
	}

	@Override
	public HashMap<Integer, PropertyType> getPtypeMap() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PropertyType> clone = (HashMap<Integer, 
				PropertyType>) ptypes.clone();
		return clone;
	}
	
	@Override
	public ArrayList<PropertyType> getPtypeList() {
		ArrayList<PropertyType> result = new ArrayList<>();
		for(PropertyType t : ptypes.values()) {
			result.add(t);
		}
		return result;
	}
	
}
