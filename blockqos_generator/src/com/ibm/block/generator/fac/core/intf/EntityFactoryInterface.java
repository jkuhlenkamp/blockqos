package com.ibm.block.generator.fac.core.intf;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public interface EntityFactoryInterface {

	public PlacementModel getPlacementModel();
	public void addPtype(PropertyType type);
	public HashMap<Integer, PropertyType> getPtypeMap();
	public ArrayList<PropertyType> getPtypeList();
	
}
