package com.trigger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.quartz.QuartzService;

public class QuartzTrigger extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
	SchedulerFactory factory;
	Scheduler schedule;
	QuartzService qserv;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		try {
			InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("quartz.properties");
			Properties prop = new Properties();
			prop.load(in);
			this.factory = new StdSchedulerFactory(prop);
			this.schedule = factory.getScheduler();
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.qserv = new QuartzService(factory, schedule);
			 int i=0;
			 int j=0;
			 String[] jobNames = null;
			 String[] groupNames = null;
			try {
				groupNames = qserv.getSchedule().getJobGroupNames();
				System.out.println("group names" + groupNames);
					for (i = 0; i < groupNames.length; i++) {
						   System.out.println("Group: " + groupNames[i] + " contains the following jobs");
						   jobNames = qserv.getSchedule().getJobNames(groupNames[i]);
	
						   for (j = 0; j < jobNames.length; j++) {
						      System.out.println("- " + jobNames[j]);
						      //multimap.put(jobNames[j], groupNames[i]);
						   }
						}
				
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Quartz: " + qserv + " ");
			qserv.getSchedule();
			System.out.println("Quartz schedule: " + qserv.getSchedule());
			//qserv.forceTriggerJob("140", "ExchgRateNofity");
	
	}
}
