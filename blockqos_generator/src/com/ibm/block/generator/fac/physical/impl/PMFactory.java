package com.ibm.block.generator.fac.physical.impl;

import com.ibm.block.generator.fac.physical.intf.PMFactoryInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PMachine;

public abstract class PMFactory extends PFactory implements PMFactoryInterface {

	public PMFactory(PlacementModel pm) {
		super(pm);
	}

	@Override
	public PMachine create() {
		PMachine product = new PMachine();
		for( ROfferFactory f : super.getROfferFactories() ) {
			product.addResource(f.create());
		}
		for( PropertyType t : super.getPtypeList() ) {
			product.addProperty(t);
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
