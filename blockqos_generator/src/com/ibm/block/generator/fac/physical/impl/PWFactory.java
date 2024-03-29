package com.ibm.block.generator.fac.physical.impl;

import com.ibm.block.generator.fac.physical.intf.PWFactoryInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PSwitch;

public abstract class PWFactory extends PFactory implements PWFactoryInterface {

	public PWFactory(PlacementModel pm) {
		super(pm);
	}

	@Override
	public PSwitch create() {
		PSwitch product = new PSwitch();
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
