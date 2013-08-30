package com.ibm.block.generator.impl;

import com.ibm.block.generator.intf.GeneratorInterface;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class Generator implements GeneratorInterface {

	private PlacementModel model;
	
	public Generator(PlacementModel model) {
		this.model = model;
	}
	
	protected PlacementModel getModel() {
		return model;
	}
}
