package com.seavus.foodorder.orderbuttondisabler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.seavus.foodorder.gui.OrderPanel;

public class OrderButtonDisabler {
	public static void start() {
		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();

			// define the job and tie it to our HelloJob class
			JobDetail job = JobBuilder.newJob(OrderPanel.class)
					.withIdentity("job3", "group1").build();

			// Trigger the job to run now, and then repeat every 40 seconds
			CronTrigger trigger = newTrigger()
					.withIdentity("trigger2", "group1")
					.withSchedule(cronSchedule("0 32 15 * * ?"))
					.forJob("job3", "group1").build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

			// Thread.sleep(60000);

			// scheduler.shutdown();

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
