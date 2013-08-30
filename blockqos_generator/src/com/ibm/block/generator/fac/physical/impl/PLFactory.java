package com.ibm.block.generator.fac.physical.impl;

import com.ibm.block.generator.fac.physical.intf.PLFactoryInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;

public abstract class PLFactory extends PFactory implements PLFactoryInterface {

	public PLFactory(PlacementModel pm) {
		super(pm);
	}

	@Override
	public PLink create(PMachine end1, PStorage end2) {
		PLink product = new PLink(end1, end2);
		for( ROfferFactory f : super.getROfferFactories() ) {
			product.addResource(f.create());
		}
		for( PropertyType t : super.getPtypeList() ) {
			product.addProperty(t);
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

	@Override
	public PLink create(PMachine end1, PSwitch end2) {
		PLink product = new PLink(end1, end2);
		for( ROfferFactory f : super.getROfferFactories() ) {
			product.addResource(f.create());
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

	@Override
	public PLink create(PStorage end1, PSwitch end2) {
		PLink product = new PLink(end1, end2);
		for( ROfferFactory f : super.getROfferFactories() ) {
			product.addResource(f.create());
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

	@Override
	public PLink create(PSwitch end1, PSwitch end2) {
		PLink product = new PLink(end1, end2);
		for( ROfferFactory f : super.getROfferFactories() ) {
			product.addResource(f.create());
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
