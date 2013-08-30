package com.ibm.block.generator.fac.virtual.impl.rd;

import com.ibm.block.generator.fac.virtual.impl.RDemandFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public class MediumRamRDemandFactory extends RDemandFactory {

	private static ResourceType type = ResourceType.RAM;
	private static int amount = 4;
	
	public MediumRamRDemandFactory(PlacementModel model) {
		super(model, type, amount);
	}
	
}
