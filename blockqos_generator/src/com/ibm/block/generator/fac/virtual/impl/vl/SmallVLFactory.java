package com.ibm.block.generator.fac.virtual.impl.vl;

import com.ibm.block.generator.fac.virtual.impl.VLFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.SmallBandwidthRDemandFactory;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallVLFactory extends VLFactory {

	public SmallVLFactory(PlacementModel model) {
		super(model);
		super.addRDemandFactory(new SmallBandwidthRDemandFactory(model));
	}

}
