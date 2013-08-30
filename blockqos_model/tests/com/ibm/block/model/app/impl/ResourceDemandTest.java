package com.ibm.block.model.app.impl;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.ibm.block.model.core.enums.ResourceType;

public class ResourceDemandTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testResource() {
		ResourceDemand res = new ResourceDemand(ResourceType.CORES, 6);
		assertEquals( "Expected name=!", "CORES_"+res.getId(), res.getName() );
	}

}