package com.ibm.block.placement.constraint.impl;

import java.util.ArrayList;

import choco.kernel.model.constraints.Constraint;

import com.ibm.block.placement.constraint.intf.ConstraintModelInterface;

public class ConstraintModel implements ConstraintModelInterface {

	private ArrayList<Constraint> obj_cons;
	private ArrayList<Constraint> vl_cons;
	private ArrayList<Constraint> prop_cons;
	private ArrayList<Constraint> resource_cons;
	private ArrayList<Constraint> similar_cons;
	private ArrayList<Constraint> storage_col_cons;
	private ArrayList<Constraint> storage_anti_col_cons;
	private ArrayList<Constraint> path_anti_col_cons;
	
	public ConstraintModel() {
		obj_cons = new ArrayList<>();
		vl_cons = new ArrayList<>();
		prop_cons = new ArrayList<>();
		resource_cons = new ArrayList<>();
		similar_cons = new ArrayList<>();
		storage_col_cons = new ArrayList<>();
		storage_anti_col_cons = new ArrayList<>();
		path_anti_col_cons = new ArrayList<>();
	}
	
	@Override
	public ArrayList<Constraint> getObjectiveConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) obj_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getVLinkConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) vl_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getPropertyConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) prop_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getResourceConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) resource_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getSimilarPropertyConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) similar_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getStorageColocationConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) storage_col_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getStorageAntiColocationConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) storage_anti_col_cons.clone();
		return clone;
	}

	@Override
	public ArrayList<Constraint> getPathAntiColocationConstraints() {
		@SuppressWarnings("unchecked")
		ArrayList<Constraint> clone = (ArrayList<
				Constraint>) path_anti_col_cons.clone();
		return clone;
	}

	@Override
	public void addObjectiveConstraint(Constraint c) {
		obj_cons.add(c);
	}

	@Override
	public void addVLinkConstraint(Constraint c) {
		vl_cons.add(c);
	}

	@Override
	public void addPropertyConstraint(Constraint c) {
		prop_cons.add(c);
	}

	@Override
	public void addResourceConstraint(Constraint c) {
		resource_cons.add(c);
	}

	@Override
	public void addSimilarPropertyConstraint(Constraint c) {
		similar_cons.add(c);
	}

	@Override
	public void addStorageColocationConstraint(Constraint c) {
		storage_col_cons.add(c);
	}

	@Override
	public void addStorageAntiColocationConstraint(Constraint c) {
		storage_anti_col_cons.add(c);
	}

	@Override
	public void addPathAntiColocationConstraint(Constraint c) {
		path_anti_col_cons.add(c);
	}

	@Override
	public ArrayList<Constraint> getAllConstraints() {
		ArrayList<Constraint> result = new ArrayList<>();
		result.addAll(obj_cons);
		result.addAll(vl_cons);
		result.addAll(prop_cons);
		result.addAll(resource_cons);
		result.addAll(similar_cons);
		result.addAll(storage_col_cons);
		result.addAll(storage_anti_col_cons);
		result.addAll(path_anti_col_cons);
		return result;
	}

}
