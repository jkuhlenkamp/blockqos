package com.ibm.block.generator.fac.physical.impl.pl;

import com.ibm.block.generator.fac.physical.impl.PLFactory;
import com.ibm.block.generator.fac.physical.impl.ro.LargeBandwidthROfferFactory;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargePLFactory extends PLFactory {

	public LargePLFactory(PlacementModel pm) {
		super(pm);
		super.addROfferFactory(new LargeBandwidthROfferFactory(pm));
	}

}
