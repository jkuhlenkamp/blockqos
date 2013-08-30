package com.ibm.block.generator.impl.app;

import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.impl.PlacementModel;
import junit.framework.TestCase;

public class SmallThreeTierAppGenTest extends TestCase {

	private PlacementModel model;
	private SmallThreeTierAppGen g;
	
	protected static void tearDownAfterClass() throws Exception {
	}

	protected void setUp() throws Exception {
		model = new PlacementModel();
		g = new SmallThreeTierAppGen(model);
	}

	public void testBuild() {
		model = g.build();
		assertNotNull(model);
		
		for(VMachine e : model.getVMachines().values()) {
			System.out.println(e.pretty());
		}
		for(VVolume e : model.getVVolumes().values()) {
			System.out.println(e.pretty());
		}
		for(VLink e : model.getVLinks().values()) {
			System.out.println(e.pretty());
		}
	}

}
