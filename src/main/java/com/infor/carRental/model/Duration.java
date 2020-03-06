package com.infor.carRental.model;

import java.time.LocalDateTime;
import java.util.Optional;

public class Duration {
	private Optional<LocalDateTime> from = Optional.empty();
	private Optional<LocalDateTime> to = Optional.empty();
	
	public Duration(LocalDateTime f, LocalDateTime t) {
		super();
		//TODO: to make sure 'from' is earlier than 'to', 
		// and the same check should be done on 'setter' methods
		this.from = Optional.of(f);
		this.to = Optional.of(t);
	}

	public Optional<LocalDateTime> getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime f) {
		this.from = Optional.of(f);
	}

	public Optional<LocalDateTime> getTo() {
		return to;
	}

	public void setTo(LocalDateTime t) {
		this.to = Optional.of(t);
	}	
	
	public boolean cover(Duration other) {
		
		if (other == null) {
			return false;
		}
		
		return other.inBetween(this);
	}
	
	public boolean cover(LocalDateTime f, LocalDateTime t) {
		
		return this.inBetween(f, t, this.getFrom().get(), this.getTo().get());
	}
	
	public boolean inBetween(Duration other) {
		
		if (other == null) {
			return false;
		}
		
		return this.inBetween(this.getFrom().get(), this.getTo().get(),
				other.getFrom().get(), other.getTo().get());
	}
	
	public boolean inBetween(LocalDateTime f, LocalDateTime t) {
		
		return this.inBetween(this.getFrom().get(), this.getTo().get(), f, t);
	}
	
	// if the first duration is inside of second duration
	private boolean inBetween(LocalDateTime ff, LocalDateTime ft, LocalDateTime sf, LocalDateTime st) {
		if (ff == null || ft == null || sf == null || st == null) {
			return false;
		}
		
		// if the from time of the second duration 'sf' is not later than first from time ff
		// and to time of second duration 'st' is not earlier than this 'to', 
		// then this first duration is in between the second
		if (!sf.isAfter(ff) && !st.isBefore(ft)) {
			return true;
		}
		
		return false;
	}
}
