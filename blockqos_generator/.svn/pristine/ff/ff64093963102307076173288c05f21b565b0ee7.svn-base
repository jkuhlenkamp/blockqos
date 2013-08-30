package com.ibm.block.generator.fac.virtual.impl.vm;

import com.ibm.block.generator.fac.virtual.impl.VMFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.LargeCpuRDemandFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.LargeRamRDemandFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargeVMFactory extends VMFactory {

	public LargeVMFactory(PlacementModel model) {
		super(model);
		super.addRDemandFactory(new LargeCpuRDemandFactory(model));
		super.addRDemandFactory(new LargeRamRDemandFactory(model));
		super.addPtype(PropertyType.CPU_INTEL);
	}

}
