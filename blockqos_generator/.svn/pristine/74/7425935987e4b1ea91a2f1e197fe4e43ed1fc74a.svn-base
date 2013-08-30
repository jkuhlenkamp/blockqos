package com.ibm.block.generator.fac.virtual.impl.vg;

import com.ibm.block.generator.fac.virtual.intf.ReplicaGroupFactoryInterface;
import com.ibm.block.generator.fac.virtual.impl.VVGroupFactory;
import com.ibm.block.model.app.impl.vgroup.ReplicaGroup;
import com.ibm.block.model.core.impl.PlacementModel;

public class ReplicaGroupFactory extends VVGroupFactory implements ReplicaGroupFactoryInterface{

	public ReplicaGroupFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public ReplicaGroup create() {
		ReplicaGroup product = new ReplicaGroup();
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
