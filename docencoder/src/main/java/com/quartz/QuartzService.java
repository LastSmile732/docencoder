package com.quartz;
/**
 * A Service to operate Quartz 1.6 API
 * 2014/12/25 Merry Christmas
 * @author Shawn Chen
 */
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class QuartzService {
	
	private SchedulerFactory factory;
	private Scheduler schedule;
	
    /**
     * Controller - initialize using SchedulerFactory and Scheduler
     * @param SchedulerFactory
     * @param Schduler
     */
	public QuartzService(SchedulerFactory schfactory, Scheduler scheduler){
		this.factory=schfactory;
		this.schedule=scheduler;
	}
	
    /**
     * Controller - initialize using a quartz property file from the local server
     * @param String property file name
     */
	public QuartzService(String propertyFileName){
		//((StdSchedulerFactory) factory).initialize("quartz.properties");			
		try {
			InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("quartz.properties");
			Properties prop = new Properties();
			prop.load(in);
			this.factory = new StdSchedulerFactory(prop);
			this.schedule = factory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Get a Quartz job and active the job class with a simple trigger 
     * @param 
     */
	public void forceTriggerJob(String jobName, String groupName){
		JobDetail job = new JobDetail();
		try {
			job = this.getSchedule().getJobDetail(jobName, groupName);
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
			JobDetail newJob = new JobDetail();
			newJob.setName(jobName);
			newJob.setJobClass(job.getJobClass());
			newJob.setGroup("FORCED");
			newJob.setJobDataMap(job.getJobDataMap());
				
			SimpleTrigger trigger = new SimpleTrigger();
			trigger.setStartTime(new Date(System.currentTimeMillis()+1000));
			trigger.setRepeatCount(0);
			trigger.setName(jobName+job.getGroup());
			trigger.setGroup("FORCED");
		try {
			this.schedule.scheduleJob(newJob, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
    /**
     *  Active the job class with a simple trigger and a parameter, 
     *  if the parameter is true and the job is not running then execute the job trigger.
     * @param 
     */
	public void forceTriggerJob(String jobName, String groupName, boolean checkOrigin){
		
		if (this.isJobRunning(jobName, groupName)==false && checkOrigin == true){
			this.forceTriggerJob(jobName, groupName);
		}	
	}
	
    /**
     * Pause a currently stored job
     * @param 
     */
	public void pauseJob(String searchJobName, String searchGroupName) throws SchedulerException{

		JobDetail job = this.getSchedule().getJobDetail(searchJobName, searchGroupName);
		if (job!=null){
			this.getSchedule().pauseJob(searchJobName, searchGroupName);
			System.out.println("Pause Job: " + job);

		}
	}
	
    /**
     * Resume a currently stored job
     * @param 
     */
	public void resumeJob(String searchJobName, String searchGroupName) throws SchedulerException{
		// TODO not tested yet
		JobDetail job = this.getSchedule().getJobDetail(searchJobName, searchGroupName);
		if (job!=null){
			this.getSchedule().resumeJob(searchJobName, searchGroupName);
			System.out.println("Resume Job: " + job);

		} 
	}
	
    /**
     * Put all the stored jobs in a map, use job name as the key and group name as the value.
     * Please notice that keys might be duplicated if a job was assigned to different groups.
     * @param None
     * @return ArrayListMultimap 
     */
	public ArrayListMultimap<String, String> listJobs(){
		 ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
		 int i=0;
		 int j=0;
		 String[] jobNames = null;
		 String[] groupNames = null;	 
			try {
				groupNames = this.getSchedule().getJobGroupNames();
				if (groupNames != null){
					for (i = 0; i < groupNames.length; i++) {
						   //System.out.println("Group: " + groupNames[i] + " contains the following jobs");
						   jobNames = this.getSchedule().getJobNames(groupNames[i]);
	
						   for (j = 0; j < jobNames.length; j++) {
						      //System.out.println("- " + jobNames[j]);
						      multimap.put(jobNames[j], groupNames[i]);
						   }
						}
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			return multimap;
	}
	
	
    /**
     * Put all the stored triggers in a map, use trigger name as the key and trigger group name as the value.
     * Please notice that keys might be duplicated if a trigger was assigned to different groups.
     * @param None
     * @return ArrayListMultimap
     */
	public ArrayListMultimap<String, String> listTriggers(){
		 ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
		 int i=0;
		 int j=0;
		 String[] triggerGroups=null;
		 String[] triggersInGroup=null;
			try {
				triggerGroups = this.getSchedule().getTriggerGroupNames();
				for (i = 0; i < triggerGroups.length; i++) {
				   //System.out.println("Group: " + triggerGroups[i] + " contains the following triggers");
				   triggersInGroup = this.getSchedule().getTriggerNames(triggerGroups[i]);
	
				   for (j = 0; j < triggersInGroup.length; j++) {
				     // System.out.println("- " + triggersInGroup[j]);
					   multimap.put(triggersInGroup[j], triggerGroups[i]);
				   }
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			return multimap;
	}
    /**
     * Print all the stored jobs
     * @param None
     */
	public void printJobs(){
		ArrayListMultimap<String, String> multimap = this.listJobs();
		for ( String key : multimap.keys()){
			for(String values : multimap.get(key)){
				System.out.println("Job: " + key + " in group(s): "+ values);
			}
		}
	}
	
    /**
     * Print all the stored triggers
     * @param None
     */
	public void printTriggers(){
		ArrayListMultimap<String, String> multimap = this.listTriggers();
		for ( String key : multimap.keys()){
			for(String values : multimap.get(key)){
				System.out.println("Trigger: " + key + " in group(s): "+ values);
			}
		}
	}
	
    /**
     * Set the schedule in standby mode if it is running, active it when it's already in standby mode
     * @param None
     */
	public void pauseReactive(){
			try {
				if (this.getSchedule().isInStandbyMode()){
					System.out.println("Schedule Reactive");	
					this.getSchedule().start();
				}else if(this.getSchedule().isStarted()){
					System.out.println("Schedule in Standby Mode");	 
					this.getSchedule().standby();
				}
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
	}
	
    /**
     * Check if a specify job is currently running 
     * @param
     */
	public boolean isJobRunning(String searchJobName, String searchGroupName){
		List<JobExecutionContext> currentJobs = null;
		try {
			currentJobs = this.getSchedule().getCurrentlyExecutingJobs();
		} catch (SchedulerException e) {
			System.out.println("Scheduler Error! Could not get current jobs!");
			e.printStackTrace();
		}
		//System.out.println("Current Jobs: "+ currentJobs);

		for (JobExecutionContext jobPointer: currentJobs){
		    String jobName = jobPointer.getJobDetail().getName();
		    String groupName = jobPointer.getJobDetail().getGroup();
		    if (jobName.equalsIgnoreCase(searchJobName) && groupName.equalsIgnoreCase(searchGroupName)) {
		        //found it!
		        System.out.println("the job is already running - do nothing");
		        return true;
		    }
		}
		return false;
	}
	
/*	public void reactiveJob(String searchJobName, String searchGroupName) throws SchedulerException{
		List<JobExecutionContext> currentJobs = this.schedule.getCurrentlyExecutingJobs();;
		System.out.println("Current Jobs: "+ currentJobs);

		for (JobExecutionContext jobCtx: currentJobs){
		    String jobName = jobCtx.getJobDetail().getName();
		    String groupName = jobCtx.getJobDetail().getGroup();
		    if (jobName.equalsIgnoreCase(searchJobName) && groupName.equalsIgnoreCase(searchGroupName)) {
		        //found it!
		        System.out.println("the job is already running - do nothing");
		        return;
		    }               
		}
		JobDetail jDetail = new JobDetail();
		jDetail.setName(searchJobName);
		jDetail.setGroup(searchGroupName);
		jDetail.setJobClass(this.schedule.getJobDetail(searchJobName, searchGroupName).getClass());
		
		CronTrigger trigger = new CronTrigger();
		trigger.setName(searchJobName);
		trigger.setGroup(searchGroupName);
		try {
			trigger.setCronExpression("0/3 * * * * ?");
			//schedule.start();
			this.schedule.scheduleJob(jDetail, trigger);
			//this.schedule.addJob(jDetail, true);
			//this.schedule.triggerJob(searchJobName, searchGroupName);
			System.out.println("Job Triggered" + jDetail);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}*/
	
	public SchedulerFactory getFactory() {
		return factory;
	}
	public void setFactory(SchedulerFactory factory) {
		this.factory = factory;
	}
	public Scheduler getSchedule() {
		return schedule;
	}
	public void setSchedule(Scheduler schedule) {
		this.schedule = schedule;
	}
	
}
