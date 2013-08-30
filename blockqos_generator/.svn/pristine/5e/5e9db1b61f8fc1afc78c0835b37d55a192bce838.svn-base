package com.ibm.block.generator.fac.physical.impl.ps;

import com.ibm.block.generator.fac.physical.impl.PSFactory;
import com.ibm.block.generator.fac.physical.impl.ro.SmallStorageROfferFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallPSFactory extends PSFactory {

	public SmallPSFactory(PlacementModel pm) {
		super(pm);
		super.addROfferFactory(new SmallStorageROfferFactory(pm));
		super.addPtype(PropertyType.STORAGE_EXCLUSIVE);
		super.addPtype(PropertyType.STORAGE_SSD);
	}

}
