package com.ibm.block.generator.intf;

import com.ibm.block.generator.fac.virtual.impl.vg.AntiColocatedPathGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.AntiColocatedStorageGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.ColocatedStorageGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.ReplicaGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.SamePropertyGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vl.LargeVLFactory;
import com.ibm.block.generator.fac.virtual.impl.vl.SmallVLFactory;
import com.ibm.block.generator.fac.virtual.impl.vm.LargeVMFactory;
import com.ibm.block.generator.fac.virtual.impl.vm.SmallVMFactory;
import com.ibm.block.generator.fac.virtual.impl.vv.LargeVVFactory;
import com.ibm.block.generator.fac.virtual.impl.vv.SmallVVFactory;

public interface ThreeTierAppGenInterface {

	// Getters for app configuration
	public int getT1VMCount();
	public int getT1VVperVMCount();
	public int getT2VMCount();
	public int getT2VVperVMCount();
	public int getT3VMCount();
	public int getT3VVperVMCount();
	
	public int getT1ReplicaCount();
	public int getT2ReplicaCount();
	public int getT3ReplicaCount();
	
	public boolean hasAntiColocatedPath();
	public boolean hasAntiColocatedStorage();
	public boolean hasColocatedStorage();
	public boolean hasReplica();
	public boolean hasSameProperty();
	
	// Getters for factories
	public LargeVMFactory getLVMFactory();
	public SmallVMFactory getSVMFactory();
	
	public LargeVVFactory getLVVFactory();
	public SmallVVFactory getSVVFactory();
	
	public LargeVLFactory getLVLFactory();
	public SmallVLFactory getSVlFactory();
	
	public ReplicaGroupFactory getRepGroupFactory();
	public SamePropertyGroupFactory getSamepropGroupFactory();
	public ColocatedStorageGroupFactory getColstorageGroupFactory();
	public AntiColocatedStorageGroupFactory getAnticolstorageGroupFactory();
	public AntiColocatedPathGroupFactory getAnticolpathGroupFactory();
	
}
