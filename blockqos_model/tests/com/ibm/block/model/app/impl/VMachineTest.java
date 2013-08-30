package com.ibm.block.model.app.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class VMachineTest {

	private VMachine vm1;
	private VMachine vm2;
	private VLink vl;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		vm1 = new VMachine();
		vm2 = new VMachine();
		vl = new VLink(vm1, vm2);
	}

	@Test
	public void testGetConnectedVMachines() {
		HashMap<Integer, VMachine> expectedVmList = new HashMap<>();
		expectedVmList.put(vm2.getId(), vm2);
		
		HashMap<Integer, VMachine> actualVmList = vm1.getConnectedVMachines();
		assertEquals("Expected HashMap=!", actualVmList, expectedVmList);
		actualVmList.remove(vm2);
		actualVmList = vm1.getConnectedVMachines();
		assertEquals("Expected shallow copy!", actualVmList, expectedVmList);
	}

	@Test
	public void testGetConnectedVVolumes() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsConnectedToVMachine() {
		assertEquals("Expected isConnected=true!", true, vm1.isConnectedTo(vm2));
		assertEquals("Expected isConnected=false!", false, vm1.isConnectedTo(vm1));
	}

	@Test
	public void testIsConnectedToVVolume() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVLinkVMachine() {
		assertEquals("Expected VLink=vl!", vl, vm1.getVLink(vm2));
	}

	@Test
	public void testGetVLinkVVolume() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddVLink() {
		fail("Not yet implemented");
	}

}
