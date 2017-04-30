package org.sjsu.edu.main;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.sjsu.edu.controller.DeviceManagementController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "org.sjsu.edu.controller", "org.sjsu.edu.model", "org.sjsu.edu.main","org.sjsu.edu.device.controller" })
public class Application {

	public static void main(String[] args) throws SchedulerException {
		
		Thread thread = new Thread(new Runnable() {

		    @Override
		    public void run() {
		    	/*JobDetail job1 = JobBuilder.newJob(DeviceManagementController.class)
						.withIdentity("job1", "group1").build();

				Trigger trigger1 = TriggerBuilder.newTrigger()
						.withIdentity("cronTrigger1", "group1")
						.withSchedule(CronScheduleBuilder.cronSchedule("0 0 0/2 * * ?"))
						.build();
				
				Scheduler scheduler1;
				try {
					scheduler1 = new StdSchedulerFactory().getScheduler();
					scheduler1.start();
					scheduler1.scheduleJob(job1, trigger1);
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		    	DeviceManagementController.execute();
		    }
		            
		});
		        
		thread.start();
		SpringApplication.run(Application.class, args);
	}
}