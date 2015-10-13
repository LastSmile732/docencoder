package com.shawn;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar newCalendar = Calendar.getInstance();
		
		int year = newCalendar.get(Calendar.YEAR);  
	    System.out.println("YEAR is = " + String.valueOf(year)); 
	    //System.out.println("DateTime= "+ String.valueOf(newCalendar.get(Calendar.YEAR))+"/"+String.valueOf(newCalendar.get(Calendar.MONTH))+"/"+String.valueOf(newCalendar.get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf(newCalendar.get(Calendar.SHORT)));
	    Date calDate = newCalendar.getTime();
	    System.out.println("Date Cal: "+ calDate);
	    initDate();
	}
	
	public static void initDate(){
		Date newDate = new Date();
		Calendar cal =Calendar.getInstance();
		cal.setTime(newDate);
		int year = cal.get(Calendar.YEAR);
		
		System.out.println("Date output: "+cal);

		System.out.println("YEAR output: "+year);
	}
	
	
}
