package com.ibm.block.generator.fac.physical.impl.ro;

import com.ibm.block.generator.fac.physical.impl.ROfferFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.ResourceOffer;

public class SmallStorageROfferFactory extends ROfferFactory {

	private static ResourceType type = ResourceType.STORAGE;
	private static Integer capacity = 3;
	
	public SmallStorageROfferFactory(PlacementModel model) {
		super(model, type, capacity);
	}

	@Override
	public ResourceOffer create() {
		ResourceOffer product = new ResourceOffer(type, capacity);
		super.getModel().addEntity(product);
		return product;
	}

}
