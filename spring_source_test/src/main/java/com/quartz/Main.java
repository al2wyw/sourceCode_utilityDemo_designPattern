package com.quartz;

import com.utils.LoggerUtils;
import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.utils.PropertiesParser;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/25
 * Time: 15:11
 * Desc: RAMJobStore 使用TreeSet来维护trigger，HashMap来维护job
 */
public class Main implements Job {

    public static void main( String args[] ) throws Exception{
        //withIdentity set the job key or trigger key
        JobDetail job = JobBuilder.newJob(Main.class).withIdentity("main-job1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("main-trigger1")
                .withSchedule(CronScheduleBuilder.cronSchedule("/2 * * * * ?")).build();

        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        System.getProperties().put(StdSchedulerFactory.PROP_SCHED_MAX_BATCH_SIZE, "10");

        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.scheduleJob(job,trigger);

        scheduler.addJob(JobBuilder.newJob(MyJob.class).withIdentity("main-job2").storeDurably().build(), true);
        scheduler.scheduleJob(TriggerBuilder.newTrigger().withIdentity("main-trigger2").forJob("main-job2")
                .withSchedule(CronScheduleBuilder.cronSchedule("/4 * * * * ?")).build());

        scheduler.start();//QuartzSchedulerThread
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LoggerUtils.getLogger().info("job1 start ");
    }
}
