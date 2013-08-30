package com.ibm.block.placement.constraint.impl;

import static choco.Choco.and;
import static choco.Choco.eq;
import static choco.Choco.geq;
import static choco.Choco.ifThenElse;
import static choco.Choco.implies;
import static choco.Choco.isIncluded;
import static choco.Choco.member;
import static choco.Choco.neq;
import static choco.Choco.plus;
import static choco.Choco.setInter;
import static choco.Choco.sum;

import java.util.ArrayList;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedPathGroup;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedStorageGroup;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.constraint.intf.ConstraintModelGenInterface;
import com.ibm.block.placement.var.impl.VariableModel;

public class ConstraintModelGen implements ConstraintModelGenInterface {

	private ConstraintModel cmodel;
	private PlacementModel pmodel;
	private VariableModel vmodel;
	
	private boolean usesObjectiveConstraints;
	private boolean usesVLinkConstraints;
	private boolean usesPropertyConstraints;
	private boolean usesResourceConstraints;
	private boolean usesSimilarPropertyConstraints;
	private boolean usesStorageColocationConstraints;
	private boolean usesStorageAntiColocationConstraints;
	private boolean usesPathAntiColocationConstraints;
	
	private int[] toIntArray(Object[] c) {
		int[] result = new int[c.length];
		for( int i = 0; i < c.length; i++ ) {
			result[i] = (int) c[i];
		}
		return result;
	}
	
	public ConstraintModelGen(PlacementModel pmodel, VariableModel vmodel) {
		this.pmodel = pmodel;
		this.vmodel = vmodel;
		
		usesObjectiveConstraints = true;
		usesVLinkConstraints = true;
		usesPropertyConstraints = false;
		usesResourceConstraints = true;
		usesSimilarPropertyConstraints = false;
		usesStorageColocationConstraints = false;
		usesStorageAntiColocationConstraints = false;
		usesPathAntiColocationConstraints = false;
	}
	
	@Override
	public void setUsesObjectiveConstraints(boolean isUsed) {
		this.usesObjectiveConstraints = isUsed;
	}

	@Override
	public void getUsesVLinkConstraints(boolean isUsed) {
		this.usesVLinkConstraints = isUsed;
	}

	@Override
	public void getUsesPropertyConstraints(boolean isUsed) {
		this.usesPropertyConstraints = isUsed;
	}

	@Override
	public void getUsesResourceConstraints(boolean isUsed) {
		this.usesResourceConstraints = isUsed;
	}

	@Override
	public void getUsesSimilarPropertyConstraints(boolean isUsed) {
		this.usesSimilarPropertyConstraints = isUsed;
	}

	@Override
	public void getUsesStorageColocationConstraints(boolean isUsed) {
		this.usesStorageColocationConstraints = isUsed;
	}

	@Override
	public void getUsesStorageAntiColocationConstraints(boolean isUsed) {
		this.usesStorageAntiColocationConstraints = isUsed;
	}

	@Override
	public void getUsesPathAntiColocationConstraints(boolean isUsed) {
		this.usesPathAntiColocationConstraints = isUsed;
	}

	@Override
	public ConstraintModel build() {
		cmodel = new ConstraintModel();
		
		if( usesObjectiveConstraints ) {
			ArrayList<Constraint> constraints = createObjectiveConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if( usesVLinkConstraints ) {
			ArrayList<Constraint> constraints = createVLinkConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if(usesPropertyConstraints) {
			ArrayList<Constraint> constraints = createPropertyConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if(usesResourceConstraints) {
			ArrayList<Constraint> constraints = createResourceConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if(usesSimilarPropertyConstraints) {
			ArrayList<Constraint> constraints = createSimilarPropertyConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if(usesStorageColocationConstraints) {
			ArrayList<Constraint> constraints = createStorageColocationConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if(usesStorageAntiColocationConstraints) {
			ArrayList<Constraint> constraints = createStorageAntiColocationConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		if(usesPathAntiColocationConstraints) {
			ArrayList<Constraint> constraints = createPathAntiColocationConstraints();
			for( Constraint c : constraints ) {
				cmodel.addObjectiveConstraint(c);
			}
		}
		
		return cmodel;
	}

	private ArrayList<Constraint> createPathAntiColocationConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		
		// Aggregate cardinality of collocated switch sets for objective variable
		IntegerVariable[] card_varList = new IntegerVariable[pmodel.getAntiColocatedPathGroups().values().size()];
		int i = 0;
		for( AntiColocatedPathGroup group : pmodel.getAntiColocatedPathGroups().values() ) {
			card_varList[i] = vmodel.getAntiColocatedPathCardinalityVar(group);
			i++;
		}
		Constraint c = null;
		c = eq(
				vmodel.getAggColSwitchesVar(), 
				sum(card_varList)
			);
		if( c != null ) {
			result.add(c);
		}
		
		for( AntiColocatedPathGroup group : pmodel.getAntiColocatedPathGroups().values() ) {
			// specify a variable that is the intersection of all switch members
			ArrayList<VVolume> vvList = new ArrayList<>(group.getMembers().values());
			c = null;
			for(i = 0; i < (vvList.size()-1); i++) {
				if( i == 0 ) {										// First iteration
					c = setInter(
								vmodel.getPSwitchLocationVar( vvList.get(i).getVLink() ), 
								vmodel.getPSwitchLocationVar( vvList.get(i+1).getVLink() ), 
								vmodel.getAntiColocatedPathVar(group)
							);
				}
				else {												// Is NOT first iteration
					c = and(
							c, 
							setInter(
									vmodel.getPSwitchLocationVar( vvList.get(i).getVLink() ), 
									vmodel.getPSwitchLocationVar( vvList.get(i+1).getVLink() ), 
									vmodel.getAntiColocatedPathVar(group)
									)
							);
					
				}
			}
			if( c != null ) {
				result.add(c);
			}
			else {
				// Throw some exception that constraint extraction for VGroup failed
			}
		}
		
		return result;
	}

	private ArrayList<Constraint> createStorageAntiColocationConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();

		for( AntiColocatedStorageGroup g : pmodel.getAntiColocatedStorageGroups().values() ) { 	// Iterate over all VGroups that represent anti-collocation demands of virtual volumes (VV)
			Constraint c = null;
			ArrayList<VVolume> vvList = new ArrayList<>( g.getMembers().values() );
			for( int i = 0; i < (vvList.size()-1); i++ ) {
				if( i == 0 ) {
					c = neq(
							vmodel.getLocationVar(vvList.get(i)), 
							vmodel.getLocationVar(vvList.get(i+1))
						);
				}
				else {
					if( c != null ) {
						c = and(
								c, 
								neq(
										vmodel.getLocationVar(vvList.get(i)), 
										vmodel.getLocationVar(vvList.get(i+1))
									)
								);
					}
					else {
						// Throw some exception that first extraction failed
					}
				}
			}
			if( c != null ) {
				result.add(c);
			}
			else {
				// Throw some exception that constraint extraction for VGroup failed
			}
		}
		return result;
	}

	private ArrayList<Constraint> createStorageColocationConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		return result;
	}

	private ArrayList<Constraint> createSimilarPropertyConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		return result;
	}

	/**
	 * Creating resource demand (RD) constraints for all RDs of each Virtual Machine (VM)
	 * @return
	 */
	private ArrayList<Constraint> createResourceConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();

		//////////////////////////////////////////////////////////////
		// VIRTUAL Machine (VM)
		//////////////////////////////////////////////////////////////
		
		for( VMachine vm : pmodel.getVMachines().values() ) {
			// All RDs of a VM must be placed on the same Physical Machine (PM) as the VM
			ArrayList<IntegerVariable> loc_vars = new ArrayList<>();	// Collect all location variables (loc_var) for the VM and all RDs of the VM
			loc_vars.add( vmodel.getLocationVar(vm) );					// Add loc_var of VM
			for( ResourceDemand rd : vm.getResourceMap().values() ) {	// Iterate over all RDemands for the VM
				loc_vars.add( vmodel.getRdLocationVar(rd) ); 			// Add loc_var of RD
				
				// Potential RDs equal the amount of the RD if the VM is placed on the PM of the RO
				for( PMachine pm : pmodel.getPMachines().values() ) {			// Iterate over all PMs
					for( ResourceOffer ro : pm.getResourceMap().values() ) { 	// Iterate over all ROs of the PM
						if( rd.getType().equals(ro.getType()) ) {				// Check if the RO matches the RD
							Constraint c = null; 
							c = ifThenElse(										
									eq(											
											vmodel.getLocationVar(vm), 
											vmodel.getPLocationVar(pm)
									), 
									eq(											// If VM is placed on PM: potential RD equals RD.amount
											vmodel.getPotRdVar(ro, rd), 
											vmodel.getAmountVar(rd)
									),
									eq(											// If VM is NOT placed on PM: potential RD equals 0
											vmodel.getPotRdVar(ro, rd),
											0
									)
								);
							
							if( c != null ) {
								result.add(c);
							}
							else {
								// throw some exception
							}
						}
					}
				}
			}
			// Create CHOCO constraints
			Constraint c = null;
			for( int i = 0; i < ( loc_vars.size()-1); i++ ) {			// Iterate over loc_vars except last element
				if( i == 0 ) {											// If first iteration
					c = eq(
							loc_vars.get(i), 
							loc_vars.get(i+1)
						);
				}
				else {													// If NOT first iteration
					c = and(
							c, 
							eq(
									loc_vars.get(i), 
									loc_vars.get(i+1)
							) 
						);
				}
				if( c != null ) {
					result.add(c);
				}
				else {
					// Throw some Exception
				}
			}
		}
		
		//////////////////////////////////////////////////////////////
		// VIRTUAL VOLUME (VV)
		//////////////////////////////////////////////////////////////
		
		for( VVolume vv : pmodel.getVVolumes().values() ) {
			// All RDs of a VV must be placed on the same Physical Storage (PS) as the VV
			ArrayList<IntegerVariable> loc_vars = new ArrayList<>();	// Collect all location variables (loc_var): one for the VM and one for all RDs of the VV
			loc_vars.add( vmodel.getLocationVar(vv) );					// Add loc_var of VV
			for( ResourceDemand rd : vv.getResourceMap().values() ) {	// Iterate over all RDemands for the VV
				loc_vars.add( vmodel.getRdLocationVar(rd) ); 			// Add loc_var of RD
				
				// Potential RDs equal the amount of the RD if the VV is placed on the PS of the RO
				for( PStorage ps : pmodel.getPStorages().values() ) {			// Iterate over all PSs
					for( ResourceOffer ro : ps.getResourceMap().values() ) { 	// Iterate over all ROs of the PS
						if( rd.getType().equals(ro.getType()) ) {				// Check if the RO matches the RD
							Constraint c = null; 
							c = ifThenElse(										
									eq(											
											vmodel.getLocationVar(vv), 
											vmodel.getPLocationVar(ps)
									), 
									eq(											// If VV is placed on PS: potential RD equals RD.amount
											vmodel.getPotRdVar(ro, rd), 
											vmodel.getAmountVar(rd)
									),
									eq(											// If VS is NOT placed on PS: potential RD equals 0
											vmodel.getPotRdVar(ro, rd),
											0
									)
								);
							
							if( c != null ) {
								result.add(c);
							}
							else {
								// throw some exception
							}
						}
					}
				}
			}
			// Create CHOCO constraints
			Constraint c = null;
			for( int i = 0; i < ( loc_vars.size()-1); i++ ) {			// Iterate over loc_vars except last element
				if( i == 0 ) {											// If first iteration
					c = eq(
							loc_vars.get(i), 
							loc_vars.get(i+1)
						);
				}
				else {													// If NOT first iteration
					c = and(
							c, 
							eq(
									loc_vars.get(i), 
									loc_vars.get(i+1)
							) 
						);
				}
				if( c != null ) {
					result.add(c);
				}
				else {
					// Throw some Exception
				}
			}
		}
		
		//////////////////////////////////////////////////////////////
		// VIRTUAL LINK (VL)
		//////////////////////////////////////////////////////////////
		
		for( VLink vl : pmodel.getVLinks().values() ) {
			//SetVariable vl_pw_loc_var = vmodel.getPSwitchLocationVar(vl);		// Get the set variable (vl_pw_loc_var) for a VL that describes the location of the VL in terms of PSwitches
			//SetVariable vl_pl_loc_var = vmodel.getPLinkLocationVar(vl);			// Get the set variable (vl_pl_loc_var) for a VL that describes the location of the VL in terms of PLinks
		
			for( ResourceDemand rd : vl.getResourceMap().values() ) {			// Iterate over all resource demands (RDs) of the VL
				for( PLink pl : pmodel.getPLinks().values() ) {					// Iterate over all physical links (PLs)
					for( ResourceOffer ro : pl.getResourceMap().values() ) {	// Iterate over all resource offers (ROs) of the PL
						if( rd.getType().equals(ro.getType()) ) {				// Check if the type of the RO equals the type of the RD 
							Constraint c = null; 
							c = ifThenElse(										
									member(											
											vmodel.getPLocationVar(pl), 
											vmodel.getPLinkLocationVar(vl)
									), 
									eq(											// If VL is placed on PL: potential RD equals RD.amount
											vmodel.getPotRdVar(ro, rd), 
											vmodel.getAmountVar(rd)
									),
									eq(											// If VL is NOT placed on PL: potential RD equals 0
											vmodel.getPotRdVar(ro, rd),
											0
									)
								);
							
							if( c != null ) {
								result.add(c);
							}
							else {
								// throw some exception
							}
						}
					}
				}
				// Iterate over physical switches (PWs)
				for( PSwitch pw : pmodel.getPSwitches().values() ) {			// Iterate over all physical switches (PWs)
					for( ResourceOffer ro : pw.getResourceMap().values() ) {	// Iterate over all resource offers (ROs) of the PL
						if( rd.getType().equals(ro.getType()) ) {				// Check if the type of the RO equals the type of the RD 
							Constraint c = null; 
							c = ifThenElse(										
									member(											
											vmodel.getPLocationVar(pw), 
											vmodel.getPLinkLocationVar(vl)
									), 
									eq(											// If VL is placed on PW: potential RD equals RD.amount
											vmodel.getPotRdVar(ro, rd), 
											vmodel.getAmountVar(rd)
									),
									eq(											// If VL is NOT placed on PW: potential RD equals 0
											vmodel.getPotRdVar(ro, rd),
											0
									)
								);
							
							if( c != null ) {
								result.add(c);
							}
							else {
								// throw some exception
							}
						}
					}
				}
			}
		}
		
		//////////////////////////////////////////////////////////////
		// AGGREGATED POTENTIAL RESOURCE USAGES (APRU)
		//////////////////////////////////////////////////////////////
		
		for( ResourceOffer ro : pmodel.getResourceOffers().values() ) {				// Iterate over all resource offers (ROs)
			ArrayList<IntegerVariable> pot_res_usage_vars = new ArrayList<>();		
			for( ResourceDemand rd : pmodel.getResourceDemands().values() ) {		// Iterate over all resource demands (RDs)
				if( ro.getType().equals(rd.getType()) ) {							// Check if the type of the RO matches the type of the RD
					pot_res_usage_vars.add( vmodel.getPotRdVar(ro, rd) );			// Get the potential resource usage variable for the RD and RO
				}
			}
			IntegerVariable[] a = new IntegerVariable[pot_res_usage_vars.size()];	// Transform IntegerVariable ArrayList to native Array to use CHOCO constructor
			for( int i = 0; i < pot_res_usage_vars.size(); i++ ) {
				a[i] = pot_res_usage_vars.get(i);
			}
			
			// Create CHOCO constraint: APRU is the sum of potential RDs for a RO 
			Constraint c = null;
			c = eq(
					vmodel.getAggPotRdVar(ro), 
					sum(a)
				);
			if( c != null ) {
				result.add(c);
			}
			
			// Create CHOCO costraint: The left capacity for a RO must be greate or equal to the APRU
			c = null;
			c = geq(
					vmodel.getPreCapVar(ro), 
					vmodel.getAggPotRdVar(ro)
				);
			if( c != null ) {
				result.add(c);
			}
		}
		
		/**
		// All resourceDemands of a single VE must be placed on RO of the same PE
		for( VEntity ve : pmodel.getVEntities().values() ) {
			ArrayList<ResourceDemand> rdList = new ArrayList<>(
					ve.getResourceMap().values());
			Constraint c = null; 
			for( int i = 0; i < (rdList.size()-1); i++ ) {
				if(c == null) {
					c = eq(
							vmodel.getRdLocationVar(rdList.get(i)), 
							vmodel.getRdLocationVar(rdList.get(i+1))
						);
				}
				else { 
					c = and(c, eq(
							vmodel.getRdLocationVar(rdList.get(i)), 
							vmodel.getRdLocationVar(rdList.get(i+1))
						) );
				}
			}
			if(c != null) {
				result.add(c);
			}
		}
		
		for( PEntity pe : pmodel.getPEntities().values() ) {
			for( ResourceOffer ro : pe.getResourceMap().values() ) {
				// Aggregated resource demands per resource offer must be leq capcity of the resource offer
				result.add( geq(vmodel.getMaxCapVar(ro), vmodel.getAggPotRdVar(ro)) );
				
				ArrayList<ResourceDemand> potRdList = new ArrayList<>( 
						pmodel.getResourceDemands(ro.getType()).values() );  
				IntegerVariable[] potRdIntList = new IntegerVariable[potRdList.size()];
				for( int i = 0; i < potRdList.size(); i++ ) {
					potRdIntList[i] = vmodel.getPotRdVar( ro, potRdList.get(i) );
					VEntity ve = potRdList.get(i).getPEntity();
					if( ve instanceof VMachine ) {
						result.add(ifThenElse(
								eq(vmodel.getPLocationVar(pe), vmodel.getLocationVar((VMachine) ve)),
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), vmodel.getAmountVar(potRdList.get(i))), 
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), 0)
							)
						);
					}
					if( ve instanceof VVolume ) {
						result.add(ifThenElse(
								eq(vmodel.getPLocationVar(pe), vmodel.getLocationVar((VVolume) ve)),
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), vmodel.getAmountVar(potRdList.get(i))), 
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), 0)
							)
						);
					}
					if( ve instanceof VLink ) {
						result.add(ifThenElse(
								member(vmodel.getPLocationVar(pe), vmodel.getPSwitchLocationVar((VLink) ve)),
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), vmodel.getAmountVar(potRdList.get(i))), 
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), 0)
							)
						);
						result.add(ifThenElse(
								member(vmodel.getPLocationVar(pe), vmodel.getPLinkLocationVar((VLink) ve)),
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), vmodel.getAmountVar(potRdList.get(i))), 
								eq(vmodel.getPotRdVar(ro, potRdList.get(i)), 0)
							)
						);
					}
					
					//result.add(ifThenElse(vmodel.getL, n2, n3));
				}
				// Aggregated potential resource demand for a resource offer equals sum of potential resource offers
				result.add( eq(vmodel.getAggPotRdVar(ro), sum(potRdIntList)) );
			}
		}
		*/
		return result;
	}

	private ArrayList<Constraint> createPropertyConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		
		for( VMachine vm : pmodel.getVMachines().values() ) {
			for( PMachine pm : pmodel.getPMachines().values() ) {
				Constraint c = implies(
						eq(vmodel.getPLocationVar(pm), vmodel.getLocationVar(vm)), 
						isIncluded(vmodel.getPDemandVar(vm), vmodel.getPOfferVar(pm)));
				result.add(c);
			}
		}
		
		for( VVolume vv : pmodel.getVVolumes().values() ) {
			for( PStorage ps : pmodel.getPStorages().values() ) {
				Constraint c = implies(
						eq(vmodel.getLocationVar(vv), vmodel.getPLocationVar(ps)), 
						isIncluded(vmodel.getPDemandVar(vv), vmodel.getPOfferVar(ps))
					);
				result.add(c);
			}
		}
		
		// 2Do: Properties for VLinks
		
		return result;
	}

	private ArrayList<Constraint> createVLinkConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		
		// If a virtual volumes (VVs) is placed on a local physical storage (PS), 
		// the attached virtual machine (VM) must be placed on the physical machine (PM)
		// that is attached to this storage.
		for( PStorage ps : pmodel.getPStorages().values() ) {			// Iterate over all PStorages
			if( ps.hasPMachine() ) {									// Check if PStorage is directly attached to a PMachine
				for( VVolume vv : pmodel.getVVolumes().values() ) {		// Iterate over all VVolumes
					Constraint c = implies(
						eq(
							vmodel.getLocationVar(vv), 
							vmodel.getPLocationVar(ps)
						), 
						eq(
							vmodel.getLocationVar(vv.getVMachine()), 
							vmodel.getPLocationVar(ps.getConnectedPMachine())
						)
					);
					result.add(c); 										// Add new constraint
				}
			}
		}
		
		//
		

		//ArrayList<VMachine> vlCandidateList = new ArrayList(pmodel.getVMachines().values());
		ArrayList<VLink> vlVistedList = new ArrayList<>();
		
		for( VMachine vm1 : pmodel.getVMachines().values() ) {
			for( VLink vl : vm1.getVLinkList() ) {
				if( !vlVistedList.contains(vl) ) {
					
					if( vl.getOtherEnd(vm1) instanceof VMachine) {
						VMachine vm2 = (VMachine) vl.getOtherEnd(vm1);
						for(PMachine pm1 : pmodel.getPMachines().values() ) {
							for(PMachine pm2 : pmodel.getPMachines().values() ) {
								//System.out.println("Found PLinks on Path ("+pm1.getName()+", "+pm2.getName()+"):"+pm1.getPLinksOnPath(pm2).size());
								// Set CARDINALITIES

								///*
								result.add(implies(
												and(
														eq(
																vmodel.getLocationVar(vm1), 
																vmodel.getPLocationVar(pm1)
														),
														eq(
																vmodel.getLocationVar(vm2), 
																vmodel.getPLocationVar(pm2)
														)
												), 
												eq(
														vmodel.getPLinkLocationVar(vl).getCard(), 
														pm1.getPLinksOnPath(pm2).size()
												)
											));
								//*/
								for(PLink pl :pm1.getPLinksOnPath(pm2).values() ) {
									Constraint c = null;
									c = implies(
												and(
														eq(
																vmodel.getLocationVar(vm1), 
																vmodel.getPLocationVar(pm1)
														),
														eq(
																vmodel.getLocationVar(vm2), 
																vmodel.getPLocationVar(pm2)
														)
												), 
												member(
														vmodel.getPLocationVar(pl), 
														vmodel.getPLinkLocationVar(vl)
												)
											);
									if( c != null ) {
										result.add(c);
									}
								}
								for( PSwitch pw : pm1.getPSwitchesOnPath(pm2).values() ) {
									Constraint c = null;
									c = implies(
												and(
														eq(
																vmodel.getLocationVar(vm1), 
																vmodel.getPLocationVar(pm1)
														),
														eq(
																vmodel.getLocationVar(vm2), 
																vmodel.getPLocationVar(pm2)
														)
												), 
												member(
														vmodel.getPLocationVar(pw), 
														vmodel.getPSwitchLocationVar(vl)
												)
											);
									if( c != null ) {
										result.add(c);
									}
								}
							}
						}
					}
					// Second case
					if( vl.getOtherEnd(vm1) instanceof VVolume) {
						VVolume vm2 = (VVolume) vl.getOtherEnd(vm1);
						for(PMachine pm1 : pmodel.getPMachines().values() ) {
							for(PStorage ps2 : pmodel.getPStorages().values() ) {
								System.out.println("Found PLinks on Path ("+pm1.getName()+", "+ps2.getName()+"):"+pm1.getPLinksOnPath(ps2).size());
								// Set CARDINALITIES
								/*
								result.add(implies(
												and(
														eq(
																vmodel.getLocationVar(vm1), 
																vmodel.getPLocationVar(pm1)
														),
														eq(
																vmodel.getLocationVar(vm2), 
																vmodel.getPLocationVar(ps2)
														)
												), 
												eq(
														vmodel.getPLinkLocationVar(vl).getCard(), 
														pm1.getPLinksOnPath(ps2).size()
												)
											));
								
								*/

								for(PLink pl :pm1.getPLinksOnPath(ps2).values() ) {
									Constraint c = null;
									c = implies(
												and(
														eq(
																vmodel.getLocationVar(vm1), 
																vmodel.getPLocationVar(pm1)
														),
														eq(
																vmodel.getLocationVar(vm2), 
																vmodel.getPLocationVar(ps2)
														)
												), 
												member(
														vmodel.getPLocationVar(pl), 
														vmodel.getPLinkLocationVar(vl)
												)
											);
									if( c != null ) {
										result.add(c);
									}
								}
								for( PSwitch pw : pm1.getPSwitchesOnPath(ps2).values() ) {
									//System.out.println("Found PSwitches on Path ("+pm1.getName()+", "+ps2.getName()+"):"+pm1.getPswitchesOnPath(ps2).size());
									Constraint c = null;
									c = implies(
												and(
														eq(
																vmodel.getLocationVar(vm1), 
																vmodel.getPLocationVar(pm1)
														),
														eq(
																vmodel.getLocationVar(vm2), 
																vmodel.getPLocationVar(ps2)
														)
												), 
												member(
														vmodel.getPLocationVar(pw), 
														vmodel.getPSwitchLocationVar(vl)
												)
											);
									if( c != null ) {
										result.add(c);
									}
								}
							}
						}
					}
					
					vlVistedList.add(vl);
				}
			}
		}
		// Place VLinks
		/*
		for( VLink vl : pmodel.getVLinks().values() ) {
			VMachine end1 = vl.getEnd1();
			VEntity end2 = vl.getEnd2();
			if( vl.isEnd1VM() ) {
				
			}
			
			Constraint c = null;
			c = implies(
						eq(
								vmodel.getLocationVarForVMorVV( ends[0] ), 
								vmodel.getLocationVarForVMorVV( ends[1] )
						), 
						member(
								var, 
								sv1
						)
					);
		}
		*/
		
		return result;
	}

	private ArrayList<Constraint> createObjectiveConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		
		// Overall Objective
		result.add(
					eq(
						vmodel.getObjectiveVar(), 
						plus(
								vmodel.getAggBandwidthVar(),
								vmodel.getAggColSwitchesVar()
						)
					)
				);

		// Aggregated bandwidth
		ArrayList<ResourceOffer> rdList = new ArrayList<>(
				pmodel.getResourceOffers().values());
		ArrayList<ResourceOffer> rdBwList = new ArrayList<>();
		for(ResourceOffer ro : rdList) {
			if(ro.getType().equals(ResourceType.BANDWIDTH)){
				rdBwList.add(ro);
			}
		}
		int i = 0;
		IntegerVariable[] rdVarList = new IntegerVariable[rdBwList.size()];
		for(ResourceOffer ro : rdBwList) {
			rdVarList[i] = vmodel.getAggPotRdVar(ro);
			i++;
		}
		result.add(
					eq(
						vmodel.getAggBandwidthVar(), 
						sum(rdVarList)
					)
				);
		return result;
	}

}
