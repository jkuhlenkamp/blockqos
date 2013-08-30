package com.ibm.block.generator.fac.virtual.impl.vl;

import com.ibm.block.generator.fac.virtual.impl.VLFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.LargeBandwidthRDemandFactory;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargeVLFactory extends VLFactory {

	public LargeVLFactory(PlacementModel model) {
		super(model);
		super.addRDemandFactory(new LargeBandwidthRDemandFactory(model));
	}

}
