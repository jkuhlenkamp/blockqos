package com.ibm.block.generator.fac.virtual.impl;

import com.ibm.block.generator.fac.core.impl.ResourceFactory;
import com.ibm.block.generator.fac.virtual.intf.RDemandFactoryInterface;
import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class RDemandFactory extends ResourceFactory implements
		RDemandFactoryInterface {

	private int amount;
	
	public RDemandFactory(PlacementModel model, ResourceType type, int amount) {
		super(model, type);
		this.amount = amount;
	}

	@Override
	public ResourceDemand create() {
		ResourceDemand product = new ResourceDemand(super.getResourceType(), amount);
		super.getModel().addEntity(product);
		return product;
	}

	@Override
	public Integer getAmount() {
		return amount;
	}

}
