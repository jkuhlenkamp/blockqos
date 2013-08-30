package com.ibm.block.placement.var.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import choco.Choco;
import choco.Options;
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
import com.ibm.block.placement.var.intf.VarModelInterface;

public class VarModel implements VarModelInterface {

	private static String perf_bw_var_name = "obj_bw";
	private static String perf_max_res_util_var_name = "obj_max_res_util";
	private static String vm_loc_var_name = "vm_loc";
	private static String vv_loc_var_name = "vv_loc";
	private static String vl_loc_on_pw_var_name = "vl_on_pw_loc";
	private static String vl_loc_on_pl_var_name = "vl_on_pl_loc";
	private static String res_post_agg_util_var_name = "ro_agg_util";
	private static String res_post_util_per_demand_var_name = "ro_single_util";
	
	private IntegerVariable obj_bw_var;
	private HashMap<ResourceType, IntegerVariable> obj_max_res_vars;
	
	private HashMap<PMachine, IntegerConstantVariable> pm_id_vars;
	private HashMap<PStorage, IntegerConstantVariable> ps_id_vars;
	private HashMap<PSwitch, IntegerConstantVariable> pw_id_vars;
	private HashMap<PLink, IntegerConstantVariable> pl_id_vars;
	
	private HashMap<VMachine, IntegerVariable> vm_loc_vars;
	private HashMap<VVolume, IntegerVariable> vv_loc_vars;
	private HashMap<VLink, SetVariable> vl_loc_on_pw_vars;	// Deprecated
	private HashMap<VLink, SetVariable> vl_loc_on_pl_vars;	// Deprecated
	
	private HashMap<ResourceDemand, IntegerConstantVariable> res_amount_vars;
	private HashMap<ResourceOffer, IntegerConstantVariable> res_capacity_vars;
	private HashMap<ResourceOffer, IntegerConstantVariable> res_pre_util_vars;
	private HashMap<ResourceOffer, IntegerVariable> res_post_agg_util_vars;
	private HashMap<ResourceOffer, HashMap<ResourceDemand, IntegerVariable>> res_post_util_per_demand_vars;
	
	
	private int[] toIntArray(Collection<Integer> c) {
		int[] a = new int[c.size()];
		int i = 0;
		for( Integer v : c ) {
			a[i] = v;
			i++;
		}
		return a;
	}
	
	public VarModel() {
		obj_bw_var = Choco.makeIntVar(perf_bw_var_name, 0, 100000);
		obj_max_res_vars = new HashMap<>();
		
		pm_id_vars = new HashMap<>();
		ps_id_vars = new HashMap<>();
		pw_id_vars = new HashMap<>();
		pl_id_vars = new HashMap<>();
		
		vm_loc_vars = new HashMap<>();
		vv_loc_vars = new HashMap<>();
		vl_loc_on_pw_vars = new HashMap<>();
		vl_loc_on_pl_vars = new HashMap<>();
		
		res_amount_vars = new HashMap<>();
		res_capacity_vars = new HashMap<>();
		res_pre_util_vars = new HashMap<>();
		res_post_agg_util_vars = new HashMap<>();
		res_post_util_per_demand_vars = new HashMap<>();
	}
	
	@Override
	public void addEntity(PMachine e) {
		pm_id_vars.put(e, new IntegerConstantVariable(e.getId()));
	}

	@Override
	public void addEntity(PStorage e) {
		ps_id_vars.put(e, new IntegerConstantVariable(e.getId()));
	}

	@Override
	public void addEntity(PSwitch e) {
		pw_id_vars.put(e, new IntegerConstantVariable(e.getId()));
	}

	@Override
	public void addEntity(PLink e) {
		pl_id_vars.put(e, new IntegerConstantVariable(e.getId()));
	}

	@Override
	public void addEntity(VMachine e, Collection<Integer> domain) {
		vm_loc_vars.put(e, Choco.makeIntVar(vm_loc_var_name+e.getId(), toIntArray(domain)));
	}

	@Override
	public void addEntity(VVolume e, Collection<Integer> domain) {
		vv_loc_vars.put(e, Choco.makeIntVar(vv_loc_var_name+e.getId(), toIntArray(domain)));
	}

	@Override
	public void addEntity(VLink e, Collection<Integer> pw_domain, Collection<Integer> pl_domain) {
		vl_loc_on_pw_vars.put(e, Choco.makeSetVar(vl_loc_on_pw_var_name+e.getId(), toIntArray(pw_domain)));
		vl_loc_on_pl_vars.put(e, Choco.makeSetVar(vl_loc_on_pl_var_name+e.getId(), toIntArray(pl_domain)));
	}

	@Override
	public IntegerConstantVariable getIdVar(PMachine e) {
		if(pm_id_vars.containsKey(e)) {
			return pm_id_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerConstantVariable getIdVar(PStorage e) {
		if(ps_id_vars.containsKey(e)) {
			return ps_id_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerConstantVariable getIdVar(PSwitch e) {
		if(pw_id_vars.containsKey(e)) {
			return pw_id_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerConstantVariable getIdVar(PLink e) {
		if(pl_id_vars.containsKey(e)) {
			return pl_id_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getLocVar(VMachine e) {
		if(vm_loc_vars.containsKey(e)) {
			return vm_loc_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getLocVar(VVolume e) {
		if(vv_loc_vars.containsKey(e)) {
			return vv_loc_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public SetVariable getLocVarOnPSwitches(VLink e) {
		if(vl_loc_on_pw_vars.containsKey(e)) {
			return vl_loc_on_pw_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public SetVariable getLocVarOnPLinks(VLink e) {
		if(vl_loc_on_pl_vars.containsKey(e)) {
			return vl_loc_on_pl_vars.get(e);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public ArrayList<IntegerConstantVariable> getPmIdVarList() {
		return new ArrayList<IntegerConstantVariable>(pm_id_vars.values());
	}

	@Override
	public ArrayList<IntegerConstantVariable> getPsIdVarList() {
		return new ArrayList<IntegerConstantVariable>(ps_id_vars.values());
	}

	@Override
	public ArrayList<IntegerConstantVariable> getPwIdVarList() {
		return new ArrayList<IntegerConstantVariable>(pw_id_vars.values());
	}

	@Override
	public ArrayList<IntegerConstantVariable> getPlIdVarList() {
		return new ArrayList<IntegerConstantVariable>(pl_id_vars.values());
	}

	@Override
	public ArrayList<IntegerVariable> getVmLocVarList() {
		return new ArrayList<IntegerVariable>(vm_loc_vars.values());
	}

	@Override
	public ArrayList<IntegerVariable> getVvLocVarList() {
		return new ArrayList<IntegerVariable>(vv_loc_vars.values());
	}

	@Override
	public ArrayList<SetVariable> getVlLocVarOnPSwitchesList() {
		return new ArrayList<SetVariable>(vl_loc_on_pw_vars.values());
	}

	@Override
	public ArrayList<SetVariable> getVlLocVarOnPLinksList() {
		return new ArrayList<SetVariable>(vl_loc_on_pl_vars.values());
	}

	@Override
	public Variable[] getVariableArray() {
		Variable[] result;
		ArrayList<Variable> l = new ArrayList<>();
		l.add(obj_bw_var);
		l.addAll(getPerfMaxUtilizationVarList());
		
		l.addAll(getPmIdVarList());
		l.addAll(getPsIdVarList());
		l.addAll(getPwIdVarList());
		l.addAll(getPlIdVarList());
		l.addAll(getVmLocVarList());
		l.addAll(getVvLocVarList());
		
		l.addAll(getResCapacityVarList());
		l.addAll(getResPreUtilizationVarList());
		l.addAll(getResPostAggUtilizationVarList());
		l.addAll(getResAmountVarList());
		l.addAll(getResPostUtilizationOfRDemandList());
		//l.addAll(getVlLocVarOnPSwitchesList());
		//l.addAll(getVlLocVarOnPLinksList());
		result = new Variable[l.size()];
		for(int i=0; i<l.size(); i++) {
			result[i] = l.get(i);
		}
		return result;
	}

	@Override
	public void addRDemand(ResourceDemand r) {
		res_amount_vars.put(r, new IntegerConstantVariable(r.getAmount()));
	}

	@Override
	public void addROffer(ResourceOffer ro, Collection<ResourceDemand> rdList, int max_agg_utilization) {
		res_capacity_vars.put(ro, new IntegerConstantVariable(ro.getCapacity()));
		res_pre_util_vars.put(ro, new IntegerConstantVariable(ro.getLeftCapacity()));
		res_post_agg_util_vars.put(ro, Choco.makeIntVar(
				res_post_agg_util_var_name+ro.getId(), 0, max_agg_utilization));
		res_post_util_per_demand_vars.put(ro, new HashMap<ResourceDemand, IntegerVariable>());
		for(ResourceDemand rd : rdList) {
			res_post_util_per_demand_vars.get(ro).put(rd, Choco.makeIntVar(
					res_post_util_per_demand_var_name+rd.getId()+"_"+ro.getId(), 
					new int[]{0, rd.getAmount()}
			));
		}
	}

	@Override
	public IntegerVariable getResAmountVar(ResourceDemand r) {
		if(res_amount_vars.containsKey(r)) {
			return res_amount_vars.get(r);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getResCapacityVar(ResourceOffer r) {
		if(res_capacity_vars.containsKey(r)) {
			return res_capacity_vars.get(r);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getResPreUtilizationVar(ResourceOffer r) {
		if(res_pre_util_vars.containsKey(r)) {
			return res_pre_util_vars.get(r);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getResPostAggUtilizationVar(ResourceOffer r) {
		if(res_post_agg_util_vars.containsKey(r)) {
			return res_post_agg_util_vars.get(r);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getResPostUtilizationOfRDemand(ResourceOffer ro,
			ResourceDemand rd) {
		if(res_post_util_per_demand_vars.containsKey(ro)) {
			if(res_post_util_per_demand_vars.get(ro).containsKey(rd)) {
				return res_post_util_per_demand_vars.get(ro).get(rd);
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public ArrayList<IntegerConstantVariable> getResAmountVarList() {
		return new ArrayList<IntegerConstantVariable>(res_amount_vars.values());
	}

	@Override
	public ArrayList<IntegerConstantVariable> getResCapacityVarList() {
		return new ArrayList<IntegerConstantVariable>(res_capacity_vars.values());
	}

	@Override
	public ArrayList<IntegerConstantVariable> getResPreUtilizationVarList() {
		return new ArrayList<IntegerConstantVariable>(res_pre_util_vars.values());
	}

	@Override
	public ArrayList<IntegerVariable> getResPostAggUtilizationVarList() {
		return new ArrayList<IntegerVariable>(res_post_agg_util_vars.values());
	}

	@Override
	public ArrayList<IntegerVariable> getResPostUtilizationOfRDemandList() {
		ArrayList<IntegerVariable> result = new ArrayList<>();
		for (HashMap<ResourceDemand, IntegerVariable> map : res_post_util_per_demand_vars.values()) {
			for(IntegerVariable var : map.values()) {
				result.add(var);
			}
		}
		return result;
	}

	@Override
	public IntegerVariable[] getResPostUtilizationVarList(
			ResourceOffer o) {
		if(res_post_util_per_demand_vars.containsKey(o)) {
			IntegerVariable[] result = new IntegerVariable[res_post_util_per_demand_vars.get(o).size()];
			int i = 0;
			for(IntegerVariable var : res_post_util_per_demand_vars.get(o).values()) {
				result[i] = var;
				i++;
			}
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public IntegerVariable getPerfBandwidthVar() {
		return obj_bw_var;
	}

	@Override
	public IntegerVariable getPerfMaxUtilizationVar(ResourceType t) {
		if(obj_max_res_vars.containsKey(t)) {
			return obj_max_res_vars.get(t);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public ArrayList<IntegerVariable> getPerfMaxUtilizationVarList() {
		return new ArrayList<IntegerVariable>(obj_max_res_vars.values());
	}

	@Override
	public void addRType(ResourceType r, int max_utilization) {
		if(r.equals(ResourceType.CORES)) {
			obj_max_res_vars.put(r, Choco.makeIntVar(perf_max_res_util_var_name, 0, max_utilization, Options.V_OBJECTIVE));
		}
		else {
			obj_max_res_vars.put(r, Choco.makeIntVar(perf_max_res_util_var_name, 0, max_utilization));
		}
		
	}

}
