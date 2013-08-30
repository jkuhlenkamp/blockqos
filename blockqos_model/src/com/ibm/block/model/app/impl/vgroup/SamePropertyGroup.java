package com.ibm.block.model.app.impl.vgroup;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.app.impl.VVolumeGroup;
import com.ibm.block.model.app.intf.SamePropertyGroupInterface;
import com.ibm.block.model.core.enums.PropertyType;

public class SamePropertyGroup extends VVolumeGroup implements SamePropertyGroupInterface{

	private static String namePrefix = "g_sameprop";
	private HashMap<Integer, PropertyType> types;
	
	public SamePropertyGroup(ArrayList<PropertyType> types) {
		super(namePrefix);
		this.types = new HashMap<Integer, PropertyType>();
		for( PropertyType t : types ) {
			this.types.put(t.getId(), t);
		}
	}
	
	public SamePropertyGroup(HashMap<Integer, PropertyType> types) {
		super(namePrefix);
		@SuppressWarnings("unchecked")
		HashMap<Integer, PropertyType> clone = (HashMap<Integer, 
				PropertyType>) types.clone();
		this.types = clone;
	}

	@Override
	public HashMap<Integer, PropertyType> getTypes() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PropertyType> clone = (HashMap<Integer, 
				PropertyType>) types.clone();
		return clone;
	}
}
