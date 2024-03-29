package com.ibm.block.generator.fac.physical.impl.ps;

import com.ibm.block.generator.fac.physical.impl.PSFactory;
import com.ibm.block.generator.fac.physical.impl.ro.LargeStorageROfferFactory;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public class LargePSFactory extends PSFactory {

	public LargePSFactory(PlacementModel pm) {
		super( pm );
		super.addROfferFactory(new LargeStorageROfferFactory(pm));
		super.addPtype(PropertyType.STORAGE_SHARED);
		super.addPtype(PropertyType.STORAGE_HDD);
		super.addPtype(PropertyType.STORAGE_SSD);
	}
}
