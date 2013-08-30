package com.ibm.block.generator.fac.virtual.impl;

import com.ibm.block.generator.fac.virtual.intf.VLFactoryInterface;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class VLFactory extends VFactory implements VLFactoryInterface {

	public VLFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public VLink create(VMachine end1, VMachine end2) {
		VLink product = new VLink(end1, end2);
		for( RDemandFactory f : super.getRDemandFactories() ) {
			product.addResource(f.create());
		}
		for( PropertyType t : super.getPtypeList() ) {
			product.addProperty(t);
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

	@Override
	public VLink create(VMachine end1, VVolume end2) {
		VLink product = new VLink(end1, end2);
		for( RDemandFactory f : super.getRDemandFactories() ) {
			product.addResource(f.create());
		}
		for( PropertyType t : super.getPtypeList() ) {
			product.addProperty(t);
		}
		super.getPlacementModel().addEntity(product);
		return product;
	}

}
