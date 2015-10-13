package com.quartz;

import java.util.Calendar;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class QuartzJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		//Date date = new Date();
		System.out.println("Hello Quartz A! - " + cal.getTime());
		
	}

}
