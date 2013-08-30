package com.ibm.block.model.core.impl;

import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.intf.EntityInterface;
import com.ibm.block.model.util.impl.Id;

public abstract class Entity implements EntityInterface {

	private int id;
	private String name;
	private HashMap<Integer, PropertyType> properties;
	
	public Entity(String name) {
		id = Id.getInstance().getId();
		this.name = name+"_"+id;
		properties = new HashMap<>();
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void addProperty(PropertyType type) {
		properties.put(type.getId(), type);
	}

	@Override
	public boolean hasProperty(PropertyType type) {
		if( properties.containsKey(type.getId()) && 
				properties.containsValue(type)) {
			return true;
		}
		return false;
	}

	@Override
	public HashMap<Integer, PropertyType> getPropertyMap() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PropertyType> clone = (HashMap<Integer, 
				PropertyType>) properties.clone();
		return clone;
	}
}
