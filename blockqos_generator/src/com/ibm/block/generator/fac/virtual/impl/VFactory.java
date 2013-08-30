package com.ibm.block.generator.fac.virtual.impl;

import java.util.ArrayList;

import com.ibm.block.generator.fac.core.impl.EntityFactory;
import com.ibm.block.generator.fac.virtual.intf.VFactoryInterface;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class VFactory extends EntityFactory implements VFactoryInterface {

	private ArrayList<RDemandFactory> factories;
	
	public VFactory(PlacementModel model) {
		super(model);
		factories = new ArrayList<>();
	}

	@Override
	public ArrayList<RDemandFactory> getRDemandFactories() {
		@SuppressWarnings("unchecked")
		ArrayList<RDemandFactory> clone = (ArrayList<
				RDemandFactory>) factories.clone();
		return clone;
	}

	@Override
	public void addRDemandFactory(RDemandFactory factory) {
		factories.add(factory);
	}

}
