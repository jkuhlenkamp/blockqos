package com.ibm.block.model.dc.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class PMachineTest {

	private PMachine pm1;
	private PStorage ps1;
	private PStorage ps2;
	private PSwitch pw1;
	private PLink pl1;
	private PLink pl2;
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pm1 = new PMachine();
		ps1 = new PStorage();
		ps2 = new PStorage();
		pw1 = new PSwitch();
		pl1 = new PLink(pm1, ps1);
		pl2 = new PLink(pm1, pw1);
	}

	@Test
	public void testGetConnectedPSwitch() {
		assertNotNull(pm1.getConnectedPSwitch());
		assertEquals("Expected PSwitch objects=pw1", pw1, pm1.getConnectedPSwitch());
	}

	@Test
	public void testGetConnectedPStorages() {
		assertNotNull(pm1.getConnectedPStorages());
		HashMap<Integer, PStorage> expected = new HashMap<>();
		expected.put(ps1.getId(), ps1);
		assertEquals("Expected PStorage objects=ps1", expected, pm1.getConnectedPStorages());
		pl2 = new PLink(pm1, ps2);
		expected.put(ps2.getId(), ps2);
		assertEquals("Expected PStorage objects=ps1", expected, pm1.getConnectedPStorages());
	}

	@Test
	public void testGetSwitchPLink() {
		assertNotNull(pm1.getSwitchPLink());
		assertEquals("Expected PLink=pl2", pl2, pm1.getSwitchPLink());
	}

	@Test
	public void testGetPLink() {
		assertNotNull(pm1.getPLink(ps1));
		pl2 = new PLink(pm1, ps2);
		assertNotNull(pm1.getPLink(ps2));
		assertEquals("Expected PLink=pl1", pl1, pm1.getPLink(ps1));
		assertEquals("Expected PLink=pl2", pl2, pm1.getPLink(ps2));
	}

	@Test
	public void testGetPLinksOnPathPMachine() {
		PStorage ps3 = new PStorage();
		PSwitch pw2 = new PSwitch();
		PSwitch pw3 = new PSwitch();
		@SuppressWarnings("unused")
		PLink pl3 = new PLink(ps2, pw1);
		@SuppressWarnings("unused")
		PLink pl4 = new PLink(ps3, pw2);
		PLink pl5 = new PLink(pw1, pw3);
		PLink pl6 = new PLink(pw2, pw3);
		@SuppressWarnings("unused")
		PStorage ps4 = new PStorage();
		PMachine pm2 = new PMachine();
		PMachine pm3 = new PMachine();
		@SuppressWarnings("unused")
		PMachine pm4 = new PMachine();
		PLink pl7 = new PLink(pm2, pw1);
		PLink pl8 = new PLink(pm3, pw2);
	
		HashMap<Integer, PLink> expected = new HashMap<>();
		expected.put(pl2.getId(), pl2);
		expected.put(pl7.getId(), pl7);
		HashMap<Integer, PLink> obtained = pm1.getPLinksOnPath(pm2);
		assertNotNull(obtained);
		//System.out.println("Expected links: " +pl2.getName()+ ", " +pl7.getName());
		//System.out.print("Obtained links: ");
		//for(PLink pl : pm1.getPLinksOnPath(pm2).values()) {
		//	System.out.print(pl.getName());
		//}
		
		assertEquals("Expected links=[pl2, pl7]", expected, obtained);
		expected.remove(pl7.getId());
		expected.put(pl5.getId(), pl5);
		expected.put(pl6.getId(), pl6);
		expected.put(pl8.getId(), pl8);
		obtained = pm1.getPLinksOnPath(pm3);
		
		assertEquals("Expected links=[pl2, pl5, pl6, pl8]", expected, obtained);
		/*
		System.out.print("Expected links: ");
		for(PLink pl : expected.values()) {
			System.out.print(pl.getName());
		}
		System.out.println("");
		System.out.print("Obtained links: ");
		for(PLink pl : pm1.getPLinksOnPath(pm3).values()) {
			System.out.print(pl.getName());
		}
		*/
	}

	@Test
	public void testGetPLinksOnPathPStorage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPswitchesOnPathPMachine() {
		PStorage ps3 = new PStorage();
		PSwitch pw2 = new PSwitch();
		PSwitch pw3 = new PSwitch();
		PLink pl3 = new PLink(ps2, pw1);
		PLink pl4 = new PLink(ps3, pw2);
		PLink pl5 = new PLink(pw1, pw3);
		PLink pl6 = new PLink(pw2, pw3);
		PStorage ps4 = new PStorage();
		PMachine pm2 = new PMachine();
		PMachine pm3 = new PMachine();
		PMachine pm4 = new PMachine();
		PLink pl7 = new PLink(pm2, pw1);
		PLink pl8 = new PLink(pm3, pw2);
		
		HashMap<Integer, PSwitch> expected = new HashMap<>();
		expected.put(pw1.getId(), pw1);
		HashMap<Integer, PSwitch> obtained = pm1.getPSwitchesOnPath(pm2);
		assertNotNull(obtained);
		assertEquals("Expected switches on path pw1", expected, obtained);
		
		expected.put(pw3.getId(), pw3);
		expected.put(pw2.getId(), pw2);
		obtained = pm1.getPSwitchesOnPath(pm3);
		assertNotNull(obtained);
		assertEquals("Expected switches on path [pw1, pw3, pw2]", expected, obtained);
	}

	@Test
	public void testGetPswitchesOnPathPStorage() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddPLink() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasSwitch() {
		assertTrue(pm1.hasSwitch());
		PMachine pm2 = new PMachine();
		assertFalse(pm2.hasSwitch());
	}

	@Test
	public void testIsConnectedTo() {
		assertTrue("PMachine pm must be connected to ps1!", pm1.isConnectedTo(ps1));
		assertFalse("PMachine pm must NOT be connected to pw!", pm1.isConnectedTo(ps2));
	}
	
	/**
	 * Runs test in a tree network data center.
	 * 				pw3
	 * 		pw1				pw2
	 * 	pm		ps2			ps3
	 *	ps1
	 */
	@Test
	public void testHasPathToPStorage() {
		PStorage ps3 = new PStorage();
		PSwitch pw2 = new PSwitch();
		PSwitch pw3 = new PSwitch();
		PLink pl3 = new PLink(ps2, pw1);
		PLink pl4 = new PLink(ps3, pw2);
		PLink pl5 = new PLink(pw1, pw3);
		PLink pl6 = new PLink(pw2, pw3);
		PStorage ps4 = new PStorage();
		
		assertTrue("Must have path to ps1", pm1.hasPathTo(ps1) );
		assertTrue("Must have path to ps2", pm1.hasPathTo(ps2) );
		assertTrue("Must have path to ps3", pm1.hasPathTo(ps3) );
		assertFalse("Must NOT have path to ps4", pm1.hasPathTo(ps4) );
	}

	@Test
	public void testHasPathToPMachine() {
		PStorage ps3 = new PStorage();
		PSwitch pw2 = new PSwitch();
		PSwitch pw3 = new PSwitch();
		PLink pl3 = new PLink(ps2, pw1);
		PLink pl4 = new PLink(ps3, pw2);
		PLink pl5 = new PLink(pw1, pw3);
		PLink pl6 = new PLink(pw2, pw3);
		PStorage ps4 = new PStorage();
		PMachine pm2 = new PMachine();
		PMachine pm3 = new PMachine();
		PMachine pm4 = new PMachine();
		PLink pl7 = new PLink(pm2, pw1);
		PLink pl8 = new PLink(pm3, pw2);
		
		assertTrue("Must have path to pm1", pm1.hasPathTo(pm1) );
		assertTrue("Must have path to pm2", pm1.hasPathTo(pm2) );
		assertTrue("Must have path to pm3", pm1.hasPathTo(pm3) );
		assertFalse("Must NOT have path to pm4", pm1.hasPathTo(pm4) );
		
	}
	
}
