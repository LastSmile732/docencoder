package com.trigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.quartz.QuartzJob;

public class SimpleJobTrigger {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JobDetail job = new JobDetail();
		job.setName("quartzJobName");
		job.setJobClass(QuartzJob.class);
		
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("triggerName");
		trigger.setStartTime(new Date(System.currentTimeMillis()+1000));
		trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		trigger.setRepeatInterval(3000);
		
		Scheduler schedule = null;
		try {
			schedule = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {

			e.printStackTrace();
		}
		try {
			schedule.start();
			schedule.scheduleJob(job, trigger);
		} catch (SchedulerException e) {

			e.printStackTrace();
		}
		
	}

}
