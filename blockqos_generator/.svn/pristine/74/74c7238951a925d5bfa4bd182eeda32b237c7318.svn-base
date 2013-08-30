package com.ibm.block.generator.fac.physical.impl.pl;

import com.ibm.block.generator.fac.physical.impl.PLFactory;
import com.ibm.block.generator.fac.physical.impl.ro.SmallBandwidthROfferFactory;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallPLFactory extends PLFactory {

	public SmallPLFactory(PlacementModel pm) {
		super(pm);
		super.addROfferFactory(new SmallBandwidthROfferFactory(pm));
	}

}
