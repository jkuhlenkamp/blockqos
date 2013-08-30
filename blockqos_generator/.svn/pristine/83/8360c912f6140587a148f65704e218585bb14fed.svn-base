package com.ibm.block.generator.fac.virtual.impl.vg;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.generator.fac.virtual.impl.VVGroupFactory;
import com.ibm.block.generator.fac.virtual.intf.SamePropertyGroupFactoryInterface;
import com.ibm.block.model.app.impl.vgroup.SamePropertyGroup;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SamePropertyGroupFactory extends VVGroupFactory implements
		SamePropertyGroupFactoryInterface {

	private HashMap<Integer, PropertyType> properties;
	
	public SamePropertyGroupFactory(PlacementModel model) {
		super(model);
		properties = new HashMap<>();
	}

	@Override
	public SamePropertyGroup create() {
		@SuppressWarnings("unchecked")
		ArrayList<PropertyType> clone = (
				ArrayList<PropertyType>) properties.clone();
		SamePropertyGroup product = new SamePropertyGroup(clone);
		super.getPlacementModel().addEntity(product);
		return product;
	}

	@Override
	public void addProperty(PropertyType type) {
		this.properties.put(type.getId(), type);
	}

	@Override
	public HashMap<Integer, PropertyType> getPropertyMap() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PropertyType> clone = (HashMap<Integer, 
				PropertyType>) properties.clone();
		return clone;
	}

	@Override
	public ArrayList<PropertyType> getPropertyList() {
		ArrayList<PropertyType> result = new ArrayList<>();
		for( PropertyType t : properties.values() ) {
			result.add(t);
		}
		return result;
	}

}
