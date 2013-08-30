package com.ibm.block.generator.fac.virtual.impl;

import com.ibm.block.generator.fac.virtual.intf.VMFactoryInterface;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class VMFactory extends VFactory implements VMFactoryInterface {

	public VMFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public VMachine create() {
		VMachine product = new VMachine();
		for( RDemandFactory f : super.getRDemandFactories() ) {
			product.addResource(f.create());
		}
		for( PropertyType t : super.getPtypeList() ) {
			product.addProperty(t);
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
