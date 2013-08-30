package com.ibm.block.generator.fac.virtual.impl.vv;

import com.ibm.block.generator.fac.virtual.impl.VVFactory;
import com.ibm.block.generator.fac.virtual.impl.rd.SmallStorageRDemandFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallVVFactory extends VVFactory {

	public SmallVVFactory(PlacementModel model) {
		super(model);
		super.addRDemandFactory(new SmallStorageRDemandFactory(model));
		super.addPtype(PropertyType.STORAGE_EXCLUSIVE);
		super.addPtype(PropertyType.STORAGE_SSD);
	}

}
