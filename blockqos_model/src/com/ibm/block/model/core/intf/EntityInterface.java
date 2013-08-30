package com.ibm.block.model.core.intf;

import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;

public interface EntityInterface {

	// Getters
	public Integer getId();
	public String getName();
	public HashMap<Integer, PropertyType> getPropertyMap();
	
	// Setters
	public void setName(String name);
	public void addProperty(PropertyType type);
	
	// Util
	public abstract String pretty();
	public boolean hasProperty(PropertyType type);
	
}
