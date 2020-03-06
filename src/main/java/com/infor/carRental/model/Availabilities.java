package com.infor.carRental.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Availabilities {
	
	private List<Duration> availablities = Collections.synchronizedList(new LinkedList<Duration>());
	
	public Availabilities() {
		super();
	}
	
	public boolean initialize(LocalDateTime from, LocalDateTime to) {
		// if invalidate time frame is provided, refuse to reset the availability
		if (from == null || to == null || !from.isBefore(to)) {
			return false;
		}
		// reset the car availability time frames
		if (!this.availablities.isEmpty()) {
			// could clear the list anyways, but probably be better to log this
			this.availablities.clear();
		}
		
		this.availablities.add(new Duration(from, to));		
		return true;
	}
	
	public void clear() {
		// make the car offline by removing all the availability time frames
		synchronized(this.availablities) {
			this.availablities.clear();
		}
	}
	
	private Optional<Duration> findAvaiableSlot(LocalDateTime from, LocalDateTime to) {
		synchronized(this.availablities) {
			return this.availablities.stream()
					.filter(dur -> dur.cover(from, to))
					.findFirst();
		}
	}
	
	public boolean isAvailable(LocalDateTime from, LocalDateTime to) {
		return this.findAvaiableSlot(from, to).isPresent();
	}
	
	public boolean reserve(LocalDateTime from, LocalDateTime to) {
		synchronized(this.availablities) {
			Optional<Duration> re = this.findAvaiableSlot(from, to);
			if (!re.isPresent()) {
				return false;
			}
			
			Duration d = re.get();
			
			// we need to keep the order of the duration
			int dInd = this.availablities.indexOf(d);
			/* to handle the following possible cases:
				   ds---from---to---dt  => (ds, from) and (to, dt)
				   ds(from)---to---dt ==> (to, dt)
				   ds---from---dt(to) ==> (ds, from)
				   ds(from)---dt(to) ==> ()
			
			 */
			this.availablities.remove(dInd);
			if (d.getFrom().get().isBefore(from)) {
				this.availablities.add(dInd, new Duration(d.getFrom().get(), from));
				dInd++;
			}
			if (d.getTo().get().isAfter(to)) {
				this.availablities.add(dInd, new Duration(to, d.getTo().get()));
				dInd++;
			}
			
			return true;
		}
	}
	
	public List<Duration> getAvailableSlots() {
		//TODO: better to return a copy of the list
		return this.availablities;
	}
}
