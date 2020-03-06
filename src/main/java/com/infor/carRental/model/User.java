package com.infor.carRental.model;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
	private int id;
	private String name;
	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	public User() {
		super();
	}

	public User(String name) {
		this.id = atomicInteger.incrementAndGet();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
