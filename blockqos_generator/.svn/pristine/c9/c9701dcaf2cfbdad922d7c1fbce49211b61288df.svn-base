package com.ibm.block.generator.fac.physical.impl;

import com.ibm.block.generator.fac.core.impl.ResourceFactory;
import com.ibm.block.generator.fac.physical.intf.ROfferFactoryInterface;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.ResourceOffer;

public abstract class ROfferFactory extends ResourceFactory implements
		ROfferFactoryInterface {

	private Integer capacity;

	public ROfferFactory(PlacementModel model, ResourceType type, Integer capacity) {
		super(model, type);
		this.capacity = capacity;
	}
	
	@Override
	public ResourceOffer create() {
		ResourceOffer product = new ResourceOffer(super.getResourceType(), capacity);
		super.getModel().addEntity(product);
		return product;
	}
	
	@Override
	public Integer getCapacity() {
		return capacity;
	}

}
