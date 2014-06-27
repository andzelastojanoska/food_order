package com.seavus.foodorder.emailsender;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class HalfHourBeforeOrderNotificationMailSender {

	public static void start() {
		
		try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            
         // define the job and tie it to our HelloJob class
            JobDetail job = JobBuilder.newJob(HalfHourBeforeOrderNotification.class)
                .withIdentity("job1", "group1")
                .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            CronTrigger trigger = newTrigger()
            	    .withIdentity("trigger3", "group1")
            	    .withSchedule(cronSchedule("00 29 15 * * ?"))
            	    .forJob("job1", "group1")
            	    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            
            
         // define the job and tie it to our HelloJob class
//            JobDetail job2 = JobBuilder.newJob(OrderingEmployeeGetter.class)
//                .withIdentity("job2", "group2")
//                .build();
//
//            // Trigger the job to run now, and then repeat every 40 seconds
//            CronTrigger trigger2 = newTrigger()
//            	    .withIdentity("trigger2", "group2")
//            	    .withSchedule(cronSchedule("0 58 13 * * ?"))
//            	    .forJob("job2", "group2")
//            	    .build();
//
//            // Tell quartz to schedule the job using our trigger
//            scheduler.scheduleJob(job2, trigger2);
//            
//            Thread.sleep(60000);
            
//            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
	}
	
}
