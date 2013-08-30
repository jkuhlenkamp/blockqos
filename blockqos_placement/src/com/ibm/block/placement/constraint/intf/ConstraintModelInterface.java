package com.ibm.block.placement.constraint.intf;

import java.util.ArrayList;

import choco.kernel.model.constraints.Constraint;

public interface ConstraintModelInterface {

	// Getters
	public ArrayList<Constraint> getAllConstraints();
	
	public ArrayList<Constraint> getObjectiveConstraints();
	public ArrayList<Constraint> getVLinkConstraints();
	public ArrayList<Constraint> getPropertyConstraints();
	public ArrayList<Constraint> getResourceConstraints();
	
	public ArrayList<Constraint> getSimilarPropertyConstraints();
	public ArrayList<Constraint> getStorageColocationConstraints();
	public ArrayList<Constraint> getStorageAntiColocationConstraints();
	public ArrayList<Constraint> getPathAntiColocationConstraints();
	
	// Setters
	
	public void addObjectiveConstraint(Constraint c);
	public void addVLinkConstraint(Constraint c);
	public void addPropertyConstraint(Constraint c);
	public void addResourceConstraint(Constraint c);
	
	public void addSimilarPropertyConstraint(Constraint c);
	public void addStorageColocationConstraint(Constraint c);
	public void addStorageAntiColocationConstraint(Constraint c);
	public void addPathAntiColocationConstraint(Constraint c);
	
}
