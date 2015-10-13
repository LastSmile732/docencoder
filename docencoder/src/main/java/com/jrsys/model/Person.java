package com.jrsys.model;


public class Person {
	
	private String name;
	
	private int height;
	
	private int weight;
	
	private Person(Person.Builder builder) {
		this.name = builder.name;
		this.height = builder.height;
		this.weight = builder.weight;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	public static class Builder {
		
		private String name;
		
		private int height = 150;
		
		private int weight = 60;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Person.Builder height(int height) {
			this.height = height;
			return this;
		}
		
		public Person.Builder weight(int weight) {
			this.weight = weight;
			return this;
		}
		
		public Person build() {
			return new Person(this);
		}
	}
	
}
