package com.ibm.block.placement.var.impl;

import static choco.Choco.and;
import static choco.Choco.eq;
import static choco.Choco.implies;
import static choco.Choco.isIncluded;
import static choco.Choco.not;
import static choco.Choco.ifOnlyIf;
import static choco.Choco.geq;
import static choco.Choco.ifThenElse;
import static choco.Choco.sum;
import static choco.Choco.plus;
import static choco.Choco.max;

import java.util.ArrayList;
import java.util.Collection;

import choco.Choco;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerExpressionVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetConstantVariable;
import choco.kernel.model.variables.set.SetVariable;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedStorageGroup;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PEntity;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.var.intf.ConModelBuilderInterface;

public class ConModelBuilder implements ConModelBuilderInterface {

	private boolean use_com_path_cons;
	private boolean use_res_cons;
	private boolean use_vol_col_cons;
	private PlacementModel p;
	private VarModel v;
	
	public ConModelBuilder(boolean use_com_path_cons, boolean use_res_cons, boolean use_vol_col_cons){
		this.use_com_path_cons = use_com_path_cons;
		this.use_res_cons = use_res_cons;
		this.use_vol_col_cons = use_vol_col_cons;
	}
	
	@Override
	public ConModel build(PlacementModel p, VarModel v) {
		ConModel c = new ConModel();
		this.p = p;
		this.v = v;
		
		if(use_com_path_cons) {
			c.addCommunicationPathConstraints(createComPathConstraints());
		}
		if(use_res_cons) {
			c.addResourceConstraints(createResConstraints());
		}
		if(use_vol_col_cons) {
			c.addVVolumeCollocationConstraints(createVolColConstraints());
		}
		
		return c;
	}

	private ArrayList<Constraint> createVolColConstraints() {
		ArrayList<Constraint> l = new ArrayList<>();
		Constraint c = null;
		 
		for(AntiColocatedStorageGroup g : p.getAntiColocatedStorageGroups().values()) {
			ArrayList<VVolume> vvList = new ArrayList<>(g.getMembers().values());
			for(PStorage ps : p.getPStorages().values()) {
				for(int i=1; i<vvList.size(); i++) {
					if(i==1) {								// First iteration
						c = not(
								eq(
										v.getLocVar(vvList.get(i)), 
										v.getIdVar(ps)
								)
							);
					}
					else {									// Seond and later iterations
						if(c != null) {
							c = and(
								c,
								not(
										eq(
												v.getLocVar(vvList.get(i)), 
												v.getIdVar(ps)
										)
									)
							);
						}
						else {
							throw new IllegalArgumentException();
						}
					}
				}
				if(c != null) {
					c = implies(
								eq(
										v.getLocVar(vvList.get(0)),
										v.getIdVar(ps)
								),
								c
					);
				}
				if(c != null) {
					l.add(c);
				}
			}
		}
		return l;
	}

	private ArrayList<Constraint> createResConstraints() {
		ArrayList<Constraint> l = new ArrayList<>();
		for(ResourceOffer o : p.getResourceOffers().values() ) {
			l.add(getGeqConstraint(o));					// Left capacity must be greater than the aggregated resource demand for the resource offer
			l.add(getAggResourceDemandConstraint(o));	// The aggregated demand of a resource offer is the aggregate of singel resource demands 
		}
		
		for(VMachine vm : p.getVMachines().values()) {
			for(PMachine pm : p.getPMachines().values()) {
				for(ResourceDemand rd : vm.getResourceMap().values()) {
					if(pm.hasResource(rd.getType())) {
						l.add(getResourceDemandPlacementConstraint(
								v.getLocVar(vm), 
								v.getIdVar(pm), 
								rd, 
								pm.getResource(rd.getType())
						));
					}
				}
			}
		}
		for(VVolume vv : p.getVVolumes().values()) {
			for(PStorage ps : p.getPStorages().values()) {
				for(ResourceDemand rd : vv.getResourceMap().values()) {
					if(ps.hasResource(rd.getType())) {
						l.add(getResourceDemandPlacementConstraint(
								v.getLocVar(vv), 
								v.getIdVar(ps), 
								rd, 
								ps.getResource(rd.getType())
						));
					}
				}
			}
		}
		ArrayList<PLink> notVisited = new ArrayList<>(p.getPLinks().values());
		for(VLink vl : p.getVLinks().values()) {
			VMachine vm1 = vl.getEnd1();
			for(PMachine pm1 : p.getPMachines().values()) {							// Get location var for PM1
				if(vl.getEnd2() instanceof VMachine) {
					VMachine vm2 = (VMachine) vl.getEnd2();							// Get location var for VM2
					for(PMachine pm2 : p.getPMachines().values()) {
						for(PLink pl : pm1.getPLinksOnPath(pm2).values() ) {
							if(notVisited.contains(pl)) {
								notVisited.remove(pl);
							}
							l.add(getResourceDemandPlacementConstraint(
									v.getLocVar(vm1), v.getIdVar(pm1), 
									v.getLocVar(vm2), v.getIdVar(pm2), 
									vl.getResource(ResourceType.BANDWIDTH), pl.getResource(ResourceType.BANDWIDTH))
							);
						}
					}
				}
				else {
					VVolume vv = (VVolume) vl.getEnd2();
					for(PStorage ps : p.getPStorages().values()) {
						for(PLink pl : pm1.getPLinksOnPath(ps).values() ) {
							if(notVisited.contains(pl)) {
								notVisited.remove(pl);
							}
							l.add(getResourceDemandPlacementConstraint(
									v.getLocVar(vm1), v.getIdVar(pm1), 
									v.getLocVar(vv), v.getIdVar(ps), 
									vl.getResource(ResourceType.BANDWIDTH), pl.getResource(ResourceType.BANDWIDTH))
							);
						}
					}
				}
			}
		}
		for(PLink pl : notVisited) {
			for(VLink vl : p.getVLinks().values()) {
				l.add(eq(
						v.getResPostUtilizationOfRDemand(
								pl.getResource(ResourceType.BANDWIDTH), 
								vl.getResource(ResourceType.BANDWIDTH)), 
						0));
			}
		}
		ArrayList<ResourceOffer> roList = new ArrayList<>();
		for(ResourceOffer ro : p.getResourceOffers().values()) {
			if(ro.getType().equals(ResourceType.BANDWIDTH)) {
				roList.add(ro);
			}
		}
		IntegerVariable[] bwVarList = new IntegerVariable[roList.size()];
		for(int i=0; i<roList.size(); i++) {
			bwVarList[i] = v.getResPostAggUtilizationVar(roList.get(i));
		}
		l.add(	eq(
					v.getPerfBandwidthVar(), 
					sum(bwVarList)
				)
		);
		
		// Max resource utilization
		for(ResourceType t : ResourceType.values()) {
			Constraint c = null;
			ArrayList<IntegerExpressionVariable> ro_var_list = new ArrayList<>();
			for(ResourceOffer ro : p.getResourceOffers().values()) {
				if(ro.getType().equals(t)) {
					ro_var_list.add(plus(
							ro.getUsage(), 
							v.getResPostAggUtilizationVar(ro)
					));
				}
			}
			IntegerExpressionVariable[] ro_var_array = new IntegerExpressionVariable[ro_var_list.size()];
			for(int i=0; i<ro_var_list.size(); i++) {
				ro_var_array[i] = ro_var_list.get(i);
			}
			c = eq(
					v.getPerfMaxUtilizationVar(t), 
					max(ro_var_array)
			);
			if(c != null) {
				l.add(c);
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		
		return l;
	}
	
	private Constraint getAggResourceDemandConstraint(ResourceOffer o) {
		Constraint c = eq(
				v.getResPostAggUtilizationVar(o),
				sum(v.getResPostUtilizationVarList(o))
		);
		if(c != null) {
			return c;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private Constraint getResourceDemandPlacementConstraint(
			IntegerVariable ve1_loc, IntegerConstantVariable pe1_loc,
			IntegerVariable ve2_loc, IntegerConstantVariable pe2_loc,
			ResourceDemand rd, ResourceOffer ro) {
		Constraint c = implies(								// Quickfix
						and(
								eq(ve1_loc, pe1_loc),
								eq(ve2_loc, pe2_loc)
						),
						eq(
								v.getResPostUtilizationOfRDemand(ro, rd), 
								v.getResAmountVar(rd)
						/*
						),
						eq(
								v.getResPostUtilizationOfRDemand(ro, rd), 
								0
						*/
						)
		);
		if(c != null) {
			return c;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private Constraint getResourceDemandPlacementConstraint(
			IntegerVariable ve_loc, IntegerConstantVariable pe_loc, 
			ResourceDemand rd, ResourceOffer ro) {
		Constraint c = ifThenElse(
				eq(ve_loc, pe_loc),
				eq(v.getResPostUtilizationOfRDemand(ro, rd), v.getResAmountVar(rd)),
				eq(v.getResPostUtilizationOfRDemand(ro, rd), 0)
		);
		if(c != null) {
			return c;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private Constraint getGeqConstraint(ResourceOffer r) {
		Constraint c = geq(
								v.getResPreUtilizationVar(r),
								v.getResPostAggUtilizationVar(r)
						);
		if(c != null) {
			return c;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private ArrayList<Constraint> createComPathConstraints() {
		ArrayList<Constraint> l = new ArrayList<>();
		
		for(VVolume vv : p.getVVolumes().values()) {
			for(PStorage ps :p.getPStorages().values()) {
				PMachine pm = ps.getConnectedPMachine();
				if(pm != null) {
					l.add(getEqImpliesEqConstraint(vv, ps, vv.getVMachine(), pm));
				}
			}
		}
		
		/*
		for(PMachine pm : p.getPMachines().values()) {
			for(PStorage ps : pm.getConnectedPStorages().values()) {
				for(VVolume vv : p.getVVolumes().values()) {
					for(VMachine vm : p.getVMachines().values()) {
						l.add(getEqImpliesEqConstraint(vv, ps, vm, pm));
					}
				}
			}
		}
		*/
		
		/*
		IntegerConstantVariable pe1_loc;
		IntegerConstantVariable pe2_loc;
		SetConstantVariable 	subset;
		IntegerVariable 		ve1_loc;
		IntegerVariable			ve2_loc;
		SetVariable 			superset;
		
		for(VLink vl : p.getVLinks().values()) {
			VMachine vm1 = vl.getEnd1();
			ve1_loc = v.getLocVar(vm1);									// Get location var for VM1
			//ArrayList<PMachine> visited = new ArrayList<>();
			for(PMachine pm1 : p.getPMachines().values()) {
				pe1_loc = v.getIdVar(pm1);								// Get location var for PM1
				if(vl.getEnd2() instanceof VMachine) {
					VMachine ve2 = (VMachine) vl.getEnd2();
					ve2_loc = v.getLocVar(ve2);							// Get location var for VM2
					for(PMachine pe2 : p.getPMachines().values()) {
						pe2_loc = v.getIdVar(pe2);						// Get location var for PM2
						// Place VL on PSwitches
						
						subset = getPSwitchesOnPathVar(pm1, pe2);		// Get PSwitches on path from PM1 to PM2
						superset = v.getLocVarOnPSwitches(vl);			// Get location on PSwitches for VL
						if(subset != null) {
							l.add(getEqAndEqImpliesEqCon(
									ve1_loc, pe1_loc, 
									ve2_loc, pe2_loc,
									subset, superset));
						}
						else {
							throw new IllegalArgumentException();		// Should not happen for Tree Network DCs
						}
						// Place VL on PLinks
						subset = getPLinksOnPathVar(pm1, pe2);			// Get PLinks on path from PM1 to PM2
						superset = v.getLocVarOnPLinks(vl);				// Get location on PLinks for VL
						
						l.add(getEqAndEqImpliesEqCon(
								ve1_loc, pe1_loc, 
								ve2_loc, pe2_loc,
								subset, superset));
						
					}
				}
				else {
					VVolume ve2 = (VVolume) vl.getEnd2();
					ve2_loc = v.getLocVar(ve2);							// Get location var for VV
					for(PStorage pe2 : p.getPStorages().values()) {
						pe2_loc = v.getIdVar(pe2);						// Get location var for PS
						// Place VL on PSwitches
						subset = getPSwitchesOnPathVar(pm1, pe2);		// Get PSwitches on path from PM to PS
						superset = v.getLocVarOnPSwitches(vl);			// Get location on PSwitches for VL
						if(subset != null) {
							l.add(getEqAndEqImpliesEqCon(
									ve1_loc, pe1_loc, 
									ve2_loc, pe2_loc,
									subset, superset));
						}
						else {
							l.add(getNotEqAndEqCon(						// Do not place pair if no connection
									ve1_loc, pe1_loc, 
									ve2_loc, pe2_loc));
						}
						// Place VL on PLinks
						subset = getPLinksOnPathVar(pm1, pe2);			// Get PLinks on path from PM to PS
						superset = v.getLocVarOnPLinks(vl);				// Get location on PLinks for VL
						
						l.add(getEqAndEqImpliesEqCon(
								ve1_loc, pe1_loc, 
								ve2_loc, pe2_loc,
								subset, superset));
						
					}
				}
			}
		}
		*/
		return l;
	}
	
	private Constraint getEqImpliesEqConstraint(VVolume vv, PStorage ps,
			VMachine vm, PMachine pm) {
		Constraint c = implies(
									eq(
											v.getLocVar(vv),
											v.getIdVar(ps)
									), 
									eq(
											v.getLocVar(vm),
											v.getIdVar(pm)
									)
						);
		if(c != null) {
			return c;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private void logConstraints(ArrayList<Constraint> l) {
		for(Constraint c : l) {
			System.out.println(c.pretty());
		}
	}
	
	private int[] toIntArray(Collection<Integer> c) {
		int[] a = new int[c.size()];
		int i = 0;
		for( Integer v : c ) {
			a[i] = v;
			i++;
		}
		return a;
	}
	
	private Constraint getEqAndEqImpliesEqCon(
			IntegerVariable ve1, IntegerConstantVariable pe1, 
			IntegerVariable ve2, IntegerConstantVariable pe2,
			SetConstantVariable subset, SetVariable superset) {
		Constraint result = ifOnlyIf(
								and(
										eq(ve1, pe1),
										eq(ve2, pe2)
								),
								eq(subset, superset)
							);
		if(result != null) {
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private Constraint getNotEqAndEqCon(
			IntegerVariable ve1, IntegerConstantVariable pe1, 
			IntegerVariable ve2, IntegerConstantVariable pe2) {
		Constraint result = not(
								and(
										eq(ve1, pe1),
										eq(ve2, pe2)
								)
							);
		if(result != null) {
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	@SuppressWarnings("unused")
	private SetConstantVariable getPSwitchesOnPathVar(PMachine pe1, PEntity pe2) {
		int[] ints = null;
		SetConstantVariable result;
		
		if(pe2 instanceof PMachine){
			ints = toIntArray(pe1.getPSwitchesOnPath((PMachine) pe2).keySet());
		}
		else {
			if(pe2 instanceof PStorage) {
				ints = toIntArray(pe1.getPSwitchesOnPath((PStorage) pe2).keySet());
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		result = new SetConstantVariable(new IntegerConstantVariable(ints.length), ints);
		
		//System.out.println("Found PSwitches on path:" +prettyArray(ints)+ ", (" +pe1.getName()+ ", " +pe2.getName()+ ")");
		if(result != null) {
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private SetConstantVariable getPLinksOnPathVar(PMachine pe1, PEntity pe2) {
		int[] ints = null;
		SetConstantVariable result;
		
		if(pe2 instanceof PMachine){
			ints = toIntArray(pe1.getPLinksOnPath((PMachine) pe2).keySet());
		}
		else {
			if(pe2 instanceof PStorage) {
				ints = toIntArray(pe1.getPLinksOnPath((PStorage) pe2).keySet());
			}
			else {
				throw new IllegalArgumentException();
			}
		}
		result = new SetConstantVariable(new IntegerConstantVariable(ints.length), ints);
		System.out.println("Found PLinks on path:" +prettyArray(ints)+ ", (" +pe1.getName()+ ", " +pe2.getName()+ ")");
		if(result != null) {
			return result;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private String prettyArray(int[] a) {
		String s = "";
		for(int i=0; i<a.length; i++) {
			s += a[i]+ " ";
		}
		return s;
	}
}
