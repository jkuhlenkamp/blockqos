package com.ibm.block.generator.fac.virtual.impl.rd;

import com.ibm.block.generator.fac.virtual.impl.RDemandFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallRamRDemandFactory extends RDemandFactory {

	private static ResourceType type = ResourceType.RAM;
	private static int amount = 2;
	
	public SmallRamRDemandFactory(PlacementModel model) {
		super(model, type, amount);
	}

}
