package com.ibm.block.model.app.intf;

import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;

public interface SamePropertyGroupInterface extends VVolumeGroupInterface {

	public HashMap<Integer, PropertyType> getTypes();
	
}
