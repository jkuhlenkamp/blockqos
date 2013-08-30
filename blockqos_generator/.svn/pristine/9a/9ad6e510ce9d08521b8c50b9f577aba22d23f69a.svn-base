package com.ibm.block.generator.fac.virtual.impl.vm;

import com.ibm.block.generator.fac.virtual.impl.VMFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.SmallCpuRDemandFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.SmallRamRDemandFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallVMFactory extends VMFactory {

	public SmallVMFactory(PlacementModel model) {
		super(model);
		super.addRDemandFactory(new SmallCpuRDemandFactory(model));
		super.addRDemandFactory(new SmallRamRDemandFactory(model));
		super.addPtype(PropertyType.CPU_AMD);
	}

}
