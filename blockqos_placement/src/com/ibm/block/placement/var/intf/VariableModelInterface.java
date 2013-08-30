package com.ibm.block.placement.var.intf;

import java.util.HashMap;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VEntity;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedPathGroup;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.dc.impl.PEntity;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;

import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetConstantVariable;
import choco.kernel.model.variables.set.SetVariable;

public interface VariableModelInterface {
	
	// Objective function variables
	public IntegerVariable getObjectiveVar();
	
	public IntegerVariable getLoadDistributionVar();
	public IntegerVariable getScareResourceVar();
	public IntegerVariable getAggBandwidthVar();
	public IntegerVariable getAggColSwitchesVar();
	
	
	// Placement constants
	public IntegerConstantVariable getPLocationVar(PEntity pe);
	public IntegerConstantVariable getPLocationVar(PMachine pm);
	public IntegerConstantVariable getPLocationVar(PStorage ps);
	public IntegerConstantVariable getPLocationVar(PLink pl);
	public IntegerConstantVariable getPLocationVar(PSwitch pw);
	public SetConstantVariable getPOfferVar(PEntity pe);
	public SetConstantVariable getPDemandVar(VEntity ve);
	public IntegerConstantVariable getMaxCapVar(ResourceOffer offer);
	public IntegerConstantVariable getPreCapVar(ResourceOffer offer);
	public IntegerConstantVariable getPreUtilVar(ResourceOffer offer);
	public IntegerConstantVariable getAmountVar(ResourceDemand demand);
	public IntegerConstantVariable getPropertyVar(PropertyType type);
	
	// Placement variables
	public IntegerVariable getLocationVarForVMorVV(VEntity ve);
	public IntegerVariable getLocationVar(VMachine vm);
	public IntegerVariable getLocationVar(VVolume vv);
	public SetVariable getPSwitchLocationVar(VLink vl);
	public SetVariable getPLinkLocationVar(VLink vl);
	public SetVariable getAntiColocatedPathVar(AntiColocatedPathGroup group);
	public IntegerVariable getAntiColocatedPathCardinalityVar(AntiColocatedPathGroup group);
	
	public IntegerVariable getRdLocationVar(ResourceDemand rd);
	public IntegerVariable getPotRdVar(ResourceOffer offer, ResourceDemand demand);
	public IntegerVariable getAggPotRdVar(ResourceOffer offer);
	
	// Group placement variables
	public HashMap<PMachine, IntegerConstantVariable> getPmLocationVarMap();
	public HashMap<PStorage, IntegerConstantVariable> getPsLocationVarMap();
	public HashMap<PLink, IntegerConstantVariable> getPlLocationVarMap();
	public HashMap<PSwitch, IntegerConstantVariable> getPwLocationVarMap();
	
	
	public HashMap<PEntity, SetConstantVariable> getPOfferVarMap();
	public HashMap<VEntity, SetConstantVariable> getPDemandVarMap();
	
	public HashMap<ResourceOffer, IntegerConstantVariable> getMaxCapVarMap();
	public HashMap<ResourceOffer, IntegerConstantVariable> getPreCapVarMap();
	public HashMap<ResourceOffer, IntegerConstantVariable> getPreUtilVarMap();
	
	public HashMap<ResourceDemand, IntegerConstantVariable> getAmountVarMap();
	public HashMap<ResourceDemand, IntegerVariable> getRdLocationVarMap();
	public HashMap<PropertyType, IntegerConstantVariable> getPropertyVarMap();
	
	public HashMap<VMachine, IntegerVariable> getVmLocationVarMap();
	public HashMap<VVolume, IntegerVariable> getVvLocationVarMap();
	public HashMap<VLink, SetVariable> getVlLocationPwVarMap();
	public HashMap<VLink, SetVariable> getVlLocationPlVarMap();
	public HashMap<AntiColocatedPathGroup, SetVariable> getAntiColPathVarMap();
	
	
	
	// Util
	public String pretty();
}
