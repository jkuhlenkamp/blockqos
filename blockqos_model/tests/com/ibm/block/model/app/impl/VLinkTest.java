package com.ibm.block.model.app.impl;

import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class VLinkTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVLinkVMachineVMachine() {
		VMachine vm1 = new VMachine();
		VMachine vm2 = new VMachine();
		VLink vl = new VLink(vm1, vm2);
		System.out.println(vl.pretty());
		System.out.println(vm1.pretty());
		System.out.println(vm2.pretty());
	}

	@Test
	public void testVLinkVMachineVVolume() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEnds() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOtherEnd() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasEnd() {
		fail("Not yet implemented");
	}

}
