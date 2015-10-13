package com.shawn;

public class Race<T extends RaceType> {

	private T raceType;
	
	public Race(T raceType) {
		this.raceType = raceType;
	}
	
	/**
	 * @return the raceType
	 */
	public T getRaceType() {
		return raceType;
	}

	/**
	 * @param raceType the raceType to set
	 */
	public void setRaceType(T raceType) {
		this.raceType = raceType;
	}

	public String toString() {
		return raceType.getDescription();
	}
	
	
	public static void main(String[] args) {
		Race<YellowType> race = new Race<>(new YellowType());
	}
	
	public static class YellowType implements RaceType {

		@Override
		public String getDescription() {
			return "yellow";
		}
		
	}
}
