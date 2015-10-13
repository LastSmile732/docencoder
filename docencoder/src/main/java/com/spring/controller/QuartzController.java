package com.spring.controller;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrsys.service.LogService;
import com.quartz.JobA;
import com.quartz.JobB;
import com.quartz.QuartzJob;
import com.quartz.QuartzService;

@Controller
public class QuartzController {
	
	//@Autowired
	//private LogService logService;
	
/*	private static final Logger logger = LoggerFactory
            .getLogger(MainController.class);*/
	SchedulerFactory factory;
	Scheduler schedule;
	QuartzService qService;
	int counter = 0;
	public QuartzController(){
		//((StdSchedulerFactory) factory).initialize("quartz.properties");			
		//this.factory = new StdSchedulerFactory("quartz.properties");
		//this.schedule = factory.getScheduler();
		//this.qService = new QuartzService(factory, schedule);
		this.qService = new QuartzService("quartz.properties");
	}
	
	 @RequestMapping(value="/quartz", method = RequestMethod.GET)
	 public String quartz(Model model){
		 this.qService.printJobs();
		 this.qService.printTriggers();
		 return "quartz";
	 }
	 @RequestMapping(value="/checkquartz", method = RequestMethod.POST)
	 public String quartzCheck(@RequestParam(value="jobname") String jobName, @RequestParam(value="groupname") String groupName, Model model) throws SchedulerException{
		 System.out.println("Check Job");
		 //reStartJob(this.schedule, jobName, groupName);
		 return "quartz";
	 }
	 @RequestMapping(value="/quartzA", method = RequestMethod.POST)
	 public String goquartz(Model model) throws Exception{
		 
		 System.out.println("quartzA");
		 String jobName = "JobA";
		 String groupName = "FTP";
		 this.qService.forceTriggerJob(jobName, groupName, true);
/*			JobDetail job = new JobDetail();
			job.setName(jobName);
			job.setJobClass(JobA.class);
			job.setGroup(groupName);
			SimpleTrigger trigger = new SimpleTrigger();
			trigger.setName(groupName);
			trigger.setStartTime(new Date(System.currentTimeMillis()+1000));
			trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
			trigger.setRepeatInterval(2000);
			this.schedule.scheduleJob(job, trigger);*/
			
		 return "quartz";
	 }
	@RequestMapping(value="/redquartz", method = RequestMethod.POST)
	 public String quartzRed(Model model){
		 System.out.println("quartzB");
		 
		 String jobName = "JobB";
		 String groupName = "FTP";
		 this.qService.forceTriggerJob(jobName, groupName);

		 return "quartz";
	 }
	@RequestMapping(value="/bluequartz", method = RequestMethod.POST)
	 public String quartzBlue(Model model){
		 this.qService.pauseReactive();
		/*try {
			if(counter==0){
				System.out.println("Pause A");
				this.qService.pauseJob("JobA", "FORCED");
				this.counter = 1;
			}
			else{
				System.out.println("Resume A");
				this.qService.resumeJob("JobA", "FORCED");
				this.counter = 0;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}*/
		 return "quartz";
	 }
	@RequestMapping(value="/quartzMain", method = RequestMethod.POST)
	 public String quartzMain(Model model){
		 System.out.println("stop - quartz.jsp");
			try {
				if (this.schedule.isStarted()){
					this.schedule.shutdown();
				} else {
					this.schedule.start();					 
				}
				
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		 return "quartz";
	 }
	
}
