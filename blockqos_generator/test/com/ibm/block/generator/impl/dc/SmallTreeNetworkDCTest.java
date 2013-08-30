package com.ibm.block.generator.impl.dc;

import com.ibm.block.generator.impl.dc.SmallTreeNetworkDCGen;
import com.ibm.block.model.core.impl.PlacementModel;
import junit.framework.TestCase;

public class SmallTreeNetworkDCTest extends TestCase {

	PlacementModel model;
	SmallTreeNetworkDCGen g;
	
	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		model = new PlacementModel();
		g = new SmallTreeNetworkDCGen(model, 1, 1,1,1,1, 1,0,1,1);
	}

	public void testBuild() {
		PlacementModel model = g.build();
		assertNotNull(model);
		
		/*
		for(PMachine e : model.getPMachines().values()) {
			System.out.println(e.pretty());
		}
		*/
	}

}
