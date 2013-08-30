package com.ibm.block.placement.constraint.impl;

import static choco.Choco.*;

import java.util.ArrayList;

import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.set.SetConstantVariable;

import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.placement.constraint.intf.ConstraintModelGenInterface;
import com.ibm.block.placement.var.impl.VariableModel;

public class ConstraintModelGen2 implements ConstraintModelGenInterface {

	PlacementModel pmodel;
	VariableModel vmodel;
	
	public ConstraintModelGen2(PlacementModel pmodel, VariableModel vmodel) {
		this.pmodel = pmodel;
		this.vmodel = vmodel;
	}

	private int[] toIntArray(Object[] c) {
		int[] result = new int[c.length];
		for( int i = 0; i < c.length; i++ ) {
			//System.out.println(c[i]);
			result[i] = (int) c[i];
		}
		return result;
	}
	
	@Override
	public void setUsesObjectiveConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesVLinkConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesPropertyConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesResourceConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesSimilarPropertyConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesStorageColocationConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesStorageAntiColocationConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getUsesPathAntiColocationConstraints(boolean isUsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public ConstraintModel build() {
		ConstraintModel cmodel = new ConstraintModel();
		
	
		ArrayList<Constraint> constraints = createVLinkConstraints();
		for( Constraint c : constraints ) {
			cmodel.addVLinkConstraint(c);
		}

		
		return cmodel;
	}

	private ArrayList<Constraint> createVLinkConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		
		//ArrayList<VMachine> vlCandidateList = new ArrayList(pmodel.getVMachines().values());
		ArrayList<VLink> vlVistedList = new ArrayList<>();
				
		for( VMachine vm1 : pmodel.getVMachines().values() ) {
			for( VLink vl : vm1.getVLinkList() ) {
				//System.out.println("DOmain of "+vl.getName()+": "+vmodel.getPLinkLocationVar(vl).get)
				if( !vlVistedList.contains(vl) ) {
					
					if( vl.getOtherEnd(vm1) instanceof VMachine) {
						VMachine vm2 = (VMachine) vl.getOtherEnd(vm1);
						ArrayList<Integer> visited = new ArrayList<>();
						for(PMachine pm1 : pmodel.getPMachines().values() ) {
							for(PMachine pm2 : pmodel.getPMachines().values() ) {
								if(! visited.contains(pm2.getId()) ) {
									int[] linkArray = toIntArray( pm1.getPLinksOnPath(pm2).keySet().toArray() );
									//int[] linkArray = new int[]{15, 33, 20, 37};
									
									SetConstantVariable setVar = new SetConstantVariable(new IntegerConstantVariable(linkArray.length), linkArray);
									/*
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
												isIncluded(
														setVar, 
														vmodel.getPLinkLocationVar(vl)
												)
											);
									System.out.println(c);
									if( c != null ) {
										result.add(c);
									}
									*/
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
											//result.add(c);
										}
									}
									Constraint c = null;
									linkArray = toIntArray( pm1.getPSwitchesOnPath(pm2).keySet().toArray() );
									setVar = new SetConstantVariable(new IntegerConstantVariable(linkArray.length), linkArray);
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
												isIncluded(
														setVar, 
														vmodel.getPSwitchLocationVar(vl)
												)
											);
									if( c != null ) {
										//result.add(c);
									}
								}
							}
							visited.add(pm1.getId());
						}
					}
					
					// Second case
					if( vl.getOtherEnd(vm1) instanceof VVolume) {
						VVolume vm2 = (VVolume) vl.getOtherEnd(vm1);
						for(PMachine pm1 : pmodel.getPMachines().values() ) {
							for(PStorage ps2 : pmodel.getPStorages().values() ) {
								//System.out.println("Found PLinks on Path ("+pm1.getName()+", "+ps2.getName()+"):"+pm1.getPLinksOnPath(ps2).size());
								int[] linkArray = toIntArray( pm1.getPLinksOnPath(ps2).keySet().toArray() );
								SetConstantVariable setVar = new SetConstantVariable(new IntegerConstantVariable(linkArray.length), linkArray);
								Constraint c = null;
								
								if( linkArray.length > 0) {
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
												isIncluded(
														setVar, 
														vmodel.getPLinkLocationVar(vl)
												)
											);
									if( c != null ) {
										//result.add(c);
									}
								}
								
								
								//System.out.println("Found PSwitches on Path ("+pm1.getName()+", "+ps2.getName()+"):"+pm1.getPswitchesOnPath(ps2).size());
								c = null;
								linkArray = toIntArray( pm1.getPSwitchesOnPath(ps2).keySet().toArray() );
								setVar = new SetConstantVariable(new IntegerConstantVariable(linkArray.length), linkArray);
								if( linkArray.length > 0) {
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
												isIncluded(
														setVar, 
														vmodel.getPSwitchLocationVar(vl)
												)
											);
									if( c != null ) {
										//result.add(c);
									}
								}
							}
						}
					}
					
					vlVistedList.add(vl);
				}
			}
		
		}
		return result;
	}

}
