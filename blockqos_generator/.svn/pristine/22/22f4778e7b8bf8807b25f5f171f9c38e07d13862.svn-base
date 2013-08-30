package com.ibm.block.generator.fac.physical.impl;

import java.util.ArrayList;

import com.ibm.block.generator.fac.core.impl.EntityFactory;
import com.ibm.block.generator.fac.physical.intf.PFactoryInterface;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class PFactory extends EntityFactory implements PFactoryInterface {

	private ArrayList<ROfferFactory> rfactories;
	
	public PFactory(PlacementModel pm) {
		super(pm);
		rfactories = new ArrayList<>();
	}

	@Override
	public ArrayList<ROfferFactory> getROfferFactories() {
		@SuppressWarnings("unchecked")
		ArrayList<ROfferFactory> clone = (ArrayList<ROfferFactory>) rfactories.clone();
		return clone;
	}

	@Override
	public void addROfferFactory(ROfferFactory factory) {
		rfactories.add(factory);
	}

}
