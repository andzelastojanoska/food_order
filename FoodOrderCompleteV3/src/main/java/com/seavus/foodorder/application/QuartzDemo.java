package com.seavus.foodorder.application;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.DateBuilder.*;


public class QuartzDemo {

	
	private static Trigger trigger;

	public static void main(String[] args) throws InterruptedException {
		  try {
	            // Grab the Scheduler instance from the Factory 
	            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

	            // and start it off
	            scheduler.start();

	            
	         // define the job and tie it to our HelloJob class
	            JobDetail job = JobBuilder.newJob(SimpleJob.class)
	                .withIdentity("job1", "group1")
	                .build();

	            // Trigger the job to run now, and then repeat every 40 seconds
	            trigger = newTrigger()
	            	    .withIdentity("trigger3", "group1")
	            	    .withSchedule(cronSchedule("0 02 11 * * ?"))
	            	    .forJob("job1", "group1")
	            	    .build();

	            // Tell quartz to schedule the job using our trigger
	            scheduler.scheduleJob(job, trigger);
	            
//	            Thread.sleep(60000);
	            
//	            scheduler.shutdown();

	        } catch (SchedulerException se) {
	            se.printStackTrace();
	        }
	    }
	}
