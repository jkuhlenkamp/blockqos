package com.ibm.block.model.app.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class VVolumeTest {

	private VVolume vv;
	private VMachine vm;
	private VLink vl;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		vv = new VVolume();
		vm = new VMachine();
		vl = new VLink(vm, vv);
	}

	@Test
	public void testVVolume() {
		assertNotNull(vv);
		assertEquals("Expected name=vv[id]", "vv" +vv.getId(), vv.getName());
	}

	@Test
	public void testGetVLink() {
		assertEquals("Expected VLink=vl", vl, vv.getVLink());
	}

	@Test
	public void testGetVMachine() {
		assertNotNull("Attached VMachine must not be null!", vv.getVMachine());
		assertEquals("Expected class=VMachine", VMachine.class, vv.getVMachine().getClass());
	}

	@Test
	public void testAddVLink() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsConnectedTo() {
		assertEquals("Expected is connected to vm=true!", true, vv.isConnectedTo(vm));
	}

}
