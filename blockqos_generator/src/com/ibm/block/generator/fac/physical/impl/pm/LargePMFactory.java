package com.ibm.block.generator.fac.physical.impl.pm;

import com.ibm.block.generator.fac.physical.impl.PMFactory;
import com.ibm.block.generator.fac.physical.impl.ro.LargeCpuROfferFactory;
import com.ibm.block.generator.fac.physical.impl.ro.LargeRamROfferFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargePMFactory extends PMFactory {
	
	public LargePMFactory(PlacementModel pm) {
		super( pm );
		super.addROfferFactory(new LargeCpuROfferFactory(pm));
		super.addROfferFactory(new LargeRamROfferFactory(pm));
		super.addPtype(PropertyType.CPU_INTEL);
	}

}
