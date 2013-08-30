package com.ibm.block.generator.fac.virtual.impl.vg;

import com.ibm.block.generator.fac.virtual.impl.VVGroupFactory;
import com.ibm.block.generator.fac.virtual.intf.AntiColocatedPathGroupFactoryInterface;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedPathGroup;
import com.ibm.block.model.core.impl.PlacementModel;

public class AntiColocatedPathGroupFactory extends VVGroupFactory implements
		AntiColocatedPathGroupFactoryInterface {

	private int limit;
	
	public AntiColocatedPathGroupFactory(PlacementModel model, int limit) {
		super(model);
		this.limit = limit;
	}

	@Override
	public AntiColocatedPathGroup create() {
		AntiColocatedPathGroup product = new AntiColocatedPathGroup(limit);
		super.getPlacementModel().addEntity(product);
		return product;
	}

	@Override
	public Integer getSameSwitchLimit() {
		return limit;
	}

}
