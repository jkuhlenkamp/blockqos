package com.ibm.block.placement.var.intf;

import java.util.ArrayList;

import choco.kernel.model.constraints.Constraint;

public interface ConModelInterface {

	public ArrayList<Constraint> getAllConstraints();
	
	public void addCommunicationPathConstraints(ArrayList<Constraint> l);
	
	public void addResourceConstraints(ArrayList<Constraint> l);
	
	public void addVVolumeCollocationConstraints(ArrayList<Constraint> l);
	
}
