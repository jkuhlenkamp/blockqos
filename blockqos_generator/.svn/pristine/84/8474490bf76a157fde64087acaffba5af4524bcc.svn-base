package com.ibm.block.generator.fac.physical.impl.pm;

import com.ibm.block.generator.fac.physical.impl.PMFactory;
import com.ibm.block.generator.fac.physical.impl.ro.SmallCpuROfferFactory;
import com.ibm.block.generator.fac.physical.impl.ro.SmallRamROfferFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallPMFactory extends PMFactory {

	public SmallPMFactory(PlacementModel pm) {
		super( pm );
		super.addROfferFactory(new SmallCpuROfferFactory(pm));
		super.addROfferFactory(new SmallRamROfferFactory(pm));
		super.addPtype(PropertyType.CPU_AMD);
	}
}
