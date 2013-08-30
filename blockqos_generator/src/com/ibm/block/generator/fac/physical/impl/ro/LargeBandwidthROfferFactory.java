package com.ibm.block.generator.fac.physical.impl.ro;

import com.ibm.block.generator.fac.physical.impl.ROfferFactory;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.ResourceOffer;

public class LargeBandwidthROfferFactory extends ROfferFactory {

	private static ResourceType type = ResourceType.BANDWIDTH;
	private static Integer capacity = 100;
	
	public LargeBandwidthROfferFactory(PlacementModel model) {
		super(model, type, capacity);
	}

	@Override
	public ResourceOffer create() {
		ResourceOffer product = new ResourceOffer(type, capacity);
		super.getModel().addEntity(product);
		return product;
	}
}