package com.ibm.block.placement.constraint.intf;

import java.util.ArrayList;

import com.ibm.block.placement.constraint.impl.ConstraintModel;

import choco.kernel.model.constraints.Constraint;

public interface ConstraintModelGenInterface {

	public void setUsesObjectiveConstraints(boolean isUsed);
	public void getUsesVLinkConstraints(boolean isUsed);
	public void getUsesPropertyConstraints(boolean isUsed);
	public void getUsesResourceConstraints(boolean isUsed);
	
	public void getUsesSimilarPropertyConstraints(boolean isUsed);
	public void getUsesStorageColocationConstraints(boolean isUsed);
	public void getUsesStorageAntiColocationConstraints(boolean isUsed);
	public void getUsesPathAntiColocationConstraints(boolean isUsed);
	
	public ConstraintModel build();

}
