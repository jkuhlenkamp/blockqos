package com.ibm.block.placement.var.impl;

import com.ibm.block.generator.impl.app.SmallThreeTierAppGen;
import com.ibm.block.generator.impl.dc.SmallTreeNetworkDCGen;
import com.ibm.block.model.core.impl.PlacementModel;

import junit.framework.TestCase;

public class VariableModelTest extends TestCase {

	private PlacementModel model;
	private VariableModel vars;
	
	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		super.setUp();
		model = new PlacementModel();
		//model = new SmallTreeNetworkDCGen(model).build();
		model = new SmallThreeTierAppGen(model).build();
		vars = new VariableModel(model);
	}

	public void testVariableModel() {
		assertNotNull(vars);
		assertEquals("Expected pm location vars=!", model.getPMachines().size(), vars.getPmLocationVarMap().size());
		assertEquals("Expected ps location vars=!", model.getPStorages().size(), vars.getPsLocationVarMap().size());
		assertEquals("Expected pw location vars=!", model.getPSwitches().size(), vars.getPwLocationVarMap().size());
		assertEquals("Expected pl location vars=!", model.getPLinks().size(), vars.getPlLocationVarMap().size());
		
		System.out.println(vars.pretty());
		System.out.println(model.getPSwitches());
		System.out.println(model.getPSwitchIntList().length);
		System.out.println(model.getPLinks());
		System.out.println(model.getPLinkIntList().length);
		/*
		assertNotNull(vars.getP)pm_loc_vars
		ps_loc_vars
		pw_loc_vars = new HashMap<>();
		pl_loc_vars = new HashMap<>();
		prop_vars = new HashMap<>();
		prop_offer_vars = new HashMap<>();
		prop_demand_vars = new HashMap<>();
		max_capacity_vars = new HashMap<>();
		pre_capacity_vars = new HashMap<>();
		pre_utilization_vars = new HashMap<>();
		amount_vars = new HashMap<>();
		vm_loc_vars = new HashMap<>();
		vv_loc_vars = new HashMap<>();
		vl_pw_loc_vars = new HashMap<>();
		vl_pl_loc_vars = new HashMap<>();
		pot_agg_rd_vars = new HashMap<>();
		pot_rd_vars = new HashMap<>();
		*/
	}

}