package com.ibm.block.model.core.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class ResourceTypeTest {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		ResourceType t1 = ResourceType.CORES;
		ResourceType t2 = ResourceType.CORES;
		ResourceType t3 = ResourceType.BANDWIDTH;
		ResourceType t4 = ResourceType.RAM;
		ResourceType t5 = ResourceType.BANDWIDTH;
		
		/*
		System.out.println("t1.id:" +t1.getId()+ ", t2.id:" +t2.getId()+ ", t3.id:" 
				+t3.getId()+ ", t4.id:" +t4.getId()+ ", t5.id:" +t5.getId());
		System.out.println("t1.metric:" +t1.getMetric()+ ", t2.metric:" +t2.getMetric());
		*/
		assertEquals( "Expected value=t1.id !", t1.getId(), t2.getId() );
		assertNotSame( "Expected value=t1.id !", t1.getId(), t3.getId() );
		assertNotSame( "Expected value=t1.id !", t1.getId(), t4.getId() );
		assertNotSame( "Expected value=t1.id !", t1.getId(), t5.getId() );
		assertEquals( "Expected value=t3.id !", t3.getId(), t5.getId() );
		assertNotSame("Expected value=t4.id !", t1.getId(), t4.getId() );
	}

}
