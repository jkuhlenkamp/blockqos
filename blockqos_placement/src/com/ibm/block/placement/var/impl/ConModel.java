package com.ibm.block.placement.var.impl;

import java.util.ArrayList;

import choco.kernel.model.constraints.Constraint;

import com.ibm.block.placement.var.intf.ConModelInterface;

public class ConModel implements ConModelInterface {

	private ArrayList<Constraint> com_path_cons;		// Communication Path Constraints
	private ArrayList<Constraint> res_cons;				// Resource constraints
	private ArrayList<Constraint> vol_collocation_cons;	// VVolume collocation and anti=collocation constraints constraints
	
	ConModel() {
		com_path_cons = new ArrayList<>();
		res_cons = new ArrayList<>();
		vol_collocation_cons = new ArrayList<>();
	}
	
	@Override
	public ArrayList<Constraint> getAllConstraints() {
		ArrayList<Constraint> l = new ArrayList<>();
		l.addAll(com_path_cons);
		l.addAll(res_cons);
		l.addAll(vol_collocation_cons);
		return l;
	}

	@Override
	public void addCommunicationPathConstraints(ArrayList<Constraint> l) {
		com_path_cons.addAll(l);
	}

	@Override
	public void addResourceConstraints(ArrayList<Constraint> l) {
		res_cons.addAll(l);
	}

	@Override
	public void addVVolumeCollocationConstraints(ArrayList<Constraint> l) {
		vol_collocation_cons.addAll(l);
		
	}

}
