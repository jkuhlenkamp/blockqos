package com.ibm.block.generator.fac.virtual.impl.rd;

import com.ibm.block.generator.fac.virtual.impl.RDemandFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallStorageRDemandFactory extends RDemandFactory {

	private static ResourceType type = ResourceType.STORAGE;
	private static int amount = 1;
	
	public SmallStorageRDemandFactory(PlacementModel model) {
		super(model, type, amount);
	}

}
