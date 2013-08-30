package com.ibm.block.generator.fac.physical.impl;

import com.ibm.block.generator.fac.physical.intf.PSFactoryInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PStorage;

public abstract class PSFactory extends PFactory implements PSFactoryInterface {

	public PSFactory(PlacementModel pm) {
		super(pm);
	}

	@Override
	public PStorage create() {
		PStorage product = new PStorage();
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
