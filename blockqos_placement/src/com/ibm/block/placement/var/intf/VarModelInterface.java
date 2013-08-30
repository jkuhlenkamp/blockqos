package com.ibm.block.placement.var.intf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetVariable;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;

public interface VarModelInterface {

	// Add Variables
	public void addEntity(PMachine e);
	public void addEntity(PStorage e);
	public void addEntity(PSwitch e);
	public void addEntity(PLink e);
	public void addEntity(VMachine e, Collection<Integer> domain);
	public void addEntity(VVolume e, Collection<Integer> domain);		// Deprecated
	public void addEntity(VLink e, Collection<Integer> pw_domain, Collection<Integer> pl_domain);	// Deprecated
	
	public void addRDemand(ResourceDemand r);
	public void addROffer(ResourceOffer ro, Collection<ResourceDemand> rdList, int max_agg_utilization);
	public void addRType(ResourceType r, int max_utilization);
	//public void addRDemandOnROffer(ResourceDemand rd, ResourceOffer ro);
	
	// Variable Getters
	public IntegerConstantVariable getIdVar(PMachine e);
	public IntegerConstantVariable getIdVar(PStorage e);
	public IntegerConstantVariable getIdVar(PSwitch e);
	public IntegerConstantVariable getIdVar(PLink e);
	public IntegerVariable getLocVar(VMachine e);
	public IntegerVariable getLocVar(VVolume e);
	public SetVariable getLocVarOnPSwitches(VLink e);		// Deprecated
	public SetVariable getLocVarOnPLinks(VLink e);			// Deprecated
	
	public IntegerVariable getResAmountVar(ResourceDemand r);
	public IntegerVariable getResCapacityVar(ResourceOffer r);
	public IntegerVariable getResPreUtilizationVar(ResourceOffer r);
	public IntegerVariable getResPostAggUtilizationVar(ResourceOffer r);
	public IntegerVariable getResPostUtilizationOfRDemand(ResourceOffer ro, ResourceDemand rd);
	
	public IntegerVariable getPerfBandwidthVar();
	public IntegerVariable getPerfMaxUtilizationVar(ResourceType r);
	
	// Variable List Getters
	public ArrayList<IntegerVariable> getPerfMaxUtilizationVarList();
	
	public ArrayList<IntegerConstantVariable> getPmIdVarList();
	public ArrayList<IntegerConstantVariable> getPsIdVarList();
	public ArrayList<IntegerConstantVariable> getPwIdVarList();
	public ArrayList<IntegerConstantVariable> getPlIdVarList();
	public ArrayList<IntegerVariable> getVmLocVarList();
	public ArrayList<IntegerVariable> getVvLocVarList();
	public ArrayList<SetVariable> getVlLocVarOnPSwitchesList();	// Deprecated
	public ArrayList<SetVariable> getVlLocVarOnPLinksList();	// Deprecated
	
	public ArrayList<IntegerConstantVariable> getResAmountVarList();
	public ArrayList<IntegerConstantVariable> getResCapacityVarList();
	public ArrayList<IntegerConstantVariable> getResPreUtilizationVarList();
	public ArrayList<IntegerVariable> getResPostAggUtilizationVarList();
	public ArrayList<IntegerVariable> getResPostUtilizationOfRDemandList();
	public IntegerVariable[] getResPostUtilizationVarList(ResourceOffer o);
	
	public Variable[] getVariableArray();
}
