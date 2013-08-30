package com.ibm.block.generator.fac.virtual.impl.vv;

import com.ibm.block.generator.fac.virtual.impl.VVFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.LargeStorageRDemandFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargeVVFactory extends VVFactory {

	public LargeVVFactory(PlacementModel model) {
		super(model);
		super.addRDemandFactory(new LargeStorageRDemandFactory(model));
		super.addPtype(PropertyType.STORAGE_SHARED);
		super.addPtype(PropertyType.STORAGE_HDD);
	}

}
