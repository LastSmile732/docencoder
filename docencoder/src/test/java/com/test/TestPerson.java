package com.test;

import org.junit.Test;

import com.jrsys.model.Person;


public class TestPerson {

	@Test
	public void test() {
		Person.Builder builder = new Person.Builder("Shawn");
		builder.height(179)
			.weight(60);
		Person p1 = builder.build();
		Person p2 = builder.build();
		System.out.println(p1.getName() + " " + p2.getName());
	}
}
