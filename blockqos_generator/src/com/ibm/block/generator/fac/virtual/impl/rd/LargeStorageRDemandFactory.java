package com.ibm.block.generator.fac.virtual.impl.rd;

import com.ibm.block.generator.fac.virtual.impl.RDemandFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargeStorageRDemandFactory extends RDemandFactory {

	private static ResourceType type = ResourceType.STORAGE;
	private static int amount = 1;
	
	public LargeStorageRDemandFactory(PlacementModel model) {
		super(model, type, amount);
	}

}
