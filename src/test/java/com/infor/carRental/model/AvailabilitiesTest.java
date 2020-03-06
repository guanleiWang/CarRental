package com.infor.carRental.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class AvailabilitiesTest {
	private Availabilities avail;
	
	@Before
	public void setup() {
		avail = new Availabilities();
	}

	@Test
	public void testInit() {
		Availabilities avail = new Availabilities();
		
		assertFalse(avail.initialize(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-03T10:15:30")));
		
		assertTrue(avail.initialize(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-04T10:15:30")));
		
		assertTrue(avail.isAvailable(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-04T10:15:30")));
		assertTrue(avail.isAvailable(LocalDateTime.parse("2007-12-03T15:15:30"), LocalDateTime.parse("2007-12-04T10:15:30")));
		assertTrue(avail.isAvailable(LocalDateTime.parse("2007-12-03T15:15:30"), LocalDateTime.parse("2007-12-04T09:15:30")));
		assertTrue(avail.isAvailable(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-04T09:15:30")));
		
		assertFalse(avail.isAvailable(LocalDateTime.parse("2007-12-02T10:15:30"), LocalDateTime.parse("2007-12-04T09:15:30")));
		assertFalse(avail.isAvailable(LocalDateTime.parse("2007-12-04T10:15:30"), LocalDateTime.parse("2007-12-04T11:15:30")));
		assertFalse(avail.isAvailable(LocalDateTime.parse("2007-12-02T10:15:30"), LocalDateTime.parse("2007-12-05T09:15:30")));
		
		
	
	}
	
	@Test
	public void testReserve() {
		assertTrue(avail.initialize(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-04T10:15:30")));
			
		// try to reserve some time slot
		assertTrue(avail.reserve(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-04T10:15:30")));
		
		// nothing left after the reservation
		assertEquals(avail.getAvailableSlots().size(), 0);
		
		assertTrue(avail.initialize(LocalDateTime.parse("2007-12-03T10:15:30"), LocalDateTime.parse("2007-12-04T10:15:30")));
		
		// try to reserve some time slot
		assertTrue(avail.reserve(LocalDateTime.parse("2007-12-03T11:15:30"), LocalDateTime.parse("2007-12-04T09:15:30")));
		
		// split the slots
		assertEquals(avail.getAvailableSlots().size(), 2);
		
		assertFalse(avail.reserve(LocalDateTime.parse("2007-12-03T10:25:30"), LocalDateTime.parse("2007-12-04T00:15:30")));
		
		// no change to existing slots
		assertEquals(avail.getAvailableSlots().size(), 2);
		
		assertFalse(avail.reserve(LocalDateTime.parse("2007-12-04T06:25:30"), LocalDateTime.parse("2007-12-04T10:00:30")));
		
		// no change to existing slots
		assertEquals(avail.getAvailableSlots().size(), 2);
		
		assertFalse(avail.reserve(LocalDateTime.parse("2007-12-03T10:25:30"), LocalDateTime.parse("2007-12-04T10:00:30")));
		
		assertEquals(avail.getAvailableSlots().size(), 2);
		
		// split it further
		assertTrue(avail.reserve(LocalDateTime.parse("2007-12-03T10:25:30"), LocalDateTime.parse("2007-12-03T11:00:30")));
		
		assertEquals(avail.getAvailableSlots().size(), 3);
		
		// split it further
		assertTrue(avail.reserve(LocalDateTime.parse("2007-12-04T09:25:30"), LocalDateTime.parse("2007-12-04T10:00:30")));
		
		assertEquals(avail.getAvailableSlots().size(), 4);
		

		// split it but the number of slots not changed
		assertTrue(avail.reserve(LocalDateTime.parse("2007-12-04T10:00:30"), LocalDateTime.parse("2007-12-04T10:10:30")));
		
		assertEquals(avail.getAvailableSlots().size(), 4);
		
		// split it but the number of slots not changed
		assertTrue(avail.reserve(LocalDateTime.parse("2007-12-03T10:20:30"), LocalDateTime.parse("2007-12-03T10:25:30")));
		
		assertEquals(avail.getAvailableSlots().size(), 4);
		
		avail.getAvailableSlots().stream().forEachOrdered(s -> System.out.println(s.getFrom().get() + "--" + s.getTo().get()));

	}

}
