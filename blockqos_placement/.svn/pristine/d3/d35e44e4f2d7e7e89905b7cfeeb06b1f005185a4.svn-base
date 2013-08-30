package com.ibm.block.placement.var.impl;

import java.util.ArrayList;
import java.util.HashMap;

import choco.Choco;
import choco.Options;
import choco.kernel.model.variables.ComponentVariable;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetConstantVariable;
import choco.kernel.model.variables.set.SetVariable;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VEntity;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedPathGroup;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Entity;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PEntity;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.var.intf.VariableModelInterface;

public class VariableModel implements VariableModelInterface {
	// Variables
	private IntegerVariable objective_var;										// IntVariable that holds the overall objective (lower is better)
	private IntegerVariable obj_ld_var;											// IntVariable that holds the overall load distribution (lower is better)
	private IntegerVariable obj_scare_res_var;									// IntVariable that holds the overall scareness of resources (lower is better)
	private IntegerVariable obj_agg_bw_var;										// IntVariable that holds the overall bandwidth usage (lower is better) 
	private IntegerVariable obj_agg_col_pw_var;										// IntVariable that holds the overall colocated switches (lower is better) 
	private HashMap<VMachine, IntegerVariable> vm_loc_vars;						// IntVariable per VM
	private HashMap<VVolume, IntegerVariable> vv_loc_vars;						// IntVariable per VV
	private HashMap<VLink, SetVariable> vl_pw_loc_vars;							// SetIntVariable per VL for set of PS locations
	private HashMap<VLink, SetVariable> vl_pl_loc_vars;							// SetIntVariable per VL for set of PL locations
	private HashMap<AntiColocatedPathGroup, SetVariable> anti_col_path_vars;	// SetIntVariable per VL for set of PL locations
	private HashMap<ResourceDemand, IntegerVariable> rd_loc_vars;				// IntVariable per RD for
	private HashMap<ResourceOffer, IntegerVariable> pot_agg_rd_vars;			// IntVariable per RO & RD
	private HashMap<ResourceOffer, HashMap<
				ResourceDemand, IntegerVariable>> pot_rd_vars; 					// IntVariable per RO & RD
	// Constants
	private HashMap<PEntity, IntegerConstantVariable> pe_loc_vars;				// IntConstant per PE for PEntity.id
	private HashMap<PMachine, IntegerConstantVariable> pm_loc_vars; 			// IntConstant per PM for PMachine.id
	private HashMap<PStorage, IntegerConstantVariable> ps_loc_vars; 			// IntConstant per PS for PSwitch.id
	private HashMap<PSwitch, IntegerConstantVariable> pw_loc_vars;				// IntConstant per PW for PSwitch.id
	private HashMap<PLink, IntegerConstantVariable> pl_loc_vars;				// IntConstant per PL for PLink.id
	private HashMap<PropertyType, IntegerConstantVariable> prop_vars;			// IntConstant per PropertType for propertyId
	private HashMap<PEntity, SetConstantVariable> prop_offer_vars;				// SetIntConstant per PO for propertyIds
	private HashMap<VEntity, SetConstantVariable> prop_demand_vars;				// SetIntConstant per PD for propertyIds
	private HashMap<ResourceOffer, IntegerConstantVariable> max_capacity_vars;		// IntConstant per RO for max capacity
	private HashMap<ResourceOffer, IntegerConstantVariable> pre_capacity_vars;		// IntConstant per RO for current capacity
	private HashMap<ResourceOffer, IntegerConstantVariable> pre_utilization_vars;	// IntConstant per RO for current utilization
	private HashMap<ResourceDemand, IntegerConstantVariable> rd_amount_vars;		// IntConstant per RD for amount of demanded resources
	
	private SetConstantVariable getSetVar(Entity e) {
		int[] ids = new int[e.getPropertyMap().size()];
		Object[] properties = e.getPropertyMap().keySet().toArray();
		for( int i = 0; i < properties.length; i++) {
			ids[i] = (int) properties[i];
		}
		return new SetConstantVariable(new IntegerConstantVariable(ids.length +1), ids);
	}
	
	private String prettyVariableArray(Object[] a) {
		String s = "[";
		for( int i = 0; i < a.length; i++) {
			if(i == (a.length-1) ) {
				s += ((ComponentVariable) a[i]).pretty();
			}
			else{
				s += ((ComponentVariable) a[i]).pretty() + ", ";
			}
		}
		s += "]";
		return s;
	}
	
	public VariableModel(PlacementModel m){
		objective_var = Choco.makeIntVar("objective", 0, 100000, Options.V_OBJECTIVE);
		obj_ld_var = new IntegerVariable("obj_load_distribution", 0, 100000);
		obj_scare_res_var = new IntegerVariable("obj_scare_resource", 0, 100000);
		obj_agg_bw_var = new IntegerVariable("obj_aggregated_bandwidth", 0, 100000);
		obj_agg_col_pw_var = new IntegerVariable("obj_aggregated_col_switches", 0, 100000);
		
		pe_loc_vars = new HashMap<>();
		pm_loc_vars = new HashMap<>();
		ps_loc_vars = new HashMap<>();
		pw_loc_vars = new HashMap<>();
		pl_loc_vars = new HashMap<>();
		prop_vars = new HashMap<>();
		prop_offer_vars = new HashMap<>();
		prop_demand_vars = new HashMap<>();
		max_capacity_vars = new HashMap<>();
		pre_capacity_vars = new HashMap<>();
		pre_utilization_vars = new HashMap<>();
		rd_loc_vars = new HashMap<>();
		rd_amount_vars = new HashMap<>();
		//ve_loc_vars = new HashMap<>();
		vm_loc_vars = new HashMap<>();
		vv_loc_vars = new HashMap<>();
		anti_col_path_vars = new HashMap<>();
		vl_pw_loc_vars = new HashMap<>();
		vl_pl_loc_vars = new HashMap<>();
		pot_agg_rd_vars = new HashMap<>();
		pot_rd_vars = new HashMap<>();
		
		for(PMachine e : m.getPMachines().values()){
			pm_loc_vars.put(e, new IntegerConstantVariable(e.getId()));
			pe_loc_vars.put(e, pm_loc_vars.get(e));
			prop_offer_vars.put(e, getSetVar(e));
		}
		for(PStorage e : m.getPStorages().values()){
			ps_loc_vars.put(e, new IntegerConstantVariable(e.getId()));
			pe_loc_vars.put(e, ps_loc_vars.get(e));
			prop_offer_vars.put(e, getSetVar(e));
		}
		for(PSwitch e : m.getPSwitches().values()){
			pw_loc_vars.put(e, new IntegerConstantVariable(e.getId()));
			pe_loc_vars.put(e, pw_loc_vars.get(e));
			prop_offer_vars.put(e, getSetVar(e));
		}
		for(PLink e : m.getPLinks().values()){
			pl_loc_vars.put(e, new IntegerConstantVariable(e.getId()));
			pe_loc_vars.put(e, pl_loc_vars.get(e));
			prop_offer_vars.put(e, getSetVar(e));
		}
		for(PropertyType e : PropertyType.values()){
			prop_vars.put(e, new IntegerConstantVariable(e.getId()));
		}
		
		// VMachines
		ArrayList<Integer> pmIds = new ArrayList<>(m.getPMachines().keySet());
		for( VMachine e : m.getVMachines().values() ){
			vm_loc_vars.put(e, Choco.makeIntVar("loc_vm"+e.getId(), pmIds));
			prop_demand_vars.put(e, getSetVar(e));
		}
		
		// VVolumes
		ArrayList<Integer> psIds = new ArrayList<>(m.getPStorages().keySet());
		for( VVolume e : m.getVVolumes().values() ){
			vv_loc_vars.put(e, Choco.makeIntVar("loc_vv"+e.getId(), psIds));
			prop_demand_vars.put(e, getSetVar(e));
		}
		
		// VLinks
		for( VLink e : m.getVLinks().values() ){
			prop_demand_vars.put(e, getSetVar(e));
			// Set variable with domain of all PSwitches
			vl_pw_loc_vars.put( e, Choco.makeSetVar(
					"loc_vl"+e.getId()+"_sws", m.getPSwitchIntList()) );
			vl_pl_loc_vars.put( e, Choco.makeSetVar(
					"loc_vl"+e.getId()+"_pls", m.getPLinkIntList()) );
		}
		
		// ResourceOffers
		for(ResourceOffer e : m.getResourceOffers().values()) {
			max_capacity_vars.put( e, new IntegerConstantVariable(e.getCapacity()) );
			pre_capacity_vars.put( e, new IntegerConstantVariable(e.getLeftCapacity()) );
			pre_utilization_vars.put( e, new IntegerConstantVariable(e.getUtilization()) );
			pot_agg_rd_vars.put(e, new IntegerVariable( "agg_rd_for_ro_"+e.getId(), 0 , m.getRDemandSum(e.getType())) );
			
			HashMap<ResourceDemand, IntegerVariable> l = new HashMap<>();
			for(ResourceDemand d : m.getResourceDemands(e.getType()).values()) {
				l.put(d, new IntegerVariable("pot_rd"+d.getId()+"_for_ro" +e.getId(), new int[]{0,d.getAmount()}));
			}
			pot_rd_vars.put(e, l);
		}
		// Resource Demands
		ArrayList<Integer> peInts = m.getPEntityIntList();
		for(ResourceDemand e : m.getResourceDemands().values()) {
			rd_loc_vars.put(e, Choco.makeIntVar("loc_rd" +e.getId(), peInts));
			rd_amount_vars.put(e, new IntegerConstantVariable(e.getAmount()) );
		}
		
		// Anti Colocated Path Groups
		for( AntiColocatedPathGroup e : m.getAntiColocatedPathGroups().values() ) {
			anti_col_path_vars.put(e, Choco.makeSetVar(
					"anti_col_path_group"+e.getId()+"_sws", m.getPSwitchIntList()));
		}
	}
	
	@Override
	public IntegerConstantVariable getPLocationVar(PMachine pm) {
		if( pm_loc_vars.containsKey(pm) ) {
			return pm_loc_vars.get(pm);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"PMachine is not registered as a CHOCO variable!: " +pm);
	}

	@Override
	public IntegerConstantVariable getPLocationVar(PStorage ps) {
		if( ps_loc_vars.containsKey(ps) ) {
			return ps_loc_vars.get(ps);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"PStorage is not registered as a CHOCO variable!: " +ps);
	}

	@Override
	public IntegerConstantVariable getPLocationVar(PLink pl) {
		if( pl_loc_vars.containsKey(pl) ) {
			return pl_loc_vars.get(pl);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"PLink is not registered a CHOCO variable!: " +pl);
	}

	@Override
	public IntegerConstantVariable getPLocationVar(PSwitch pw) {
		if( pw_loc_vars.containsKey(pw) ) {
			return pw_loc_vars.get(pw);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"PSwitch is not registered as a CHOCO variable!: " +pw);
	}

	@Override
	public SetConstantVariable getPOfferVar(PEntity pe) {
		if( prop_offer_vars.containsKey(pe) ) {
			return prop_offer_vars.get(pe);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"PEntity is not registered a CHOCO set variable!: " +pe);
	}

	@Override
	public SetConstantVariable getPDemandVar(VEntity ve) {
		if( prop_demand_vars.containsKey(ve) ) {
			return prop_demand_vars.get(ve);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VEntity is not registered a CHOCO set variable!: " +ve);
	}

	@Override
	public IntegerConstantVariable getMaxCapVar(ResourceOffer offer) {
		if( max_capacity_vars.containsKey(offer) ) {
			return max_capacity_vars.get(offer);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"ResourceOffer is not registered a CHOCO variable!: " +offer);
	}

	@Override
	public IntegerConstantVariable getPreCapVar(ResourceOffer offer) {
		if( pre_capacity_vars.containsKey(offer) ) {
			return pre_capacity_vars.get(offer);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"ResourceOffer is not registered a CHOCO variable!: " +offer);
	}

	@Override
	public IntegerConstantVariable getPreUtilVar(ResourceOffer offer) {
		if( pre_utilization_vars.containsKey(offer) ) {
			return pre_utilization_vars.get(offer);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"ResourceOffer is not registered a CHOCO variable!: " +offer);
	}

	@Override
	public IntegerConstantVariable getAmountVar(ResourceDemand demand) {
		if( rd_amount_vars.containsKey(demand) ) {
			return rd_amount_vars.get(demand);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"ResourceDemand is not registered a CHOCO variable!: " +demand);
	}

	@Override
	public IntegerConstantVariable getPropertyVar(PropertyType type) {
		if( prop_vars.containsKey(type) ) {
			return prop_vars.get(type);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"PropertyType is not registered a CHOCO variable!: " +type);
	}

	@Override
	public IntegerVariable getLocationVar(VMachine vm) {
		if( vm_loc_vars.containsKey(vm) ) {
			return vm_loc_vars.get(vm);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VMachine is not registered a CHOCO variable!: " +vm);
	}

	@Override
	public IntegerVariable getLocationVar(VVolume vv) {
		if( vv_loc_vars.containsKey(vv) ) {
			return vv_loc_vars.get(vv);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VVolume is not registered a CHOCO variable!: " +vv);
	}

	@Override
	public SetVariable getPSwitchLocationVar(VLink vl) {
		if( vl_pw_loc_vars.containsKey(vl) ) {
			return vl_pw_loc_vars.get(vl);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VLink is not registered a CHOCO variable!: " +vl);
	}

	@Override
	public SetVariable getPLinkLocationVar(VLink vl) {
		if( vl_pl_loc_vars.containsKey(vl) ) {
			return vl_pl_loc_vars.get(vl);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VLink is not registered a CHOCO variable!: " +vl);
	}

	@Override
	public IntegerVariable getPotRdVar(ResourceOffer offer,
			ResourceDemand demand) {
		if( pot_rd_vars.containsKey(offer) ) {
			if( pot_rd_vars.get(offer).containsKey(demand) ) {
				return pot_rd_vars.get(offer).get(demand);
			}
		}
		throw new IllegalArgumentException("Error: The provided " +
				"ResourceOffer and ResourceDemand are not registered" +
				"as a CHOCO variable!: ResourceOffer:" +offer+ 
				", ResourceDemand:" +demand);
	}

	@Override
	public IntegerVariable getAggPotRdVar(ResourceOffer offer) {
		if( pot_agg_rd_vars.containsKey(offer) ) {
			return pot_agg_rd_vars.get(offer);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"ResourceOffer is not registered a CHOCO variable!: " +offer);
	}

	@Override
	public HashMap<PMachine, IntegerConstantVariable> getPmLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<PMachine, IntegerConstantVariable> clone = (
				HashMap<PMachine, IntegerConstantVariable>) pm_loc_vars.clone();
		return clone;
	}

	@Override
	public HashMap<PStorage, IntegerConstantVariable> getPsLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<PStorage, IntegerConstantVariable> clone = (
				HashMap<PStorage, IntegerConstantVariable>) ps_loc_vars.clone();
		return clone;
	}

	@Override
	public HashMap<PLink, IntegerConstantVariable> getPlLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<PLink, IntegerConstantVariable> clone = (
				HashMap<PLink, IntegerConstantVariable>) pl_loc_vars.clone();
		return clone;
	}

	@Override
	public HashMap<PSwitch, IntegerConstantVariable> getPwLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<PSwitch, IntegerConstantVariable> clone = (
				HashMap<PSwitch, IntegerConstantVariable>) pw_loc_vars.clone();
		return clone;
	}

	@Override
	public IntegerVariable getObjectiveVar() {
		return objective_var;
	}

	@Override
	public IntegerVariable getLoadDistributionVar() {
		return obj_ld_var;
	}

	@Override
	public IntegerVariable getScareResourceVar() {
		return obj_scare_res_var;
	}

	@Override
	public IntegerVariable getAggBandwidthVar() {
		return obj_agg_bw_var;
	}

	@Override
	public HashMap<PEntity, SetConstantVariable> getPOfferVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<PEntity, SetConstantVariable> clone = (HashMap<PEntity, 
				SetConstantVariable>) prop_offer_vars.clone();
		return clone;
	}

	@Override
	public HashMap<VEntity, SetConstantVariable> getPDemandVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<VEntity, SetConstantVariable> clone = (HashMap<VEntity, 
				SetConstantVariable>) prop_demand_vars.clone();
		return clone;
	}

	@Override
	public HashMap<ResourceOffer, IntegerConstantVariable> getMaxCapVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<ResourceOffer, IntegerConstantVariable> clone = (HashMap<
				ResourceOffer, IntegerConstantVariable>) max_capacity_vars.clone();
		return clone;
	}

	@Override
	public HashMap<ResourceOffer, IntegerConstantVariable> getPreCapVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<ResourceOffer, IntegerConstantVariable> clone = (HashMap<
				ResourceOffer, IntegerConstantVariable>) pre_capacity_vars.clone();
		return clone;
	}

	@Override
	public HashMap<ResourceOffer, IntegerConstantVariable> getPreUtilVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<ResourceOffer, IntegerConstantVariable> clone = (HashMap<
				ResourceOffer, IntegerConstantVariable>) pre_utilization_vars.clone();
		return clone;
	}

	@Override
	public HashMap<ResourceDemand, IntegerConstantVariable> getAmountVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<ResourceDemand, IntegerConstantVariable> clone = (HashMap<
				ResourceDemand, IntegerConstantVariable>) rd_amount_vars.clone();
		return clone;
	}

	@Override
	public HashMap<PropertyType, IntegerConstantVariable> getPropertyVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<PropertyType, IntegerConstantVariable> clone = (HashMap<
				PropertyType, IntegerConstantVariable>) prop_vars.clone();
		return clone;
	}

	@Override
	public HashMap<VMachine, IntegerVariable> getVmLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<VMachine, IntegerVariable> clone = (HashMap<VMachine, 
				IntegerVariable>) vm_loc_vars.clone();
		return clone;
	}

	@Override
	public HashMap<VVolume, IntegerVariable> getVvLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<VVolume, IntegerVariable> clone = (HashMap<VVolume, 
				IntegerVariable>) vv_loc_vars.clone();
		return clone;
	}

	@Override
	public HashMap<VLink, SetVariable> getVlLocationPwVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<VLink, SetVariable> clone = (HashMap<VLink, 
				SetVariable>) vl_pw_loc_vars.clone();
		return clone;
	}

	@Override
	public HashMap<VLink, SetVariable> getVlLocationPlVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<VLink, SetVariable> clone = (HashMap<VLink, 
				SetVariable>) vl_pl_loc_vars.clone();
		return clone;
	}

	@Override
	public String pretty() {
		String s ="VariableModel:[";
		s += "PmVars:" +prettyVariableArray(pm_loc_vars.values().toArray())+ ", ";
		s += "PsVars:" +prettyVariableArray(ps_loc_vars.values().toArray())+ ", ";
		s += "PwVars:" +prettyVariableArray(pw_loc_vars.values().toArray())+ ", ";
		s += "PlVars:" +prettyVariableArray(pl_loc_vars.values().toArray())+ ", ";
		s += "PropertyVars:" +prettyVariableArray(prop_vars.values().toArray())+ ", ";
		s += "PropertyOfferVars:" +prettyVariableArray(prop_offer_vars.values().toArray())+ ", ";
		s += "PropertyDemandVars:" +prettyVariableArray(prop_demand_vars.values().toArray())+ ", ";
		s += "MaxCapacityVars:" +prettyVariableArray(max_capacity_vars.values().toArray())+ ", ";
		s += "PreCapacityVars:" +prettyVariableArray(pre_capacity_vars.values().toArray())+ ", ";
		s += "PreUtilizationVars:" +prettyVariableArray(pre_utilization_vars.values().toArray())+ ", ";
		s += "AmountVars:" +prettyVariableArray(rd_amount_vars.values().toArray())+ ", ";
		s += "VmLocationVars:" +prettyVariableArray(vm_loc_vars.values().toArray())+ ", ";
		s += "VvLocationVars:" +prettyVariableArray(vv_loc_vars.values().toArray())+ ", ";
		s += "VlPSwitchLocationVars:" +prettyVariableArray(vl_pw_loc_vars.values().toArray())+ ", ";
		s += "VlPLinkLocationVars:" +prettyVariableArray(vl_pl_loc_vars.values().toArray())+ ", ";
		//s += "PotentialAggregatedRDemands:" +prettyVariableArray(pot_agg_rd_vars.values().toArray())+ ", ";
		//s += "PotentialRDemands:" +prettyVariableArray(pot_rd_vars.values().toArray())+ ", ";
		s += "]";
		return s;
	}

	@Override
	public IntegerVariable getRdLocationVar(ResourceDemand rd) {
		return rd_loc_vars.get(rd);
	}

	@Override
	public HashMap<ResourceDemand, IntegerVariable> getRdLocationVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<ResourceDemand, IntegerVariable> clone = (
				HashMap<ResourceDemand, IntegerVariable>) rd_loc_vars.clone();
		return clone;
	}

	@Override
	public IntegerConstantVariable getPLocationVar(PEntity pe) {
		if(! (pe_loc_vars.get(pe) == null) ){
			return pe_loc_vars.get(pe);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VPentity is not registered as a CHOCO variable!: ");
	}

	@Override
	public SetVariable getAntiColocatedPathVar(AntiColocatedPathGroup group) {
		if(! (anti_col_path_vars.get(group) == null) ){
			return anti_col_path_vars.get(group);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VGroup is not registered as a CHOCO set variable!: ");
	}

	@Override
	public IntegerVariable getAntiColocatedPathCardinalityVar(
			AntiColocatedPathGroup group) {
		return getAntiColocatedPathVar(group).getCard();
	}

	@Override
	public HashMap<AntiColocatedPathGroup, SetVariable> getAntiColPathVarMap() {
		@SuppressWarnings("unchecked")
		HashMap<AntiColocatedPathGroup, SetVariable> clone = (
				HashMap<AntiColocatedPathGroup, SetVariable>) anti_col_path_vars.clone();
		return clone;
	}

	@Override
	public IntegerVariable getAggColSwitchesVar() {
		return obj_agg_col_pw_var;
	}

	@Override
	public IntegerVariable getLocationVarForVMorVV(VEntity ve) {
		if( ve instanceof VMachine ) {
			return getLocationVar((VMachine) ve);
		}
		if( ve instanceof VVolume ) {
			return getLocationVar((VVolume) ve);
		}
		throw new IllegalArgumentException("Error: The provided " +
				"VEntity is not registered as a CHOCO variable!: ");
	}

}
