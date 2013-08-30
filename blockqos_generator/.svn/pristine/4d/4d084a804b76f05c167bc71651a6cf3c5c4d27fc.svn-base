package com.ibm.block.generator.fac.virtual.impl.vg;

import com.ibm.block.generator.fac.virtual.impl.VVGroupFactory;
import com.ibm.block.generator.fac.virtual.intf.ColocatedStorageGroupFactoryInterface;
import com.ibm.block.model.app.impl.vgroup.ColocatedStorageGroup;
import com.ibm.block.model.core.impl.PlacementModel;

public class ColocatedStorageGroupFactory extends VVGroupFactory implements
		ColocatedStorageGroupFactoryInterface {

	public ColocatedStorageGroupFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public ColocatedStorageGroup create() {
		ColocatedStorageGroup product = new ColocatedStorageGroup();
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
