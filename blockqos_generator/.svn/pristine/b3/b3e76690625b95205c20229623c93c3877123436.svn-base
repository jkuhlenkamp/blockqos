package com.ibm.block.generator.fac.virtual.impl;

import com.ibm.block.generator.fac.virtual.intf.VVFactoryInterface;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class VVFactory extends VFactory implements VVFactoryInterface {

	public VVFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public VVolume create() {
		VVolume product = new VVolume();
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
