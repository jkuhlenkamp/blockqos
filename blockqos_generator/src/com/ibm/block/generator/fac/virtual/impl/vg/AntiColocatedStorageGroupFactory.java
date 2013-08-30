package com.ibm.block.generator.fac.virtual.impl.vg;

import com.ibm.block.generator.fac.virtual.impl.VVGroupFactory;
import com.ibm.block.generator.fac.virtual.intf.AntiColocatedStorageGroupFactoryInterface;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedStorageGroup;
import com.ibm.block.model.core.impl.PlacementModel;

public class AntiColocatedStorageGroupFactory extends VVGroupFactory implements
		AntiColocatedStorageGroupFactoryInterface {

	public AntiColocatedStorageGroupFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public AntiColocatedStorageGroup create() {
		AntiColocatedStorageGroup product = new AntiColocatedStorageGroup();
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
