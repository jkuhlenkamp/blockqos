package com.ibm.block.generator.fac.virtual.impl.rd;

import com.ibm.block.generator.fac.virtual.impl.RDemandFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallCpuRDemandFactory extends RDemandFactory {

	private static ResourceType type = ResourceType.CORES;
	private static int amount = 2;
	
	public SmallCpuRDemandFactory(PlacementModel model) {
		super(model, type, amount);
	}

}
